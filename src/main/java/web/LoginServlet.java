package web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import dao.MemberDAO3;
import model.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException {
        String username=req.getParameter("username"), password=req.getParameter("password");
        if(!String.valueOf(req.getSession().getAttribute("csrf")).equals(req.getParameter("csrf"))) { res.sendError(403); return; }
        if(username==null||password==null||username.length()>255||password.length()>255) { res.sendRedirect("index.jsp?error=1"); return; }
        try(MemberDAO3 dao=new MemberDAO3()) {
            Member3 member=dao.login(username,password);
            if(member==null) { res.sendRedirect("index.jsp?error=1"); return; }
            req.getSession().invalidate(); HttpSession session=req.getSession(true);
            session.setAttribute("member",member); session.setAttribute("csrf",UUID.randomUUID().toString()); session.setMaxInactiveInterval(1800);
            res.sendRedirect(member instanceof Customer3?"CustomerMainUI3.jsp":"SalesStaffMainUI3.jsp");
        } catch(SQLException e) { throw new ServletException("Không thể kết nối cơ sở dữ liệu.",e); }
    }
}
