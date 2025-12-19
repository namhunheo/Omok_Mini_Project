<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Omok - Start</title>
    <style>
        html, body { height: 100%; margin: 0; }
        .screen {
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            background: url("<%= request.getContextPath() %>/assets/img/start_bg.png") center/cover no-repeat;
        }
        .start-btn {
            border: 0;
            cursor: pointer;
            padding: 16px 50px;
            font-size: 28px;
            font-weight: 800;
            border-radius: 10px;
            background: rgba(200, 230, 255, 0.85);
            box-shadow: 0 6px 18px rgba(0,0,0,.25);
        }
        .start-btn:active { transform: translateY(1px); }
    </style>
</head>
<body>
<div class="screen">
    <!-- 클릭하면 /login 이동 -->
    <form action="<%= request.getContextPath() %>/login" method="get">
        <button class="start-btn" type="submit">시작하기</button>
    </form>
</div>
</body>
</html>
