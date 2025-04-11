<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
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























<!--footer  -->
 <jsp:include page="footer.jsp"></jsp:include>


</body>
</html>
