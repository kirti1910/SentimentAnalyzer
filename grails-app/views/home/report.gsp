<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>

    <style>
    .Negative {
        color: red;
    }
    .Positive {
        color: green;
    }
    .Neutral {
         color: yellow;
    }
    </style>

</head>

<body>

<div align="center"> <label>Overall Score is </label> <span class="${overallScore}"> <b><i>${overallScore} </i></b></span></div>

<g:if test="${messages}">
    <div>
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>From</th>
                <th>Sensitivity</th>
                <th>Polarity</th>
                <th>Email Count</th>
                <th>Score</th>
            </tr>
            </thead>
            <g:each in="${messages}" var="msg" status="i">
                <tbody>
                <tr>
                    <th scope="row">${i+1}</th>
                    <td>${msg.getKey()}</td>
                    <td>${msg.getValue().subjectivity.round(2)}</td>
                    <td>${msg.getValue().polarity.round(2)}</td>
                    <td>${msg.getValue().emails}</td>
                    <td>${msg.getValue().score}</td>
                </tr>
                </tbody>

            </g:each>

        </table>
    </div>
</g:if>

</body>
</html>