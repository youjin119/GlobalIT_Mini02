<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>post viewer</title>
</head>
<body>
	<h2>Viewer</h2>
	<p>
		${postID }번째 글을 클릭했습니다.
	</p>
	<form method="get" action="/pList.do">
	<button type="submit">돌아가기</button>
	</form>
</body>
</html>