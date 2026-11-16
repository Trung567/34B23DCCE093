<%@ page contentType="text/html; charset=UTF-8" import="model.*,web.Ui" %>
<%
if(session.getAttribute("member") instanceof Customer3) { response.sendRedirect("CustomerMainUI3.jsp"); return; }
if(session.getAttribute("member") instanceof SalesStaff3) { response.sendRedirect("SalesStaffMainUI3.jsp"); return; }
%>
<!doctype html><html lang="vi"><head><%@ include file="WEB-INF/head.jspf" %><title>Đăng nhập</title></head><body>
<main class="panel login-panel"><h1>Đăng nhập</h1>
<% if(request.getParameter("error")!=null) { %><p class="message error" role="alert">Sai tên đăng nhập hoặc mật khẩu.</p><% } %>
<% if(request.getParameter("timeout")!=null) { %><p class="note">Vui lòng đăng nhập để tiếp tục.</p><% } %>
<form method="post" action="login">
<input type="hidden" name="csrf" value="<%=Ui.h(session.getAttribute("csrf"))%>">
<label for="username">Tên đăng nhập<input id="username" name="username" required maxlength="255" autocomplete="username" autofocus></label>
<label for="password">Mật khẩu<input id="password" name="password" type="password" required maxlength="255" autocomplete="current-password"></label>
<div class="actions"><button class="primary" type="submit">Đăng nhập</button></div>
</form></main></body></html>
