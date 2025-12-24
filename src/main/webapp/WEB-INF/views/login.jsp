<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Omok Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css">
</head>
<body>

<%
    String error = (String) request.getAttribute("error");
    String loginIdVal = (String) request.getAttribute("loginId");
    if (loginIdVal == null) loginIdVal = "";
%>

<div class="screen">
    <div class="ui-anchor">

        <!-- ✅ login-form은 레이아웃용 컨테이너로만 사용 (form 중첩 제거) -->
        <div class="login-form">

            <!-- 왼쪽: 일반 로그인 form -->
            <div class="col left">
                <form method="post" action="${pageContext.request.contextPath}/login">

                    <div class="error <%= (error != null ? "show" : "") %>">
                        <%= (error != null ? error : "") %>
                    </div>

                    <input class="input id"
                           type="text"
                           name="loginId"
                           value="<%= loginIdVal %>"
                           placeholder="아이디"
                           autocomplete="username"/>

                    <input class="input pw"
                           type="password"
                           name="password"
                           placeholder="비밀번호"
                           autocomplete="current-password"/>

                    <button class="login-btn" type="submit" aria-label="로그인">
                        <img src="${pageContext.request.contextPath}/static/img/loginButton.png"
                             alt="로그인"/>
                    </button>
                </form>
            </div>

            <!-- 오른쪽: 비회원/회원가입 -->
            <div class="col right">

                <!-- ✅ 비회원 로그인: 별도 form (중첩 아님) -->
                <form method="post" action="${pageContext.request.contextPath}/login">
                    <input type="hidden" name="guestLogin" value="true">
                    <button type="submit" class="guestlogin-btn" aria-label="비회원로그인">
                        <img src="${pageContext.request.contextPath}/static/img/guestButton.png"
                             alt="비회원로그인"/>
                    </button>
                </form>

                <!-- 회원가입 -->
                <a class="register-btn"
                   href="${pageContext.request.contextPath}/register"
                   aria-label="회원가입">
                    <img src="${pageContext.request.contextPath}/static/img/registerButton.png"
                         alt="회원가입"/>
                </a>

            </div>
        </div>
    </div>
</div>

</body>
</html>
