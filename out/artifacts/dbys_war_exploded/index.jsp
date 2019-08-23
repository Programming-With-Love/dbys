<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/17
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RequestDispatcher rd = request.getRequestDispatcher("/syhq");
    rd.forward(request, response);
%>