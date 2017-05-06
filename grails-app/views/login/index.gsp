<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main">
</head>

<body>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Welcome to Sentiment Analyzer</h1>

        <p>
            Congratulations, you can now analyze the sentiments from inside your inbox very easily. You are just
            one step before you get there. Sign in with your google account and analyze the emotions hidden in the content
            emailed to you from various sources.
        </p>

        <div align="center">
            <g:form name="signIn" action="auth" controller="login">
                <g:submitButton name="Sign In" value="Sign in with Google" class='btn btn-default btn-success btn-lg'> </g:submitButton>
            </g:form>

        </div>
    </section>
</div>

</body>
</html>