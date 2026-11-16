from pathlib import Path
import json
import os
import secrets
import shutil
import socket
import subprocess
import sys
import time

ROOT=Path(__file__).resolve().parents[1]
RUNTIME=ROOT/'.runtime'
JAVA_HOME=Path(os.environ.get('JAVA_HOME',r'C:\Program Files\Java\jdk-25.0.2'))
JAVA=str(JAVA_HOME/'bin/java.exe') if (JAVA_HOME/'bin/java.exe').exists() else shutil.which('java')
JAVAC=str(JAVA_HOME/'bin/javac.exe') if (JAVA_HOME/'bin/javac.exe').exists() else shutil.which('javac')
FLAGS=subprocess.CREATE_NO_WINDOW if os.name=='nt' else 0

def run(args,**kwargs):
    subprocess.run([str(a) for a in args],cwd=ROOT,check=True,creationflags=FLAGS,**kwargs)

def config():
    if not (RUNTIME/'runtime.json').exists():
        run([sys.executable,ROOT/'scripts/setup_runtime.py'])
    return json.loads((RUNTIME/'runtime.json').read_text(encoding='utf-8'))

def external_database():
    values={}
    file=ROOT/'database.properties'
    if file.exists():
        for line in file.read_text(encoding='utf-8-sig').splitlines():
            key,separator,value=line.partition('=')
            if separator: values[key.strip()]=value
    url=os.environ.get('STORE_DB_URL',values.get('url','')).strip()
    if not url: return None
    return {
        'STORE_DB_URL':url,
        'STORE_DB_USER':os.environ.get('STORE_DB_USER',values.get('user','root')),
        'STORE_DB_PASSWORD':os.environ.get('STORE_DB_PASSWORD',values.get('password','')),
    }

def listening(port):
    try:
        with socket.create_connection(('127.0.0.1',port),timeout=.3): return True
    except OSError: return False

def wait_port(port):
    deadline=time.monotonic()+45
    while time.monotonic()<deadline:
        if listening(port): return
        time.sleep(.25)
    raise RuntimeError(f'Port {port} did not become ready; inspect .runtime logs.')

def launch(args,log,env=None):
    with (RUNTIME/log).open('ab') as output:
        process=subprocess.Popen([str(a) for a in args],cwd=ROOT,stdout=output,stderr=output,env=env,creationflags=FLAGS)
    return process.pid

def build(cfg):
    if not JAVAC: raise RuntimeError('JDK 17 or newer is required.')
    classes=ROOT/'target/classes'; classes.mkdir(parents=True,exist_ok=True)
    sources=sorted((ROOT/'src/main/java').rglob('*.java'))
    cp=str(Path(cfg['tomcat'])/'lib/*')+os.pathsep+cfg['jdbc']
    arguments=ROOT/'target/javac.args'
    arguments.write_text('\n'.join('"'+str(p).replace('\\','/')+'"' for p in sources),encoding='utf-8')
    run([JAVAC,'-encoding','UTF-8','--release','17','-cp',cp,'-d',classes,'@'+str(arguments)])
    webapp=RUNTIME/'tomcat-base/webapps/ROOT'
    if webapp.exists():
        for page in webapp.glob('*.jsp'):
            if not (ROOT/'src/main/webapp'/page.name).exists(): page.unlink()
    shutil.copytree(ROOT/'src/main/webapp',webapp,dirs_exist_ok=True)
    shutil.copytree(classes,webapp/'WEB-INF/classes',dirs_exist_ok=True)
    (webapp/'WEB-INF/lib').mkdir(exist_ok=True)
    shutil.copy2(cfg['jdbc'],webapp/'WEB-INF/lib'/Path(cfg['jdbc']).name)
    target=ROOT/'target/store-web.war'
    import zipfile
    with zipfile.ZipFile(target,'w',zipfile.ZIP_DEFLATED) as war:
        for file in webapp.rglob('*'):
            if file.is_file(): war.write(file,file.relative_to(webapp))
    print('Build ready: target/store-web.war',flush=True)

