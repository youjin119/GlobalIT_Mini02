<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pdto.title}</title>
<style>
    body { font-family: sans-serif; text-align: center; }
    .post-container { max-width: 800px; margin: auto; padding: 20px; }
    .title { font-size: 2em; font-weight: bold; }
    .meta { color: gray; margin-bottom: 10px; }
    .icons { margin: 10px; }
    .icons span { margin: 0 10px; font-size: 1.2em; }
    .tag { color: #555; }
    .content { text-align: center; margin-top: 30px; line-height: 1.6em; }
    .writer { margin-top: 50px; text-align: left; font-style: italic; }
</style>
</head>
<body>

<div class="post-container">

    <div class="title">${pdto.title}</div>
    <div class="meta">${pdto.country}</div>

    <div class="icons">
        <span>🤍</span>  <!-- 좋아요 버튼은 추후 기능 추가 -->
        <span>👁️ ${pdto.vcount}회 조회</span>
    </div>

    <div style=text-align:left font-weight: bold;">
        작성일: ${pdto.postDate}
    </div>

    <div class="tag">
        <c:if test="${not empty pdto.tag}">
            <c:forTokens var="t" items="${pdto.tag}" delims=",">
                #${t} 
            </c:forTokens>
        </c:if>
    </div>

    <hr>

    <!-- 대표 이미지 -->
    <c:if test="${not empty imgList}">
        <img src="/upload/${imgList[0].imgid}" alt="대표 이미지" width="100%" style="max-height: 400px; object-fit: cover;">
    </c:if>

    <!-- 본문 -->
    <div class="content">${pdto.content}</div>

    <div class="writer">
        작성자: ${pdto.name} 
    </div>

</div>

</body>
</html>
