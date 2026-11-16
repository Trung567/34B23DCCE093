<%@ page contentType="text/html; charset=UTF-8" import="model.*,dao.*,web.Ui" %>
<%
int idOrder=Ui.id(request.getParameter("idOrder")); Order3 order=null; DeliveryStaff3 deliveryStaff;
Order3 summary;
try(OrderDAO3 dao=new OrderDAO3()) { summary=dao.getOrderDetail(idOrder); }
if(summary!=null && summary.getInvoice()!=null) {
    try(InvoiceDAO3 dao=new InvoiceDAO3()) { order=dao.getInvoiceDetail(summary.getInvoice().getId()); }
}
if(order==null) { response.sendError(404); return; }
try(ExportNoteDAO3 dao=new ExportNoteDAO3()) { deliveryStaff=dao.getExportNoteOfOrder(order.getId()); }
if(deliveryStaff==null) { response.sendError(404); return; }
ExportNote3 export=deliveryStaff.getExportNotes()[0]; order.getInvoice().setExportNote(export);
String success=(String)session.getAttribute("success"); session.removeAttribute("success");
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>In hóa đơn và phiếu xuất</title></head><body>
<main class="panel detail-panel print-panel"><h1>In hóa đơn và phiếu xuất</h1>
<% if(success!=null) { %><p class="message no-print" role="status"><%=Ui.h(success)%></p><% } %>
<section class="section print-section"><h2>HÓA ĐƠN</h2>
<div class="print-meta"><div><div class="field"><span class="label">Mã hóa đơn:</span><strong><%=Ui.h(order.getInvoice().getInvoiceNumber())%></strong></div><div class="field"><span class="label">Ngày lập:</span><strong><%=Ui.dateTime(order.getInvoice().getIssuedAt())%></strong></div></div>
<div><div class="field"><span class="label">Mã đơn hàng:</span><strong><%=Ui.h(order.getOrderNumber())%></strong></div><div class="field"><span class="label">Ngày đặt:</span><strong><%=Ui.dateTime(order.getOrderedAt())%></strong></div></div>
<div class="field"><span class="label">Địa chỉ giao hàng:</span><strong><%=Ui.h(order.getDeliveryAddress())%></strong></div></div>
<%@ include file="WEB-INF/lines.jspf" %></section>
<section class="section print-section"><h2>PHIẾU XUẤT HÀNG</h2>
<div class="delivery-fields"><div><div class="field"><span class="label">Mã phiếu xuất:</span><strong><%=Ui.h(export.getExportNumber())%></strong></div><div class="field"><span class="label">Ngày xuất:</span><strong><%=Ui.dateTime(export.getExportedAt())%></strong></div></div><div><div class="field"><span class="label">Nhân viên giao hàng:</span><strong><%=Ui.h(Ui.name(deliveryStaff))%> (<%=Ui.h(deliveryStaff.getEmployeeCode())%>)</strong></div><div class="field"><span class="label">SĐT:</span><strong><%=Ui.h(deliveryStaff.getPhone())%></strong></div></div></div>
</section>
<div class="actions no-print"><button class="primary" type="button" data-print>In</button><a class="button" href="ApproveAndExportOrderUI3.jsp?idOrder=<%=order.getId()%>">Quay lại</a></div>
</main></body></html>
