<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
<style>
	body{margin:0; padding:0; text-align:center;}
    .bordertest{
        border: 1px solid darkblue;
        clear: both;
        margin-left: 20vh;
        margin-right: 20vh;
        max-width: 800px;
    }
    .mainImgs{
    	float: left;
    	width: 150px;
        height: 150px;
        line-height: 150px;
        border: 1px solid black;
    }
		#absolutePanel{
			position: fixed;
			right: 20px;
			bottom: 10vh;
			width: 200px;
			height: 500px;
			background-color: skyblue;
			opacity: 70%;
		}
		.panelImg{
		    width: 100px;
       		height: 100px;
       		line-height: 100px;
       		border: 1px dashed white;
		}
		.viewSelector{
			cursor: pointer;
		}
		p{
			pointer-events: none;
		}

    /* 스마트폰 모드 (최대 너비 576px) */
    @media (max-width: 576px) {
    /* 스마트폰 모드에 적용할 CSS 스타일 */
        .bordertest{margin: 0;}
        .mainImgs{border-color: yellow;}
    }

    /* 태블릿 및 스마트폰 모드 (최대 너비 768px) */
    @media (max-width: 768px) {
    /* 태블릿 및 스마트폰 모드에 적용할 CSS 스타일 */
        .bordertest{margin: 0;}
        .mainImgs{border-color: red;}
    }
    
</style>
</head>
<body>
 <h1 id="toIndexLink">기사 목록 띄우기</h1>
 <form method="post" action="/pList.do">
 	<button type="submit" id="plusButton" name="click" value="insert">insert</button>
 	<button type="submit" id="updateButton" name="click" value="update">update</button>
 	<button type="reset" id="delButton" name="click" value="delete">delete</button>
 </form>
 <c:choose>
 <c:when test="${ empty boardLists }">
 	등록된 게시물이 없습니다^^*
 </c:when>
 
 <c:otherwise>
	 <c:forEach items="${boardLists }" var="post">
	    <div class="bordertest">
	    	<form method="post" action="/pView.do" id="viewForm${post.idx}">
		        <img src="../mini2/imgs/default.png" alt="로드실패" class="mainImgs">
		        <div>
		            <h2 id="viewLink${post.idx}" class="viewSelector">${post.title}</h2>
		            <p>${post.country}</p>
		            <p>${post.tag}</p>
		            <p>기사내용 : ${post.content}<br>
		            	글 번호 : ${post.idx}</p>
		        </div>
		        <input type="hidden" name="postID" value="${post.idx}">
	    	</form>
	    </div>  
	 </c:forEach>
 </c:otherwise>
 
 </c:choose>
	<div>
		<%-- ${ map.pagingImg } --%>
		페이징 처리<br>
		${ map.pagingImg }
	</div>
	
	<div id="absolutePanel">
		테스트
	 	<c:forEach items="${panelLists }" var="best">
			<div class="aP">
				<img src="/" alt="사진 추가 예정" class="panelImg"><br>
				${best.title}<br>
			</div>
		</c:forEach>
	</div>

	<script src="../mini2/js/pList.js"></script>
</body>
</html>