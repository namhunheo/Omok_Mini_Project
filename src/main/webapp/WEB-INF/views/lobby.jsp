<%--
  Created by IntelliJ IDEA.
  User: leeyj
  Date: 25. 12. 14.
  Time: 오후 2:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>로비</title>
</head>
<body>

  <h2>로비</h2>

  <ul>
    <c:forEach var="room" items="${rooms}">
      <li>
        방 ${room.roomId}
        <button onclick="location.href='/omok/lobby/enter?roomId=${room.roomId}'">입장</button>
      </li>
    </c:forEach>
  </ul>

  <button onclick="location.href='/omok/lobby/quick-enter'">빠른 방 입장</button>

  <form method="post" action="/omok/lobby/create">
    <button type="submit">방 생성</button>
  </form>

  <h3>상위 랭크 </h3>
  <ul>
    <c:forEach var="ranker" items="${rankingList}">
      <li>
          ${ranker.rank}위 | 닉네임: ${ranker.nickname} | 점수: ${ranker.rating}
      </li>
    </c:forEach>
    <c:if test="${empty rankingList}">
      <li>랭킹 데이터가 없습니다.</li>
    </c:if>
  </ul>


</body>
</html>
