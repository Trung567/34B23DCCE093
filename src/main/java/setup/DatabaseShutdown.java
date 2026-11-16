package setup;

import java.nio.file.*;
import java.sql.*;
import java.util.Properties;


public class DatabaseShutdown {
    public static void main(String[] args) throws Exception {
        Path root=Path.of(args[0]); Properties properties=new Properties();
        try(var reader=Files.newBufferedReader(root.resolve(".runtime/database.properties"))) { properties.load(reader); }
        try(Connection con=DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3308/","root",properties.getProperty("rootPassword"));Statement st=con.createStatement()) {
            st.execute("SHUTDOWN");
        }
        System.out.println("Project database shut down cleanly.");
    }
}
