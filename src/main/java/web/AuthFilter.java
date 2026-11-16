package web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.*;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Set<String> CUSTOMER_PAGES=Set.of(
        "/CustomerMainUI3.jsp","/InvoiceListUI3.jsp","/InvoiceDetailUI3.jsp");
    private static final Set<String> SALES_PAGES=Set.of(
        "/SalesStaffMainUI3.jsp","/OrderListUI3.jsp","/ApproveAndExportOrderUI3.jsp",
        "/PrintInvoiceUI3.jsp","/approve-export");
    @Override public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException {
        HttpServletRequest req=(HttpServletRequest)request; HttpServletResponse res=(HttpServletResponse)response;
        req.setCharacterEncoding("UTF-8"); res.setCharacterEncoding("UTF-8");
        res.setHeader("X-Content-Type-Options","nosniff"); res.setHeader("X-Frame-Options","DENY");
        res.setHeader("Cache-Control","no-store");
        String path=req.getServletPath();
        if(path.startsWith("/assets/")) { chain.doFilter(req,res); return; }
        HttpSession session=req.getSession(true);
        if(session.getAttribute("csrf")==null) session.setAttribute("csrf",UUID.randomUUID().toString());
        Member3 member=(Member3)session.getAttribute("member");
        if(path.equals("/")||path.equals("/index.jsp")||path.equals("/login")) { chain.doFilter(req,res); return; }
        if(member==null) { res.sendRedirect(req.getContextPath()+"/index.jsp?timeout=1"); return; }
        boolean customer=CUSTOMER_PAGES.contains(path);
        boolean sales=SALES_PAGES.contains(path);
        if((customer && !(member instanceof Customer3)) || (sales && !(member instanceof SalesStaff3))) { res.sendError(403,"Bạn không có quyền thực hiện chức năng này."); return; }
        chain.doFilter(req,res);
    }
}
