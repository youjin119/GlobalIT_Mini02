<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>work</title>
<style>
	body{margin:0; padding:0; text-align:center;}
	
    .bordertest{
        display: flex;
        flex-direction: row;
        border: 1px solid darkblue;
        /* margin-left: 20vh;
        margin-right: 20vh; */
        margin: 40px 20vh;
    }
	.bordertest > form { /* 수정: form 태그에 스타일 적용 */
	    display: flex;
	    flex-direction: row; /* 추가: 가로 방향으로 정렬 */
	}
	
	.bordertest > form > div { /* 수정: form 태그 내부의 div 태그에 스타일 적용 */
	    display: flex;
	    flex-direction: column;
	}
    .mainImgs{
    	width: 150px;
        height: 150px;
        line-height: 150px;
        border: 1px solid black;
        margin-right: 10vh;
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
		.tagsContainer{
			display: flex;
			justify-content: center;
		}
		.tagSelector{
			margin : 5px;
			padding-left: 10px;
			padding-right: 10px;
			background-color: #f2f2f2;
			border-radius: 30px;
		}
		.tagSelected{
			background-color: pink;
		}
		.cursorSetPointer{
			cursor: pointer;
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
						        <img src="mini2/imgs/default.png" alt="로드실패" class="mainImgs">
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
				<img src="#" alt="사진 추가 예정" class="panelImg"><br>
				${best.title}<br>
			</div>
		</c:forEach>
	</div>

	<script src="../mini2/js/pList.js?v=1.2"></script>
</body>
</html>