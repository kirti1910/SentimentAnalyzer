
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<form id="authForm" name="authForm"
      action="${requestMap["baseUrl"]}" method="get" style="display: none">

<table>
    <tr height="100%">
        <td>
            <input type="hidden" name="scope" value="${requestMap['scope']}"/>
            <input type="hidden" name="access_type" value="${requestMap['access_type']}"/>
            <input type="hidden" name="include_granted_scopes" value="${requestMap['include_granted_scopes']}"/>
            <input type="hidden" name="state" value="${requestMap['state']}"/>
            <input type="hidden" name="redirect_uri" value="${requestMap['redirect_uri']}"/>
            <input type="hidden" name="response_type" value="${requestMap['response_type']}"/>
            <input type="hidden" name="client_id" value="${requestMap['client_id']}"/>
            <input type="submit" id="submit" name="submit" value="Submit"/>
        </td>
    </tr>
</table>

</form>


</body>

<script language="JavaScript" type="text/javascript">
    window.onload = function () {
        document.getElementById("submit").click();
    }
</script>



</body>
</html>