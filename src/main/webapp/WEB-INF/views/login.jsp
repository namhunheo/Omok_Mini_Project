<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>오목 로그인</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .box { max-width: 400px; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }
        .row { margin-bottom: 12px; }
        input { width: 100%; height: 38px; padding: 0 10px; }
        button { height: 40px; padding: 0 16px; font-weight: bold; }
        .error { color: red; margin-bottom: 10px; }
    </style>
</head>
<body>

<h2>로그인</h2>

<div class="box">

    <!-- 에러 메시지 -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="row">
            <input type="text" name="loginId" placeholder="아이디">
        </div>

        <div class="row">
            <input type="password" name="password" placeholder="비밀번호">
        </div>

        <button type="submit">로그인</button>
    </form>

    <br>

    <!-- 회원가입 이동 -->
    <a href="${pageContext.request.contextPath}/register">회원가입</a>

</div>

</body>
</html>
