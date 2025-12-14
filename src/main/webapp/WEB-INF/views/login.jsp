<%--
  Created by IntelliJ IDEA.
  User: leeyj
  Date: 25. 12. 14.
  Time: 오전 1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>오목 로그인</title>
</head>
<body>

    <h2>로그인</h2>

    <form method="post" action="/omok/login">
        <input type="text" name="id" placeholder="아이디"><br>
        <input type="password" name="pw" placeholder="비밀번호"><br>
        <button type="submit">로그인</button>
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

</body>
</html>
