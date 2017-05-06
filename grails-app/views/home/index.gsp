<%--
  Created by IntelliJ IDEA.
  User: ttnd
  Date: 6/5/17
  Time: 1:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

<g:if test="${code}">
    <%
        out << "User code is ${code}"
    %>
</g:if>



</body>
</html>