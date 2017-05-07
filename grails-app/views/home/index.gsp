<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
</head>

<body>

<g:if test="${code}">
    <%
        out << "User code is ${code}"
    %>
</g:if>



</body>
</html>