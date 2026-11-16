package setup;

import dao.DAO;
import java.sql.*;
import java.util.Set;

public class DatabaseCheck {
    public static void main(String[] args) throws SQLException {
        Set<String> required=Set.of("tblMember3","tblFullName3","tblCustomer3","tblEmployee3",
            "tblSalesStaff3","tblDeliveryStaff3","tblProduct3","tblProductCategory3",
            "tblOrder3","tblOrderLine3","tblInvoice3","tblExportNote3");
        try(Connection con=DAO.openConnection()) {
            if(con.getCatalog()==null || con.getCatalog().isBlank())
                throw new SQLException("Database name is missing from the JDBC URL.");
            for(String table:required) {
                try(PreparedStatement statement=con.prepareStatement("SELECT 1 FROM "+table+" LIMIT 0")) {
                    statement.executeQuery().close();
                }
            }
            System.out.println("Connected to database: "+con.getCatalog()+" ("+con.getMetaData().getDatabaseProductVersion()+")");
        }
    }
}
