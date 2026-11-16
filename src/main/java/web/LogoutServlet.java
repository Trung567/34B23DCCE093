package web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
        if(!String.valueOf(req.getSession().getAttribute("csrf")).equals(req.getParameter("csrf"))) { res.sendError(403); return; }
        req.getSession().invalidate(); res.sendRedirect("index.jsp");
    }
}
