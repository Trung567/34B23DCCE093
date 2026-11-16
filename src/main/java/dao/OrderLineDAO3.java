package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class OrderLineDAO3 extends DAO {
    public OrderLineDAO3() throws SQLException { super(); }
    public OrderLineDAO3(Connection con) { super(con); }
    public OrderLine3[] getLinesOfOrder(int idOrder) throws SQLException {
        List<OrderLine3> lines = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM tblOrderLine3 WHERE tblOrder3id=? ORDER BY id")) {
            ps.setInt(1, idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderLine3 line = new OrderLine3();
                    line.setId(rs.getInt("id"));
                    line.setQuantity(rs.getInt("quantity"));
                    line.setUnitPrice(rs.getFloat("unitPrice"));
                    line.setLineTotal(rs.getFloat("lineTotal"));
                    Product3 product = new Product3(); product.setId(rs.getInt("tblProduct3id"));
                    line.setProduct(product);
                    lines.add(line);
                }
            }
        }
        return lines.toArray(OrderLine3[]::new);
    }
}
