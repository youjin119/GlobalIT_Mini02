<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<head>
  <title>logout navbar</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

  <!-- jQuery full version -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

  <!-- Popper + Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- stylesheet -->
 <link href="${pageContext.request.contextPath}/mini2/style/member.css" rel="stylesheet">
 
</head>
<body>
  <div class="container-f navBar fixed-top">
    <div class="container">
	<nav class="navbar navbar-expand-md  navbar-dark">
	   <!-- Logo -->
	   <a class="navbar-brand text-white" href="/pList.do">
	    <img class="logoNavBar" src="/mini2/imgs/logoPNG.png" alt="로고" height="30"> <!-- hoặc chữ: MiniTravel -->
	   </a>
	
	   <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	    <span class="navbar-toggler-icon"></span>
	   </button>
	   <div class="collapse navbar-collapse" id="collapsibleNavbar">
	    <ul class="navbar-nav  ml-auto">     
	      <li class="nav-item">
	        <a class="nav-link" href="/pList.do">여행기사</a>
	      </li>
	
	      <li class="nav-item">
	        <a class="nav-link" >환영홥니다!<span class="peopleName"> ${loginUser.name}</span>님</a>  
	      </li>
	
	      <li>
	      <div class="dropdown">
	        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          <i class="fas fa-user-circle fa-lg"></i>
	        </a>
	        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
	          <c:if test="${not empty sessionScope.loginUser}">
	            <a class="dropdown-item" href="memberEdit.do?id=${loginUser.id}">회원정보 수정</a>
	          </c:if> 
	          <a class="dropdown-item" href="/pWrite.do">여행기사 쓰기</a>
	        </div>
	      </div>
	      </li> 
	
	      <li class="nav-item">
	         <c:if test="${not empty sessionScope.loginUser}">
	    		<a class="btn btn-login" href="logout.do">로그아웃</a>
			</c:if>
	      </li> 	
	    </ul>	  
	  </div>  
	</nav>
  </div>
 </div>



</body>
</html>
