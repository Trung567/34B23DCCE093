<%@ page contentType="text/html; charset=UTF-8" import="model.*,dao.*,web.Ui" %>
<%
SalesStaff3 salesStaff=(SalesStaff3)session.getAttribute("member");
Order3 order; DeliveryStaff3[] listDeliveryStaff;
try(OrderDAO3 dao=new OrderDAO3()) { order=dao.getOrderDetail(Ui.id(request.getParameter("idOrder"))); }
if(order==null) { response.sendError(404); return; }
try(OrderLineDAO3 dao=new OrderLineDAO3()) { order.setOrderLines(dao.getLinesOfOrder(order.getId())); }
try(ProductDAO3 dao=new ProductDAO3()) { for(OrderLine3 line:order.getOrderLines()) line.setProduct(dao.getProductOfLine(line.getId())); }
try(DeliveryStaffDAO3 dao=new DeliveryStaffDAO3()) { listDeliveryStaff=dao.getAllDeliveryStaff(); }
String error=(String)session.getAttribute("error"); session.removeAttribute("error");
boolean exported=!"Chờ duyệt".equals(order.getStatus());
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Duyệt đơn hàng và xuất hàng</title></head><body>
<main class="panel detail-panel approval-panel"><h1>Duyệt đơn hàng và xuất hàng</h1>
<% if(error!=null) { %><p class="message error" role="alert"><%=Ui.h(error)%></p><% } %>
<% if(exported) { %><p class="message" role="status">Đơn hàng đã xuất hoặc không còn được phép xử lý.</p><% } %>
<section class="section"><h2>1. Thông tin đơn hàng</h2>
<div class="fields-three"><div><div class="field"><span class="label">Mã đơn hàng:</span><strong><%=Ui.h(order.getOrderNumber())%></strong></div><div class="field"><span class="label">Ngày đặt hàng:</span><strong><%=Ui.dateTime(order.getOrderedAt())%></strong></div></div>
<div><div class="field"><span class="label">Trạng thái:</span><strong><%=Ui.h(order.getStatus())%></strong></div><div class="field"><span class="label">Ngày duyệt:</span><strong><%=Ui.dateTime(order.getApprovedAt())%></strong></div></div>
<div><div class="field"><span class="label">Địa chỉ giao hàng:</span><strong><%=Ui.h(order.getDeliveryAddress())%></strong></div></div></div>
</section>
<section class="section"><h2>2. Danh sách hàng hóa cần đóng gói</h2>
<div class="table-wrap"><table><thead><tr><th scope="col">TT</th><th scope="col">Mã sản phẩm</th><th scope="col">Tên sản phẩm</th><th scope="col">Đơn vị tính</th><th scope="col">SL đặt</th><th scope="col">Tồn kho</th><th scope="col">Đơn giá (VNĐ)</th><th scope="col">Thành tiền (VNĐ)</th></tr></thead><tbody>
<% for(int i=0;i<order.getOrderLines().length;i++) { OrderLine3 line=order.getOrderLines()[i]; %>
<tr><td class="center"><%=i+1%></td><td><%=Ui.h(line.getProduct().getProductCode())%></td><td><%=Ui.h(line.getProduct().getProductName())%></td><td class="center"><%=Ui.h(line.getProduct().getUnitOfMeasure())%></td><td class="number"><%=line.getQuantity()%></td><td class="number <%=line.getProduct().getStockQuantity()<line.getQuantity()?"error":""%>"><%=line.getProduct().getStockQuantity()%></td><td class="number"><%=Ui.money(line.getUnitPrice())%></td><td class="number"><%=Ui.money(line.getLineTotal())%></td></tr>
<% } %></tbody></table></div><p class="total">Tổng tiền: <%=Ui.money(order.getTotalAmount())%> VNĐ</p></section>
<section class="section"><h2>3. Nhân viên giao hàng nhận hàng và hóa đơn</h2>
<form action="approve-export" method="post" data-approval-form>
<input type="hidden" name="csrf" value="<%=Ui.h(session.getAttribute("csrf"))%>"><input type="hidden" name="idOrder" value="<%=order.getId()%>">
<div class="delivery-select"><label for="deliveryStaff">Chọn nhân viên giao hàng:</label><select id="deliveryStaff" name="idDeliveryStaff" <%=exported?"disabled":""%>>
<option value="">-- Chọn nhân viên giao hàng --</option>
<% for(DeliveryStaff3 staff:listDeliveryStaff) { %><option value="<%=staff.getId()%>"><%=Ui.h(staff.getEmployeeCode()+" - "+Ui.name(staff)+" - "+staff.getPhone())%></option><% } %>
</select></div>
<div class="actions"><a class="button" href="OrderListUI3.jsp">Quay lại</a><% if(!exported) { %><button class="primary" type="submit">Xác nhận duyệt và xuất hàng</button><% } else { %><a class="button primary" href="PrintInvoiceUI3.jsp?idOrder=<%=order.getId()%>">In hóa đơn và phiếu xuất</a><% } %></div>
</form></section>
</main></body></html>
