<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
</head>
<body>
<h2>홈 화면</h2>
	<form action="/logout.do">
		<table>
			<tr>
				<td>환영홥니다! ${loginUser.name}님</td>
			</tr>
			<tr>
				<td>
					<c:if test="${not empty sessionScope.loginUser}">
    					<a href="logout.do">로그아웃 |</a>
					</c:if>
					<c:if test="${not empty sessionScope.loginUser}">
    					<a href="memberEdit.do?id=${loginUser.id}">화원정보수정</a>
					</c:if>
					
				</td>
			</tr>
		</table>
	</form>
	<a href="/pList.do">여행기사 </a><br>
	<a href="/pWrite.do">기사 쓰기</a>
</body>
</html>