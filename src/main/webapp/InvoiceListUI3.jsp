<%@ page contentType="text/html; charset=UTF-8" import="model.*,dao.*,web.Ui" %>
<%
Customer3 customer=(Customer3)session.getAttribute("member");
Order3[] listOrder;
try(InvoiceDAO3 dao=new InvoiceDAO3()) { listOrder=dao.getInvoicesOfCustomer(customer.getId()); }
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Danh sách hóa đơn của tôi</title></head><body>
<main class="panel list-panel invoice-list-panel"><h1>Danh sách hóa đơn của tôi</h1>
<div class="meta"><div><div class="field"><span class="label">Mã khách hàng:</span><strong><%=Ui.h(customer.getCustomerCode())%></strong></div><div class="field"><span class="label">Họ tên:</span><strong><%=Ui.h(Ui.name(customer))%></strong></div></div><div class="field"><span class="label">Tổng số hóa đơn:</span><strong><%=listOrder.length%></strong></div></div>
<div class="table-wrap"><table><thead><tr><th scope="col">TT</th><th scope="col">Mã hóa đơn</th><th scope="col">Ngày lập</th><th scope="col">Mã đơn hàng</th><th scope="col">Ngày đặt</th><th scope="col">Tổng tiền (VNĐ)</th><th scope="col">Trạng thái đơn</th><th scope="col">Chi tiết</th></tr></thead><tbody>
<% for(int i=0;i<listOrder.length;i++) { Order3 order=listOrder[i]; %>
<tr><td class="center"><%=i+1%></td><td><%=Ui.h(order.getInvoice().getInvoiceNumber())%></td><td class="center"><%=Ui.date(order.getInvoice().getIssuedAt())%></td><td><%=Ui.h(order.getOrderNumber())%></td><td class="center"><%=Ui.date(order.getOrderedAt())%></td><td class="number"><%=Ui.money(order.getTotalAmount())%></td><td class="center"><%=Ui.h(order.getStatus())%></td><td class="center"><a href="InvoiceDetailUI3.jsp?idInvoice=<%=order.getInvoice().getId()%>" aria-label="Xem hóa đơn <%=Ui.h(order.getInvoice().getInvoiceNumber())%>">(click để xem)</a></td></tr>
<% } %>
</tbody></table></div>
<% if(listOrder.length==0) { %><p class="note" role="status">Bạn chưa có hóa đơn nào</p><% } %>
<div class="actions"><a class="button" href="CustomerMainUI3.jsp">Về trang chủ</a></div>
</main></body></html>
