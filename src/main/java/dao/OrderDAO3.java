package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class OrderDAO3 extends DAO {
    public OrderDAO3() throws SQLException { super(); }
    public OrderDAO3(Connection con) { super(con); }

    public Order3[] getUnexportedOrders() throws SQLException {
        List<Order3> orders = new ArrayList<>();
        String sql = "SELECT o.* FROM tblOrder3 o WHERE o.status='Chờ duyệt' "
            + "AND NOT EXISTS (SELECT 1 FROM tblInvoice3 i JOIN tblExportNote3 e ON e.tblInvoice3id=i.id WHERE i.tblOrder3id=o.id) ORDER BY o.orderedAt,o.id";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) orders.add(mapOrder(rs));
        }
        return orders.toArray(Order3[]::new);
    }
    public Order3 getOrderDetail(int idOrder) throws SQLException {
        String sql="SELECT o.*,i.id AS invoiceId,i.invoiceNumber,i.issuedAt FROM tblOrder3 o LEFT JOIN tblInvoice3 i ON i.tblOrder3id=o.id WHERE o.id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                if(!rs.next()) return null;
                Order3 order=mapOrder(rs);
                if(rs.getObject("invoiceId")!=null) {
                    Invoice3 invoice=new Invoice3(); invoice.setId(rs.getInt("invoiceId"));
                    invoice.setInvoiceNumber(rs.getString("invoiceNumber")); invoice.setIssuedAt(rs.getTimestamp("issuedAt")); order.setInvoice(invoice);
                }
                return order;
            }
        }
    }

    public boolean approveAndExportOrder(int idOrder, int idSalesStaff) throws SQLException {
        if (con.getAutoCommit()) throw new SQLException("Duyệt và xuất hàng phải thực hiện trong một giao dịch.");
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM tblOrder3 WHERE id=? FOR UPDATE")) {
            ps.setInt(1,idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("Đơn hàng không tồn tại.");
                if (!"Chờ duyệt".equals(rs.getString("status")))
                    throw new SQLException("Đơn hàng đã xuất hoặc không còn được phép xử lý.");
            }
        }

        try (PreparedStatement ps = con.prepareStatement("SELECT id FROM tblInvoice3 WHERE tblOrder3id=?")) {
            ps.setInt(1,idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("Đơn hàng chưa có hóa đơn. Vui lòng kiểm tra dữ liệu đặt hàng.");
            }
        }

        String sql = "SELECT p.id,p.productName,p.stockQuantity,SUM(l.quantity) AS requiredQuantity "
            + "FROM tblProduct3 p JOIN tblOrderLine3 l ON l.tblProduct3id=p.id WHERE l.tblOrder3id=? "
            + "GROUP BY p.id,p.productName,p.stockQuantity ORDER BY p.id FOR UPDATE";
        List<int[]> quantities = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int required = rs.getInt("requiredQuantity");
                    if (rs.getInt("stockQuantity") < required)
                        throw new SQLException("Mặt hàng thiếu tồn kho: " + rs.getString("productName") + ".");
                    quantities.add(new int[]{rs.getInt("id"),required});
                }
            }
        }
        if (quantities.isEmpty()) throw new SQLException("Đơn hàng chưa có hàng hóa.");
        for (int[] item: quantities) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE tblProduct3 SET stockQuantity=stockQuantity-? WHERE id=? AND stockQuantity>=?")) {
                ps.setInt(1,item[1]); ps.setInt(2,item[0]); ps.setInt(3,item[1]);
                if (ps.executeUpdate()!=1) throw new SQLException("Tồn kho vừa thay đổi. Vui lòng kiểm tra lại đơn hàng.");
            }
        }
        try (PreparedStatement ps = con.prepareStatement("UPDATE tblOrder3 SET status='Đã xuất', approvedAt=CURRENT_TIMESTAMP, tblSalesStaff3tblEmployee3tblMember3id=? WHERE id=?")) {
            ps.setInt(1,idSalesStaff); ps.setInt(2,idOrder); ps.executeUpdate();
        }

        return true;
    }
    static Order3 mapOrder(ResultSet rs) throws SQLException {
        Order3 o = new Order3(); o.setId(rs.getInt("id"));
        o.setOrderNumber(rs.getString("orderNumber")); o.setOrderedAt(rs.getTimestamp("orderedAt"));
        o.setDeliveryAddress(rs.getString("deliveryAddress")); o.setTotalAmount(rs.getFloat("totalAmount"));
        o.setStatus(rs.getString("status")); o.setApprovedAt(rs.getTimestamp("approvedAt")); return o;
    }
}
