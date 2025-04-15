<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>login navBar</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <!-- stylesheet -->
 <link href="${pageContext.request.contextPath}/mini2/style/headerFooter.css" rel="stylesheet">
</head>
<body>
<!--Navbar-------------------------------------------------------------------------------------------------------------------  -->
  <div class="container-f navBar fixed-top">
    <div class="container">
	<nav class="navbar navbar-expand-md  navbar-dark">
	   <!-- Logo -->
	   <a class="navbar-brand text-white" href="/main.do">
	    <img class="logoNavBar" src="/mini2/imgs/logoPNG.png" alt="로고" height="30"> 
	  </a>
	
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="collapsibleNavbar">
	    <ul class="navbar-nav  ml-auto">
	            
	      
	      <li class="nav-item">
	        <a class="nav-link" href="/pList.do">여행기사</a>
	      </li>
	      <li>
	        <a class="btn btn-login" href="/login.do">로그인</a>
	      </li>    
	    </ul>
	  </div>  
	</nav>
	</div>
 </div>
<!--Navbar end--->



</body>
</html>
