<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>memberEdit</title>


 <!-- stylesheet -->
 <link href="${pageContext.request.contextPath}/mini2/style/member.css" rel="stylesheet">
 
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


 <!-- background 이미지 -->
   <div class="bg-blur"></div>
   
  <form id="memberEdit" action="memberEdit.do" method="post" name="frm" >
    <div class="container editCont">
      <h2> 회원 정보 수정</h2>
      <div class="container memberEdit">
        <p> 이름 :</p>
        <input class="input-field" type="text" placeholder="이름" id="name"  name="name" value="${mVo.name}"> 
      </div>
      <p class="error-message editError" id="error-name"></p>

      <div class="container memberEdit">
        <p> 아이디(이메일) :</p>
        <input class="input-field" type="text" placeholder="아이디(이메일)" id="idName" name="id" value="${mVo.id}" readonly>
      </div>
      
    
      <div class="container memberEdit">
        <p> 비밀번호 :</p>
        <input class="input-field" type="password" placeholder="비밀번호" id="password"  name="pw">  
      </div>
      <span class="error-message editError" id="error-password"></span> 
      <p class="  mes2  editMes">&nbsp;&nbsp; &times; 숫자나 글자 (4~20자) 입력해주세요!. </p>
      
     
      
      <div class="container memberEdit">
        <p> 비밀번호 확인 :</p>
        <input class="input-field" type="password" placeholder="비밀번호 확인" id="passwordConfirm"  name="pw_check"> 
      </div>
       <span class="error-message editError " id="error-passwordConfirm"></span>
      <p class="  mes3 editMes">&nbsp;&nbsp; &times; 확인을 위해 새 비밀번호를 다시 입력해주세요. </p>
      
      <div class="container memberEdit">
        <p> 전화번호 :</p>
         <input class="input-field" type="text" placeholder="휴대폰번호" id="tel"  name="phonenum" value="${mVo.phonenum}">  
      </div> 
      <span class="error-message editError" id="error-tel"></span>
      <p class="  mes5 editMes">&nbsp;&nbsp; &times; 휴대폰 번호를 정확하게 입력해주세요.'-' 포함 ex) 010-3322-7788</p>
      
      
    </div>

    <div class="btnCont">
      <button type="submit" class="btn btn-primary btn-lg applyBtn" value="확인" onclick="return joinCheck()">적용</button>
      <button type="reset" class="btn btn-secondary btn-lg cancellBtn" value="취소" >취소</button>
    </div>
  </form>
  
  <jsp:include page="footer.jsp"></jsp:include>
  
   <!-- javaScript main -->
 <script type="text/javascript" src="/mini2/js/member.js"></script>

</body>
</html>