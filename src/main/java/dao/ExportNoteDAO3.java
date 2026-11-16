package dao;

import java.sql.*;
import java.util.UUID;
import model.*;

public class ExportNoteDAO3 extends DAO {
    public ExportNoteDAO3() throws SQLException { super(); }
    public ExportNoteDAO3(Connection con) { super(con); }
    public DeliveryStaff3 getExportNoteOfOrder(int idOrder) throws SQLException {
        String sql="SELECT m.*,f.lastName,f.firstName,em.employeeCode,em.position,e.id AS exportId,e.exportNumber,e.exportedAt,e.note AS exportNote "
            +"FROM tblExportNote3 e JOIN tblInvoice3 i ON i.id=e.tblInvoice3id "
            +"JOIN tblDeliveryStaff3 d ON d.tblEmployee3tblMember3id=e.tblDeliveryStaff3tblEmployee3tblMember3id "
            +"JOIN tblEmployee3 em ON em.tblMember3id=d.tblEmployee3tblMember3id JOIN tblMember3 m ON m.id=em.tblMember3id "
            +"JOIN tblFullName3 f ON f.tblMember3id=m.id WHERE i.tblOrder3id=?";
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,idOrder);
            try(ResultSet rs=ps.executeQuery()) {
                if(!rs.next()) return null;
                DeliveryStaff3 staff=DeliveryStaffDAO3.mapStaff(rs); ExportNote3 note=new ExportNote3();
                note.setId(rs.getInt("exportId")); note.setExportNumber(rs.getString("exportNumber"));
                note.setExportedAt(rs.getTimestamp("exportedAt")); note.setNote(rs.getString("exportNote"));
                staff.setExportNotes(new ExportNote3[]{note}); return staff;
            }
        }
    }
    public boolean createForOrder(int idOrder,int idSalesStaff,int idDeliveryStaff) throws SQLException {
        if(con.getAutoCommit()) throw new SQLException("Phiếu xuất phải được tạo cùng giao dịch duyệt đơn.");

        try(PreparedStatement ps=con.prepareStatement("SELECT tblEmployee3tblMember3id FROM tblDeliveryStaff3 WHERE tblEmployee3tblMember3id=?")) {
            ps.setInt(1,idDeliveryStaff);
            try(ResultSet rs=ps.executeQuery()) { if(!rs.next()) throw new SQLException("Vui lòng chọn nhân viên giao hàng hợp lệ."); }
        }
        String sql="INSERT INTO tblExportNote3(exportNumber,exportedAt,note,tblInvoice3id,tblDeliveryStaff3tblEmployee3tblMember3id,tblSalesStaff3tblEmployee3tblMember3id) "
            +"SELECT ?,CURRENT_TIMESTAMP,NULL,i.id,?,? FROM tblInvoice3 i JOIN tblOrder3 o ON o.id=i.tblOrder3id WHERE o.id=? AND o.status='Đã xuất'";
        try(PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,"PX-"+UUID.randomUUID()); ps.setInt(2,idDeliveryStaff); ps.setInt(3,idSalesStaff); ps.setInt(4,idOrder);
            if(ps.executeUpdate()!=1) throw new SQLException("Không thể tạo phiếu xuất cho đơn hàng.");
            try(ResultSet keys=ps.getGeneratedKeys()) {
                if(!keys.next()) throw new SQLException("Không thể tạo mã phiếu xuất.");
                int id=keys.getInt(1);
                try(PreparedStatement update=con.prepareStatement("UPDATE tblExportNote3 SET exportNumber=? WHERE id=?")) {
                    update.setString(1,String.format("PX%04d",id)); update.setInt(2,id); update.executeUpdate();
                }
            }
        }
        return true;
    }
}
