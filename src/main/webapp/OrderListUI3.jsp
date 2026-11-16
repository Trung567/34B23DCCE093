<%@ page contentType="text/html; charset=UTF-8" import="model.*,dao.*,web.Ui" %>
<%
SalesStaff3 salesStaff=(SalesStaff3)session.getAttribute("member"); Order3[] listOrder;
try(OrderDAO3 dao=new OrderDAO3()) { listOrder=dao.getUnexportedOrders(); }
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Danh sách đơn hàng chưa xuất</title></head><body>
<main class="panel list-panel order-list-panel"><h1>Danh sách đơn hàng chưa xuất</h1>
<div class="meta"><div class="field"><span class="label">Nhân viên:</span><strong><%=Ui.h(Ui.name(salesStaff))%> (<%=Ui.h(salesStaff.getEmployeeCode())%>)</strong></div><div class="field"><span class="label">Số đơn chưa xuất:</span><strong><%=listOrder.length%></strong></div></div>
<div class="table-wrap"><table><thead><tr><th scope="col">TT</th><th scope="col">Mã đơn hàng</th><th scope="col">Ngày đặt</th><th scope="col">Địa chỉ giao hàng</th><th scope="col">Tổng tiền (VNĐ)</th><th scope="col">Trạng thái</th><th scope="col">Xử lý</th></tr></thead><tbody>
<% for(int i=0;i<listOrder.length;i++) { Order3 order=listOrder[i]; %>
<tr><td class="center"><%=i+1%></td><td><%=Ui.h(order.getOrderNumber())%></td><td class="center"><%=Ui.date(order.getOrderedAt())%></td><td><%=Ui.h(order.getDeliveryAddress())%></td><td class="number"><%=Ui.money(order.getTotalAmount())%></td><td class="center"><%=Ui.h(order.getStatus())%></td><td class="center"><a href="ApproveAndExportOrderUI3.jsp?idOrder=<%=order.getId()%>" aria-label="Xử lý đơn hàng <%=Ui.h(order.getOrderNumber())%>">(click để xử lý)</a></td></tr>
<% } %></tbody></table></div>
<% if(listOrder.length==0) { %><p class="note" role="status">Không có đơn hàng cần xử lý</p><% } %>
<div class="actions"><a class="button" href="SalesStaffMainUI3.jsp">Về trang chủ</a></div>
</main></body></html>
