package dao;

import java.sql.*;
import model.*;

public class ProductDAO3 extends DAO {
    public ProductDAO3() throws SQLException { super(); }
    public ProductDAO3(Connection con) { super(con); }
    public Product3 getProductOfLine(int idOrderLine) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT p.* FROM tblProduct3 p JOIN tblOrderLine3 l ON l.tblProduct3id=p.id WHERE l.id=?")) {
            ps.setInt(1, idOrderLine);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapProduct(rs) : null; }
        }
    }
    static Product3 mapProduct(ResultSet rs) throws SQLException {
        Product3 p = new Product3();
        p.setId(rs.getInt("id")); p.setProductCode(rs.getString("productCode"));
        p.setProductName(rs.getString("productName")); p.setDescription(rs.getString("description"));
        p.setUnitOfMeasure(rs.getString("unitOfMeasure")); p.setSellingPrice(rs.getFloat("sellingPrice"));
        p.setStockQuantity(rs.getInt("stockQuantity")); return p;
    }
}