def start(cfg):
    state_file=RUNTIME/'processes.json'
    if state_file.exists() and listening(8080):
        print('Website already running: http://localhost:8080. Restart Tomcat to apply database configuration changes.',flush=True); return
    build(cfg)
    state=json.loads(state_file.read_text()) if state_file.exists() else {}
    env=os.environ.copy()
    selected_database=external_database()
    cp=str(ROOT/'target/classes')+os.pathsep+cfg['jdbc']
    if selected_database:
        env.update(selected_database)
        run([JAVA,'-cp',cp,'setup.DatabaseCheck'],env=env)
    else:
        secrets_file=RUNTIME/'database.properties'
        if not secrets_file.exists():
            secrets_file.write_text('rootPassword='+secrets.token_hex(24)+'\nappPassword='+secrets.token_hex(24)+'\n',encoding='utf-8')
        passwords=dict(line.split('=',1) for line in secrets_file.read_text().splitlines())
        data=RUNTIME/'database-data'
        maria=Path(cfg['mariadb'])
        if not (data/'mysql').exists():
            run([maria/'bin/mariadb-install-db.exe','--datadir='+str(data),'--port=3308'])
        if not listening(3308):
            state['database']=launch([maria/'bin/mariadbd.exe','--no-defaults','--basedir='+str(maria),
                '--datadir='+str(data),'--port=3308','--bind-address=127.0.0.1','--character-set-server=utf8mb4',
                '--collation-server=utf8mb4_unicode_ci','--console'],'database.log')
            state_file.write_text(json.dumps(state),encoding='utf-8')
        wait_port(3308)
        run([JAVA,'-cp',cp,'setup.DatabaseSetup',ROOT])
        env['STORE_DB_USER']='store_app'
        env['STORE_DB_PASSWORD']=passwords['appPassword']
        env['STORE_DB_URL']='jdbc:mariadb://127.0.0.1:3308/online_department_store3?useUnicode=true&characterEncoding=UTF-8'
    base=RUNTIME/'tomcat-base'; conf=base/'conf'; conf.mkdir(parents=True,exist_ok=True)
    for name in ('web.xml','catalina.properties','logging.properties','context.xml'):
        shutil.copy2(Path(cfg['tomcat'])/'conf'/name,conf/name)
    (base/'logs').mkdir(exist_ok=True); (base/'temp').mkdir(exist_ok=True)
    (conf/'server.xml').write_text('''<Server port="-1"><Service name="Catalina">
<Connector port="8080" address="127.0.0.1" protocol="HTTP/1.1" connectionTimeout="20000" URIEncoding="UTF-8"/>
<Engine name="Catalina" defaultHost="localhost"><Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="false"/></Engine>
</Service></Server>''',encoding='utf-8')
    classpath=str(Path(cfg['tomcat'])/'bin/bootstrap.jar')+os.pathsep+str(Path(cfg['tomcat'])/'bin/tomcat-juli.jar')
    state['web']=launch([JAVA,'-Dfile.encoding=UTF-8','-Duser.timezone=Asia/Ho_Chi_Minh',
        '-Dcatalina.home='+cfg['tomcat'],'-Dcatalina.base='+str(base),'-Djava.io.tmpdir='+str(base/'temp'),
        '-Djava.util.logging.config.file='+str(conf/'logging.properties'),
        '-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager',
        '-cp',classpath,'org.apache.catalina.startup.Bootstrap','start'],'web.log',env)
    state_file.write_text(json.dumps(state),encoding='utf-8')
    wait_port(8080)
    print('Website: http://localhost:8080',flush=True)

def stop():
    state_file=RUNTIME/'processes.json'
    if not state_file.exists(): print('No project processes recorded.'); return
    state=json.loads(state_file.read_text())
    for name,pid in state.items():
        if name=='database': continue

        command=f"$p=Get-CimInstance Win32_Process -Filter 'ProcessId = {int(pid)}'; if ($p -and $p.CommandLine.Contains('{str(ROOT).replace(chr(39),chr(39)*2)}')) {{ Stop-Process -Id {int(pid)} -Force }}"
        run(['powershell','-NoProfile','-Command',command])
    if 'database' in state and listening(3308):
        cfg=config()
        cp=str(ROOT/'target/classes')+os.pathsep+cfg['jdbc']
        run([JAVA,'-cp',cp,'setup.DatabaseShutdown',ROOT])
        deadline=time.monotonic()+15
        while listening(3308) and time.monotonic()<deadline: time.sleep(.25)
        if listening(3308): raise RuntimeError('Database is still shutting down; the process record was retained.')
    state_file.unlink()
    print('Project processes stopped.')

if __name__=='__main__':
    action=sys.argv[1] if len(sys.argv)>1 else 'start'
    if action=='stop': stop()
    elif action=='build': build(config())
    elif action=='start': start(config())
    else: raise SystemExit('Usage: manage.py [start|stop|build]')
