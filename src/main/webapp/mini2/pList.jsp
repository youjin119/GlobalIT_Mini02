<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>work</title>
	<link rel="stylesheet" href="/mini2/style/pList.css">

</head>
<body>
 <h1 id="toIndexLink">기사 목록 띄우기</h1>
 	<div class="tagsContainer">
		<div class="tagSelector tagSelected cursorSetPointer">#전체</div>
		<div class="tagSelector cursorSetPointer">#여행</div>
		<div class="tagSelector cursorSetPointer">#아시아</div>
		<div class="tagSelector cursorSetPointer">#아프리카</div>
		<div class="tagSelector cursorSetPointer">#아메리카</div> 	
		<div class="tagSelector cursorSetPointer">#오세아니아</div> 	
 	</div>
 <br>
 <form method="post" action="/pList.do">
 	<!-- <button type="submit" id="plusButton" name="click" value="insert">insert</button> -->
 	<button type="submit" id="updateButton" name="click" value="update">새로고침</button>
 	<button type="reset" id="delButton" name="click" value="delete">test</button>
 </form>
 
 <div class="boardListsContainer">
	 <c:choose>
		 <c:when test="${ empty boardLists }">
		 	등록된 게시물이 없습니다^^*
		 </c:when>
		 
		 <c:otherwise>
			 <c:forEach items="${boardLists }" var="post">
			    <div class="bordertest">
			    	<form method="get" action="/pView.do" id="viewForm${post.postID}">
						<c:choose>
						    <c:when test="${post.content == null}">
						        <img src="/mini2/imgs/default.png" alt="로드실패" class="mainImgs">
						    </c:when>
						    <c:otherwise>
						        <img src="/uploads/${post.content}" alt="로드실패" class="mainImgs">
						    </c:otherwise>
						</c:choose>
				        <div>
				            <h2 id="viewLink${post.postID}" class="viewSelector">${post.title}</h2>
				            <p>${post.country}</p>
				            <p>${post.tag}</p>
				        </div>
				        <input type="hidden" name="postID" value="${post.postID}">
			    	</form>
			    </div>  
			 </c:forEach>
		 </c:otherwise>
	 </c:choose>
	<div>
		페이징 처리<br>
		${ map.pagingImg }
	</div>
 </div> <!-- 리스트 불러옴 -->
	
	<div id="absolutePanel">
		테스트
	 	<c:forEach items="${panelLists }" var="best">
			<div class="aP">
				<c:choose>
				    <c:when test="${best.content == null}">
				        <img src="/mini2/imgs/default.png" alt="로드실패" class="panelImg">
				    </c:when>
				    <c:otherwise>
				        <img src="/uploads/${best.content}" alt="로드실패" class="panelImg">
				    </c:otherwise>
				</c:choose>
				<br>
				${best.title}<br>
			</div>
		</c:forEach>
	</div>

	<script src="../mini2/js/pList.js?v=1.2"></script>
</body>
</html>