<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pdto.title}</title>
    <link rel="stylesheet" href="/mini2/style/pView.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function(){
        $(".like-btn").click(function(e){
            e.preventDefault(); // 기본 폼 제출 방지

            var postID = $(this).siblings("input[name='postID']").val(); // postID 가져오기
            var button = $(this);
            
            $.post("/pLike.do", { postID: postID }, function(response){
                // 좋아요 상태가 바뀌었으면 버튼의 텍스트를 업데이트
                console.log(response);
                if (response.isLiked) {
                    button.addClass("liked");
                    button.html("❤️"); // 좋아요 상태로 변경
                } else {
                    button.removeClass("liked");
                    button.html("🤍"); // 좋아요 취소 상태로 변경
                }

                // 좋아요 수를 갱신
                $(".like-count").text(response.likeCount);
            });
        });
    });
</script>
</head>
<body class="d-flex-column min-vh-100">
    <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
            <jsp:include page="headerlogout.jsp" />
        </c:when>
        <c:otherwise>
            <jsp:include page="headerlogin.jsp" />
        </c:otherwise>
    </c:choose>

    <div class="main_content">
        <div class="post-container">
            <div class="title">${pdto.title}</div>
            <div class="meta">${pdto.country} 
                <span class="divider"></span>
                작성일: ${pdto.postDate}
            </div>

            <c:set var="likeCountKey" value="likeCount_${pdto.postID}" />

            <div class="top-icons">
                <span>
                    <c:choose>
                        <c:when test="${not empty sessionScope.loginUser}">
                            <form method="post" action="/pLike.do">
                                <input type="hidden" name="postID" value="${pdto.postID}">
                                <button type="submit" class="like-btn 
                                    ${userLiked ? 'liked' : ''}">
                                    <c:choose>
                                        <c:when test="${userLiked}">
                                            ❤️
                                        </c:when>
                                        <c:otherwise>
                                            🤍
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <a href="/login.do?redirectURL=/pView.do?postID=${pdto.postID}" class="like-btn">🤍</a>
                        </c:otherwise>
                    </c:choose>

                    <!-- 좋아요 수는 항상 표시 -->
                    <span class="like-count">
                        ${likeCount}
                </span>
                <span>👁️ ${pdto.vcount}</span>
            </div>

            <div class="tag">
                <c:if test="${not empty pdto.tag}">
                    <c:forTokens var="t" items="${pdto.tag}" delims=",">
                        <span class="badge badge-light">${t}</span>
                    </c:forTokens>
                </c:if>
            </div>

            <hr>

            <div class="content">${pdto.content}</div>

            <div class="writer">작성자: ${pdto.name}</div>

            <div class="bottom-icons">
                <c:if test="${loginUser.id == pdto.id}">
                    <span><a href="/pEdit.do?postID=${pdto.postID}">🛠️<br>수정</a></span>
                    <span><a href="/pDelete.do?postID=${pdto.postID}" onclick="return confirm('정말 삭제하시겠습니까?')">🗑️<br>삭제</a></span>
                </c:if>
                <span><a href="/pList.do?pageNum=${param.pageNum}&tag=${fn:replace(param.tag, '#', '%23')}">📋<br>목록</a></span>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>