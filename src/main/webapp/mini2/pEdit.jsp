<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>여기저기 기사 수정</title>

  <!-- 외부 CSS 연결 -->
  <link rel="stylesheet" href="/mini2/style/pEdit.css">

  <!-- 기존 이미지 목록 스크립트 -->
  <script>
    const originalImages = [
      <c:forEach var="img" items="${imgList}" varStatus="status">
        "${img.imgid}"<c:if test="${!status.last}">,</c:if>
      </c:forEach>
    ];
  </script>

  <script src="/mini2/js/pEdit.js"></script>
</head>

<body>
<jsp:include page="headerlogout.jsp"></jsp:include>

  <h2 class="page-title">🛠️기사 수정</h2>

  <form name="pWriteFrm" method="post" action="/pEdit.do">
    <input type="hidden" name="postID" value="${pdto.postID}" />

    <table class="edit-table">
      <tr>
        <td class="label">기사제목 :</td>
        <td><input type="text" name="title" class="input-text large" value="${pdto.title}" /></td>
      </tr>
      <tr>
        <td class="label">기사 소개 태그 :</td>
        <td><input type="text" name="tag" class="input-text medium" value="${pdto.tag}" /></td>
      </tr>
      <tr>
        <td class="label">국가/지역 :</td>
        <td><input type="text" name="country" class="input-text medium" value="${pdto.country}" /></td>
      </tr>
      <tr>
        <td class="label">기사 내용 :</td>
        <td>
          <div id="editor" contenteditable="true">
            <c:out value="${pdto.content}" escapeXml="false"/>
          </div>
          <input type="hidden" name="content" id="content" />
			
		  <div class="image-buttons">
          <button type="button" id="imgAddBtn">이미지 추가</button>
          <button type="button" id="imgUndoBtn">이미지 취소</button>
          </div>
          <input type="file" id="imgInput" accept="image/*" />
        </td>
      </tr>
      <tr>
        <td colspan="2" class="submit-row">
          <button type="submit">수정 완료</button>
           <a href="/pView.do?postID=${pdto.postID}" class="returnBtn">돌아가기</a>
        </td>
      </tr>
    </table>
  </form>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
