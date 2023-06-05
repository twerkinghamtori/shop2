<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인화면</title>
<script type="text/javascript">
	function win_open(page) {
		let op = "width=500, height=350, left=50, top=150";
		open(page, "", op);
	}
</script>
</head>
<body>
	<h2>사용자 로그인</h2>
	<form:form modelAttribute="user" method="post" action="login" name="loginForm">
		<!-- 유효성 검사 회피 -->
		<input type="hidden" name="username" value="유효성검증을 위한 데이터">
		<input type="hidden" name="email" value="a@a.a">
		<input type="hidden" name="birthday" value="2000-01-01">
		
		<spring:hasBindErrors name="user">
			<font color="red">
				<c:forEach items="${errors.globalErrors }" var="error">
					<spring:message code="${error.code }" />
				</c:forEach>
			</font>
		</spring:hasBindErrors>
		
		<table style="margin-left:auto;margin-right:auto;" class="w3-table w3-striped">
			<tr>
				<td>아이디</td>
				<td>
					<form:input path="userid"/>
					<font color="red">
						<form:errors path="userid" />
					</font>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<form:password path="password"/>
					<font color="red">
						<form:errors path="password" />
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="로그인">
					<input type="button" value="회원가입" onclick="location.href='join'">
					<input type="button" value="아이디찾기" onclick="win_open('idsearch')">
					<input type="button" value="비밀번호찾기" onclick="win_open('pwsearch')">
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>