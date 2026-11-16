package setup;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.io.*;


public class DatabaseSetup {
    public static void main(String[] args) throws Exception {
        Path root=Path.of(args[0]);
        Properties secrets=new Properties();
        try(Reader in=Files.newBufferedReader(root.resolve(".runtime/database.properties"))) { secrets.load(in); }
        boolean initialized=Files.exists(root.resolve(".runtime/database-ready"));
        String password=initialized?secrets.getProperty("rootPassword"):"";
        String url="jdbc:mariadb://127.0.0.1:3308/?useUnicode=true&characterEncoding=UTF-8";
        try(Connection con=DriverManager.getConnection(url,"root",password);Statement statement=con.createStatement()) {
            runFile(statement,root.resolve("database/schema.sql"));
            if(!initialized) runFile(statement,root.resolve("database/data.sql"));
            String appPassword=secrets.getProperty("appPassword");
            statement.execute("CREATE USER IF NOT EXISTS 'store_app'@'127.0.0.1' IDENTIFIED BY '"+appPassword+"'");
            statement.execute("ALTER USER 'store_app'@'127.0.0.1' IDENTIFIED BY '"+appPassword+"'");
            statement.execute("GRANT SELECT,INSERT,UPDATE,DELETE ON online_department_store3.* TO 'store_app'@'127.0.0.1'");
            if(!initialized) statement.execute("ALTER USER 'root'@'localhost' IDENTIFIED BY '"+secrets.getProperty("rootPassword")+"'");
        }
        Files.writeString(root.resolve(".runtime/database-ready"),"Initialized; do not delete while retaining the database.\n");
        System.out.println("Database online_department_store3 ready (17 tables).");
    }
    public static void runFile(Statement statement,Path file) throws Exception {
        String source=Files.readString(file,StandardCharsets.UTF_8).replaceAll("(?m)^\\s*--.*$","");
        String delimiter=";";
        StringBuilder command=new StringBuilder();
        for(String line:source.split("\\R")) {
            String trimmed=line.trim();
            if(trimmed.regionMatches(true,0,"DELIMITER ",0,10)) {
                if(!command.toString().isBlank()) throw new IOException("Incomplete SQL command before DELIMITER.");
                delimiter=trimmed.substring(10).trim();
                if(delimiter.isEmpty()) throw new IOException("Empty SQL delimiter.");
                continue;
            }
            command.append(line).append('\n');
            String sql=command.toString().trim();
            if(!sql.isEmpty() && sql.endsWith(delimiter)) {
                statement.execute(sql.substring(0,sql.length()-delimiter.length()).trim());
                command.setLength(0);
            }
        }
        if(!command.toString().isBlank()) throw new IOException("Incomplete SQL command at end of file.");
    }
}
