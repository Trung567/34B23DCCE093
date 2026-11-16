package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class InvoiceDAO3 extends DAO {
    public InvoiceDAO3() throws SQLException { super(); }
    public InvoiceDAO3(Connection con) { super(con); }
    public Order3[] getInvoicesOfCustomer(int idCustomer) throws SQLException {
        List<Order3> orders = new ArrayList<>();
        String sql="SELECT o.*,i.id AS invoiceId,i.invoiceNumber,i.issuedAt FROM tblOrder3 o JOIN tblInvoice3 i ON i.tblOrder3id=o.id WHERE o.tblCustomer3tblMember3id=? ORDER BY i.issuedAt DESC,i.id DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,idCustomer);
            try (ResultSet rs=ps.executeQuery()) { while(rs.next()) orders.add(mapInvoiceOrder(rs)); }
        }
        return orders.toArray(Order3[]::new);
    }
    public Order3 getInvoiceDetail(int idInvoice) throws SQLException {
        String sql="SELECT o.*,i.id AS invoiceId,i.invoiceNumber,i.issuedAt FROM tblOrder3 o JOIN tblInvoice3 i ON i.tblOrder3id=o.id WHERE i.id=?";
        Order3 order;
        try (PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,idInvoice);
            try(ResultSet rs=ps.executeQuery()) { order=rs.next()?mapInvoiceOrder(rs):null; }
        }
        if(order!=null) {
            order.setOrderLines(new OrderLineDAO3(con).getLinesOfOrder(order.getId()));
            for(OrderLine3 line:order.getOrderLines()) line.setProduct(new ProductDAO3(con).getProductOfLine(line.getId()));
        }
        return order;
    }

    public Order3 getInvoiceDetail(int idInvoice,int idCustomer) throws SQLException {
        try(PreparedStatement ps=con.prepareStatement("SELECT i.id FROM tblInvoice3 i JOIN tblOrder3 o ON o.id=i.tblOrder3id WHERE i.id=? AND o.tblCustomer3tblMember3id=?")) {
            ps.setInt(1,idInvoice); ps.setInt(2,idCustomer);
            try(ResultSet rs=ps.executeQuery()) { if(!rs.next()) return null; }
        }
        return getInvoiceDetail(idInvoice);
    }
    private Order3 mapInvoiceOrder(ResultSet rs) throws SQLException {
        Order3 order=OrderDAO3.mapOrder(rs); Invoice3 invoice=new Invoice3();
        invoice.setId(rs.getInt("invoiceId")); invoice.setInvoiceNumber(rs.getString("invoiceNumber"));
        invoice.setIssuedAt(rs.getTimestamp("issuedAt")); order.setInvoice(invoice); return order;
    }
}
