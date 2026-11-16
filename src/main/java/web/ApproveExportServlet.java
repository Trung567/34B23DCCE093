package web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import dao.*;
import model.*;

@WebServlet("/approve-export")
public class ApproveExportServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException {
        if(!(req.getSession().getAttribute("member") instanceof SalesStaff3 sales)) { res.sendError(403); return; }
        if(!String.valueOf(req.getSession().getAttribute("csrf")).equals(req.getParameter("csrf"))) { res.sendError(403); return; }
        int idOrder=Ui.id(req.getParameter("idOrder")); int idDeliveryStaff=Ui.id(req.getParameter("idDeliveryStaff"));
        if(idOrder<1) { res.sendError(400); return; }
        if(idDeliveryStaff<1) { fail(req,res,idOrder,"Vui lòng chọn nhân viên giao hàng."); return; }
        try(Connection con=DAO.openConnection()) {
            con.setAutoCommit(false);
            try {
                new OrderDAO3(con).approveAndExportOrder(idOrder,sales.getId());
                new ExportNoteDAO3(con).createForOrder(idOrder,sales.getId(),idDeliveryStaff);
                con.commit();
            } catch(SQLException e) {
                con.rollback();
                String message=e.getMessage();
                if(e.getSQLState()!=null && (e.getSQLState().startsWith("23")||e.getSQLState().startsWith("40")))
                    message="Đơn hàng hoặc tồn kho vừa thay đổi. Vui lòng tải lại danh sách đơn hàng.";
                fail(req,res,idOrder,message); return;
            }
        } catch(SQLException e) { throw new ServletException("Không thể lưu giao dịch xuất hàng.",e); }
        req.getSession().setAttribute("success","Duyệt đơn hàng và xuất hàng thành công.");
        res.sendRedirect("PrintInvoiceUI3.jsp?idOrder="+idOrder);
    }
    private void fail(HttpServletRequest req,HttpServletResponse res,int idOrder,String message) throws IOException {
        req.getSession().setAttribute("error",message);
        res.sendRedirect("ApproveAndExportOrderUI3.jsp?idOrder="+idOrder);
    }
}
