<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여기저기 기사 쓰기</title>
 <link rel="stylesheet" href="/mini2/style/pWrite.css?">
<script src="/mini2/js/pWrite.js?"></script>
</head>
<body class="d-flex-column min-vh-100">
   <jsp:include page="headerlogout.jsp"></jsp:include>

  <h2 class="page-title">✏️기사 작성</h2>
  <div class="hrContainer">
	<hr>
  </div>

  <form name="pWriteFrm" method="post" action="/pWrite.do">
    <input type="hidden" name="postID" value="${pdto.postID}" />

    <table class="edit-table">
      <tr>
        <td class="label">기사제목 :</td>
        <td><input type="text" name="title" class="input-text large" value="${pdto.title}" /></td>
      </tr>
      <tr>
        <td class="label">기사 소개 태그 :</td>
        <td><input type="text" name="tag" class="input-text medium" value="${pdto.tag}" placeholder="예)#대한민국"/></td>
      </tr>
      <tr>
        <td class="label">국가/지역 :</td>
        <td><input type="text" name="country" class="input-text medium" value="${pdto.country}" /></td>
      </tr>
      <tr>
        <td class="label">기사 내용 :</td>
        <td>
          <div id="editor" contenteditable="true">
          </div>
          <input type="hidden" name="content" id="content" />
         
        <div class="image-buttons">
          <button type="button" id="imgAddBtn">이미지 추가</button>
          <button type="button" id="imgUndoBtn">이미지 취소</button>
          </div>
          <input type="file" id="imgInput" accept="image/*"/>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="submit-row">
          <button type="submit">작성 완료</button>
        </td>
      </tr>
    </table>
  </form>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>