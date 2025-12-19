<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>회원가입</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .box { max-width: 420px; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }
        .row { margin-bottom: 12px; }
        label { display: block; margin-bottom: 6px; font-weight: 700; }
        input { width: 100%; height: 38px; padding: 0 10px; border: 1px solid #ccc; border-radius: 8px; }
        button { height: 40px; padding: 0 16px; border: 0; border-radius: 8px; cursor: pointer; font-weight: 800; }
        .btn-primary { background: #2d7ff9; color: white; }
        .btn-link { background: transparent; color: #2d7ff9; text-decoration: underline; }
        .error { color: #d60000; font-weight: 800; margin-bottom: 12px; }
        .hint { color: #666; font-size: 13px; margin-top: 4px; }
    </style>
</head>
<body>

<h2>회원가입</h2>

<div class="box">

    <%-- 에러 메시지 출력 (RegisterServlet에서 request.setAttribute("error", "...") 해주면 됨) --%>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error"><%= error %></div>
    <%
        }
    %>

    <%-- 값 유지용 (실패 시 forward로 돌아왔을 때 입력했던 값 다시 보여주기) --%>
    <%
        String loginId = (String) request.getAttribute("loginId");
        String nickname = (String) request.getAttribute("nickname");
        String profileImg = (String) request.getAttribute("profileImg");

        if (loginId == null) loginId = "";
        if (nickname == null) nickname = "";
        if (profileImg == null) profileImg = "";
    %>

    <form method="post" action="<%= request.getContextPath() %>/register">
        <div class="row">
            <label>아이디</label>
            <input name="loginId" value="<%= loginId %>" placeholder="예) test1" />
        </div>

        <div class="row">
            <label>비밀번호</label>
            <input type="password" name="password" placeholder="비밀번호 입력" />
            <div class="hint">※ 저장할 때는 해시로 변환해서 DB에 저장하는 게 정석</div>
        </div>

        <div class="row">
            <label>닉네임</label>
            <input name="nickname" value="<%= nickname %>" placeholder="예) minseok" />
        </div>

        <div class="row">
            <label>프로필 이미지 URL (선택)</label>
            <input name="profileImg" value="<%= profileImg %>" placeholder="https://..." />
            <div class="hint">나중에 S3/업로드로 바꿔도 됨 (지금은 기능 구현용)</div>
        </div>

        <div class="row" style="display:flex; gap:10px; align-items:center;">
            <button class="btn-primary" type="submit">가입하기</button>

            <%-- 로그인으로 이동 --%>
            <a class="btn-link" href="<%= request.getContextPath() %>/login">로그인으로</a>
        </div>
    </form>

</div>

</body>
</html>
