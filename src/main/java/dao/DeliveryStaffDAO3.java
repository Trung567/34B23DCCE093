package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class DeliveryStaffDAO3 extends DAO {
    public DeliveryStaffDAO3() throws SQLException { super(); }
    public DeliveryStaffDAO3(Connection con) { super(con); }
    public DeliveryStaff3[] getAllDeliveryStaff() throws SQLException {
        List<DeliveryStaff3> staff=new ArrayList<>();
        String sql="SELECT m.*,f.lastName,f.firstName,e.employeeCode,e.position FROM tblDeliveryStaff3 d JOIN tblEmployee3 e ON e.tblMember3id=d.tblEmployee3tblMember3id JOIN tblMember3 m ON m.id=e.tblMember3id JOIN tblFullName3 f ON f.tblMember3id=m.id ORDER BY e.employeeCode";
        try(PreparedStatement ps=con.prepareStatement(sql);ResultSet rs=ps.executeQuery()) { while(rs.next()) staff.add(mapStaff(rs)); }
        return staff.toArray(DeliveryStaff3[]::new);
    }
    static DeliveryStaff3 mapStaff(ResultSet rs) throws SQLException {
        DeliveryStaff3 staff=new DeliveryStaff3(); staff.setId(rs.getInt("id"));
        staff.setEmployeeCode(rs.getString("employeeCode")); staff.setPosition(rs.getString("position"));
        staff.setPhone(rs.getString("phone")); staff.setEmail(rs.getString("email"));
        FullName3 name=new FullName3(); name.setLastName(rs.getString("lastName")); name.setFirstName(rs.getString("firstName"));
        staff.setFullName(name); return staff;
    }
}
