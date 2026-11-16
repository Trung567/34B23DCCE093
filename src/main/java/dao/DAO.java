package dao;

import java.sql.*;


public class DAO implements AutoCloseable {
    protected final Connection con;
    private final boolean owner;
    public DAO() throws SQLException { this(openConnection(), true); }
    public DAO(Connection con) { this(con, false); }
    private DAO(Connection con, boolean owner) { this.con = con; this.owner = owner; }
    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
            System.getenv().getOrDefault("STORE_DB_URL", "jdbc:mariadb://127.0.0.1:3308/online_department_store3?useUnicode=true&characterEncoding=UTF-8"),
            System.getenv().getOrDefault("STORE_DB_USER", "store_app"),
            System.getenv().getOrDefault("STORE_DB_PASSWORD", ""));
    }
    @Override public void close() throws SQLException { if (owner) con.close(); }
}
