<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>login</title>
 <!-- jQuery/ajax -->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <!-- Bootstrap4 -->
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<!--   <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script> -->
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
 <!-- fontawsome -->
 <script src="https://kit.fontawesome.com/e7c9242ec2.js" crossorigin="anonymous"></script>

 <!-- stylesheet -->
 <link href="${pageContext.request.contextPath}/mini2/style/member.css" rel="stylesheet">

</head>
<body>
 <!-- background 이미지 -->
   <div class="bg-blur"></div>
 
  <form  id="login_register_form" action="/login.do" method="post">
   <input type="hidden" name="redirectURL" value="${param.redirectURL}">
    <a href="/main.do">
     <img class="loginReImg" src="/mini2/imgs/logo.png">
    </a>
    <p class="loginTitle">* 이메일 로그인</p>

    <div class="input-container">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" placeholder="아이디(이메일)" id="idName" name="id" value="${id}">
    </div>

    <div class="input-container">
      <i class="fa fa-lock icon"></i>
      <input class="input-field" type="password" placeholder="비밀번호" id="password" name="pw">  
    </div>


    <p style="color:red">${message}</p>

    <!-- ID/ 비밀번호 찾기 모달------------------------------------------------------------------------------------------------>   
	<!-- 모달 버튼 -->
	<p class="findIdBtn" data-toggle="modal" data-target="#findModal">아이디∙비밀번호 찾기 ></p>
	
	<!-- Modal -->
	<div class="modal fade" id="findModal" tabindex="-1" role="dialog" aria-labelledby="findModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content p-3">
	      <div class="modal-header">
	        <h5 class="modal-title" id="findModalLabel">아이디∙비밀번호 찾기</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
	      </div>
	      <div class="modal-body">
	        <!-- Tìm ID -->
	        <div class="form-group">
	          <label for="findPhone">전화번호로 아이디 찾기</label>
	          <input type="text" class="form-control" id="findPhone" placeholder="010-XXXX-XXXX">
	          <small class="text-muted" id="foundIdMsg"></small>
	          <button type="button" class="btn btn-primary mt-2"onclick="return findId()">아이디 찾기</button>
	        </div>
	
	        <hr>
	
	        <!-- 새비밀번호 설정 -->
	        <div class="form-group">
	          <label for="resetId">아이디</label>
	          <input type="text" class="form-control" id="resetId">
	          <label for="resetPhone">전화번호</label>
	          <input type="text" class="form-control" id="resetPhone">
	          <label for="newPassword">새 비밀번호</label>
	          <input type="password" class="form-control" id="newPassword">
	          <small class="text-muted" id="resetMsg"></small>
	          <button  type="button" class="btn btn-success mt-2" onclick="resetPassword()">비밀번호 재설정</button>
	        </div>
	        <a  href="/login.do" class=" loginDisplay text-right d-block"> 로그인 화면 >></a>
	      </div>
	    </div>
	  </div>
	</div>
   <!-- ID/ 비밀번호 찾기 모달 end-->

    <button type="submit" class="btn loginBtn">로그인</button>
    <button type="button" class="btn registerBtn btn-outline-primary" onclick="location.href='register.do'">회원가입</button>
  </form>
  
 

<!-- javaScript main -->
 <script type="text/javascript" src="/mini2/js/member.js"></script>
    

</body>
</html>