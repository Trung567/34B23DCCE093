package dao;

import java.sql.*;
import model.*;
import web.Passwords;


public class MemberDAO3 extends DAO {
    public MemberDAO3() throws SQLException { super(); }
    public Member3 login(String username,String password) throws SQLException {
        String sql="SELECT m.*,f.lastName,f.firstName,c.customerCode,e.employeeCode,e.position,s.tblEmployee3tblMember3id AS salesId "
            +"FROM tblMember3 m JOIN tblFullName3 f ON f.tblMember3id=m.id LEFT JOIN tblCustomer3 c ON c.tblMember3id=m.id "
            +"LEFT JOIN tblEmployee3 e ON e.tblMember3id=m.id LEFT JOIN tblSalesStaff3 s ON s.tblEmployee3tblMember3id=m.id WHERE m.username=?";
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1,username.trim());
            try(ResultSet rs=ps.executeQuery()) {
                if(!rs.next() || !Passwords.verify(password,rs.getString("password"))) return null;
                Member3 member;
                if(rs.getString("customerCode")!=null) {
                    Customer3 customer=new Customer3(); customer.setCustomerCode(rs.getString("customerCode")); member=customer;
                } else if(rs.getObject("salesId")!=null) {
                    SalesStaff3 sales=new SalesStaff3(); sales.setEmployeeCode(rs.getString("employeeCode")); sales.setPosition(rs.getString("position")); member=sales;
                } else return null;
                member.setId(rs.getInt("id")); member.setUsername(rs.getString("username")); member.setBirthDate(rs.getDate("birthDate"));
                member.setEmail(rs.getString("email")); member.setPhone(rs.getString("phone"));
                FullName3 fullName=new FullName3(); fullName.setLastName(rs.getString("lastName")); fullName.setFirstName(rs.getString("firstName")); member.setFullName(fullName);
                return member;
            }
        }
    }
}
