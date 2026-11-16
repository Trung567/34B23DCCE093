<%@ page contentType="text/html; charset=UTF-8" import="model.*,dao.*,web.Ui" %>
<%
Customer3 customer=(Customer3)session.getAttribute("member");
Order3 order; DeliveryStaff3 deliveryStaff;
try(InvoiceDAO3 dao=new InvoiceDAO3()) { order=dao.getInvoiceDetail(Ui.id(request.getParameter("idInvoice")),customer.getId()); }
if(order==null) { response.sendError(404); return; }
try(ExportNoteDAO3 dao=new ExportNoteDAO3()) { deliveryStaff=dao.getExportNoteOfOrder(order.getId()); }
if(deliveryStaff!=null) order.getInvoice().setExportNote(deliveryStaff.getExportNotes()[0]);
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Chi tiết hóa đơn</title></head><body>
<main class="panel detail-panel invoice-detail-panel"><h1>Chi tiết hóa đơn</h1>
<section class="section"><h2>1. Thông tin hóa đơn và đơn hàng</h2>
<div class="fields-three invoice-info"><div><div class="field"><span class="label">Mã hóa đơn:</span><strong><%=Ui.h(order.getInvoice().getInvoiceNumber())%></strong></div><div class="field"><span class="label">Ngày lập hóa đơn:</span><strong><%=Ui.dateTime(order.getInvoice().getIssuedAt())%></strong></div></div>
<div><div class="field"><span class="label">Mã đơn hàng:</span><strong><%=Ui.h(order.getOrderNumber())%></strong></div><div class="field"><span class="label">Ngày đặt hàng:</span><strong><%=Ui.dateTime(order.getOrderedAt())%></strong></div></div>
<div><div class="field"><span class="label">Trạng thái đơn:</span><strong><%=Ui.h(order.getStatus())%></strong></div><div class="field"><span class="label">Ngày duyệt:</span><strong><%=Ui.dateTime(order.getApprovedAt())%></strong></div></div>
<div class="field address-row"><span class="label">Địa chỉ giao hàng:</span><strong><%=Ui.h(order.getDeliveryAddress())%></strong></div></div>
</section>
<section class="section"><h2>2. Danh sách hàng hóa</h2><%@ include file="WEB-INF/lines.jspf" %></section>
<section class="section"><h2>3. Thông tin giao hàng</h2>
<% if(deliveryStaff==null) { %><p class="note" role="status">Chưa xuất hàng</p><% } else { ExportNote3 export=deliveryStaff.getExportNotes()[0]; %>
<div class="delivery-fields"><div><div class="field"><span class="label">Mã phiếu xuất:</span><strong><%=Ui.h(export.getExportNumber())%></strong></div><div class="field"><span class="label">Ngày xuất hàng:</span><strong><%=Ui.dateTime(export.getExportedAt())%></strong></div></div><div><div class="field"><span class="label">Nhân viên giao hàng:</span><strong><%=Ui.h(Ui.name(deliveryStaff))%></strong></div><div class="field"><span class="label">SĐT nhân viên giao hàng:</span><strong><%=Ui.h(deliveryStaff.getPhone())%></strong></div></div></div>
<% } %></section>
<div class="actions"><a class="button" href="InvoiceListUI3.jsp">Quay lại</a></div>
</main></body></html>
