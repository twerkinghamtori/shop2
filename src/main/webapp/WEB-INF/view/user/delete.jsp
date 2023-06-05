<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
</head>
<body>
<%--<spring:hasBindErrors name="user"> 
			<font color="red">
				<c:forEach items="${errors.globalErrors }" var="error">
					<spring:message code="${error.code }" /> <br>
				</c:forEach>
			</font>
	</spring:hasBindErrors>  --%>
	<table class="w3-table-all">
		<tr>
			<td>아이디</td>
			<td>${user.userid }</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>${user.username }</td>
		</tr>
		<tr>
			<td>생년월일</td>
			<td><fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd" /></td>
		</tr>		
	</table>
	<form method="post" action="delete" name="deleteForm">
		<input type="hidden" name="userid" value="${param.userid }">
		비밀번호<input type="password" name="password" >
		<a href="javascript:document.deleteForm.submit()">[회원탈퇴]</a>
	</form>
</body>
</html>