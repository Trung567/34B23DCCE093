<%@ page contentType="text/html; charset=UTF-8" import="model.*,web.Ui" %>
<% Customer3 customer=(Customer3)session.getAttribute("member"); %>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Trang chủ khách hàng</title></head><body>
<main class="panel home-panel"><h1>Trang chủ khách hàng</h1>
<div class="greeting"><p><strong>Xin chào, <%=Ui.h(Ui.name(customer))%></strong></p><p class="muted">Mã khách hàng: <%=Ui.h(customer.getCustomerCode())%></p></div>
<div class="actions"><a class="button primary" href="InvoiceListUI3.jsp">Theo dõi hóa đơn</a></div>
</main>
<%@ include file="WEB-INF/logout.jspf" %>
</body></html>
