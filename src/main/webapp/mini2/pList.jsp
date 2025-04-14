<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page import="java.net.URLEncoder" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">


	<title>여기저기</title>
	<link rel="stylesheet" href="/mini2/style/pList.css?v=6.0.2">

</head>
<body class="d-flex flex-column min-vh-100">
<!-- navbar------------------------------------------------------- -->
	<c:choose>
	  <c:when test="${not empty sessionScope.loginUser}">
	    <!--login 성공하면 logout navbar 나옴  -->
	    <jsp:include page="headerlogout.jsp"></jsp:include>
	  </c:when>
	  <c:otherwise>
	    <!--login 하지 않을 경우 login navbar 나옴  -->
	    <jsp:include page="headerlogin.jsp"></jsp:include>
	  </c:otherwise>
	</c:choose>
	<!-- navbar end-->

	<div class="tagsContainer">
		<ul class="breadcrumb">    
		<%
			String tagUrl = "pList.do?pageNum=1&tag=";
			ArrayList<String> tags = 
					new ArrayList<>(Arrays.asList("#전체","#유럽","#아시아","#아프리카","#아메리카","#오세아니아"));
		%>    
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(0), "UTF-8") %>">#전체</a></li>
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(1), "UTF-8") %>">#유럽</a></li>
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(2), "UTF-8") %>">#아시아</a></li>
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(3), "UTF-8") %>">#아프리카</a></li>
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(4), "UTF-8") %>">#아메리카</a></li>
           <li><a href="<%= tagUrl + URLEncoder.encode(tags.get(5), "UTF-8") %>">#오세아니아</a></li>
           <li><a href="pList.do?pageNum=1&tag=%23여행">#여행</a></li>
		</ul>
	</div>
 
	<div class="boardListsContainer">
	 <c:choose>
		 <c:when test="${ empty boardLists }">
		 	<div id="noBoardLists">
			 	🧭🗺 앗, 아직 아무도 다녀가지 않았나봐요~ 🧭🗺
		 	</div>
		 </c:when>
		 <c:otherwise>
			 <c:forEach items="${boardLists }" var="post">
			    <div class="bordertest">
			    	<form method="get" action="/pView.do" id="viewForm${post.postID}" class="pListForm">
						<c:choose>
						    <c:when test="${post.content == null}">
						        <img src="/mini2/imgs/default.png" alt="기본 이미지" class="mainImgs">
						    </c:when>
						    <c:otherwise>
						        <img src="/uploads/${post.content}" alt="${post.title}" class="mainImgs">
						    </c:otherwise>
						</c:choose>
				        <div>
				            <h2 id="viewLink${post.postID}" class="viewSelector">${post.title}</h2>
				            <p>${post.country}</p>
				            <p>${post.tag} ${post.postID }</p>
				        </div>
				        <input type="hidden" name="postID" value="${post.postID}">
			    	</form>
			    </div>  
			 </c:forEach>
		 </c:otherwise>
	 </c:choose>
	<div class="pagingContainer">
		<div class="pageStyleSet">
			${ map.pagingImg }
		</div>
	</div>
 </div> <!-- 리스트 불러옴 -->
	
	<div id="absolutePanel">
		<div>New Recommend❣</div>
	 	<c:forEach items="${panelLists}" var="best">
			<div class="aP">
				<form method="get" action="/pView.do" id="aPForm${best.postID}" class="pListForm">
					<c:choose>
					    <c:when test="${best.content == null}">
					        <img src="/mini2/imgs/default.png" alt="기본 이미지" class="panelImg">
					    </c:when>
					    <c:otherwise>
					        <img src="/uploads/${best.content}" alt="${best.title}" class="panelImg">
					    </c:otherwise>
					</c:choose>
					<div class="apText">
						<div id="aPLink${best.postID}" class="aPSelector">${best.title}</div>
					</div>
					<input type="hidden" name="postID" value="${best.postID}">
					<!-- 페이지넘버  태그 넘기기 -->
				    <input type="hidden" name="pageNum" value="${map.pageNum}">
			        <input type="hidden" name="tag" value="${param.tag}">
			        <!--  -->
				</form>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	
	<script>
	const selectedTag = "<%= request.getParameter("tag") != null 
		&& !request.getParameter("tag").isEmpty() ? request.getParameter("tag") : "#전체" %>";
    </script>
	<script src="/mini2/js/pList.js?v=5.6"></script>
</body>
</html>