<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>register</title>
  <!-- jQuery/ajax -->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <!-- Bootstrap4 -->
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
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
   
  <form  action="register.do" method="post" id="login_register_form" name="frm" >
    <img src="/mini2/imgs/logo.png">
    <p class="registerTitle">회원가입</p>
    <div class="input-container">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" placeholder="아이디(이메일)" id="idName" class="idName" name="id">    
    </div>
    <span class="error-message" id="error-idName"></span>
    <p class="isID" style="color:blue">${message}</p> 
    
       
  
    <div class="input-container">
      <i class="fa fa-lock icon"></i>
      <input class="input-field" type="password" placeholder="비밀번호" id="password"  name="pw">  
    </div>
    <span class="error-message" id="error-password"></span> 
    <p class="message  mes2">&nbsp;&nbsp; &times; 숫자나 글자 (4~20자) 입력해주세요!. </p> 

    <div class="input-container">
      <i class="fa fa-lock icon"></i>
      <input class="input-field" type="password" placeholder="비밀번호 확인" id="passwordConfirm"  name="pw_check"> 
    </div>
    <span class="error-message" id="error-passwordConfirm"></span>
    <p class="message  mes3">&nbsp;&nbsp; &times; 확인을 위해 새 비밀번호를 다시 입력해주세요. </p>

    <div class="input-container">
      <i class="fa fa-user icon"></i>
      <input class="input-field" type="text" placeholder="이름" id="name"  name="name">  
    </div>
     <p class="error-message" id="error-name"></p>
    

    <div class="input-container">
      <i class="fa fa-phone icon"></i>
      <input class="input-field" type="text" placeholder="휴대폰번호" id="tel"  name="phonenum">  
    </div>
    <span class="error-message" id="error-tel"></span>
    <p class="message  mes5">&nbsp;&nbsp; &times; 휴대폰 번호를 정확하게 입력해주세요.'-' 포함 ex) 010-3322-7788</p>
    
 

    <button type="submit" class="btn registerBtn1" value="확인" onclick="return joinCheck()" >회원가입</button>
    
    
  </form>

   <!-- javaScript main -->
 <script type="text/javascript" src="/mini2/js/member.js"></script>

  
  
</body>
</html>