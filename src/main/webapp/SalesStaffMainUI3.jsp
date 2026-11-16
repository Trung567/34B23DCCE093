<%@ page contentType="text/html; charset=UTF-8" import="model.*,web.Ui" %>
<% SalesStaff3 salesStaff=(SalesStaff3)session.getAttribute("member"); %>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Trang chủ nhân viên bán hàng</title></head><body>
<main class="panel home-panel"><h1>Trang chủ nhân viên bán hàng</h1>
<div class="greeting"><p><strong>Xin chào, <%=Ui.h(Ui.name(salesStaff))%></strong></p><p class="muted">Mã nhân viên: <%=Ui.h(salesStaff.getEmployeeCode())%></p></div>
<div class="actions"><a class="button primary" href="OrderListUI3.jsp">Duyệt đơn hàng</a></div>
</main>
<%@ include file="WEB-INF/logout.jspf" %>
</body></html>
