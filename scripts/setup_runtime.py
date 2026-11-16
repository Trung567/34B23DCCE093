from pathlib import Path
from concurrent.futures import ThreadPoolExecutor
import hashlib
import json
import urllib.request
import zipfile

ROOT = Path(__file__).resolve().parents[1]
RUNTIME = ROOT / '.runtime'
RUNTIME.mkdir(exist_ok=True)

def fetch(url, destination):
    if destination.exists():
        return destination
    print('Downloading ' + destination.name, flush=True)
    with urllib.request.urlopen(url, timeout=90) as response:
        with destination.with_suffix(destination.suffix + '.part').open('wb') as output:
            while data := response.read(1024 * 1024):
                output.write(data)
    destination.with_suffix(destination.suffix + '.part').replace(destination)
    return destination

def prepare_tomcat():

    version = '10.1.61'
    folder = RUNTIME / f'apache-tomcat-{version}'
    if folder.exists():
        return str(folder)
    filename = f'tomcat-{version}.zip'
    base = f'https://repo.maven.apache.org/maven2/org/apache/tomcat/tomcat/{version}/'
    target = RUNTIME / filename
    fetch(base + filename, target)
    checksum = fetch(base + filename + '.sha512', RUNTIME / (filename + '.sha512'))
    expected = checksum.read_text().split()[0].lower()
    if hashlib.sha512(target.read_bytes()).hexdigest() != expected:
        raise RuntimeError('Tomcat checksum mismatch')
    if not folder.exists():
        with zipfile.ZipFile(target) as package:
            package.extractall(RUNTIME)
    return str(folder)

def prepare_database():
    version = '11.4.13'
    folder = RUNTIME / f'mariadb-{version}-winx64'
    if folder.exists():
        return str(folder)
    filename = f'mariadb-{version}-winx64.zip'
    base = f'https://mirror.mariadb.org/mariadb-{version}/winx64-packages/'
    target = fetch(base + filename, RUNTIME / filename)
    if not folder.exists():
        with zipfile.ZipFile(target) as package:
            package.extractall(RUNTIME)
    return str(folder)

def prepare_driver():
    version = '3.5.3'
    filename = f'mariadb-java-client-{version}.jar'
    target = RUNTIME / filename
    base = f'https://repo.maven.apache.org/maven2/org/mariadb/jdbc/mariadb-java-client/{version}/'
    fetch(base + filename, target)
    checksum = fetch(base + filename + '.sha1', RUNTIME / (filename + '.sha1'))
    if hashlib.sha1(target.read_bytes()).hexdigest() != checksum.read_text().strip().lower():
        raise RuntimeError('JDBC driver checksum mismatch')
    return str(target)

if __name__ == '__main__':
    with ThreadPoolExecutor(max_workers=3) as pool:
        jobs = [pool.submit(fn) for fn in (prepare_tomcat, prepare_database, prepare_driver)]
        values = [job.result() for job in jobs]
    config = dict(zip(('tomcat', 'mariadb', 'jdbc'), values))
    (RUNTIME / 'runtime.json').write_text(json.dumps(config, indent=2), encoding='utf-8')
    print('Runtime ready.', flush=True)
