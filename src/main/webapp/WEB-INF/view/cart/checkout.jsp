<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 전 상품 목록 보기</title>
</head>
<body>
	<h2>배송지 정보</h2>
	<table class="w3-table w3-striped">
		<tr>
			<td width="30%">주문아이디</td>
			<td width="70%">${sessionScope.loginUser.userid }</td>
		</tr>
		<tr>
			<td width="30%">이름</td>
			<td width="70%">${sessionScope.loginUser.username }</td>
		</tr>
		<tr>
			<td width="30%">우편번호</td>
			<td width="70%">${sessionScope.loginUser.postcode }</td>
		</tr>
		<tr>
			<td width="30%">주소</td>
			<td width="70%">${sessionScope.loginUser.address }</td>
		</tr>
		<tr>
			<td width="30%">전화번호</td>
			<td width="70%">${sessionScope.loginUser.phoneno }</td>
		</tr>
	</table>
	<h2>구매 상품</h2>
	<table class="w3-table w3-striped">
		<tr>
			<th>상품명</th>
			<th>가격</th>
			<th>수량</th>
			<th>합계</th>
		</tr>
		<c:forEach items="${sessionScope.CART.itemSetList }" var="itemSet" varStatus="st">
			<tr>
				<td>${itemSet.item.name }</td>
				<td><fmt:formatNumber value="${itemSet.item.price }" pattern="###,###" /></td>
				<td>${itemSet.quantity }</td>
				<td><fmt:formatNumber value="${itemSet.item.price * itemSet.quantity }" pattern="###,###" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" align="right">총 구입 금액 : <fmt:formatNumber value="${sessionScope.CART.total }" pattern="###,###" />원</td>			
			
		</tr>
		<tr>
			<td colspan="4">
				<a href="javascript:kakaopay()"><img src="../img/payment_icon_yellow_medium.png"></a>&nbsp;
				<a href="../item/list">상품목록</a>&nbsp;
			</td>			
		</tr>
	</table>
	<%--결제하기 모듈 : 개발자 문서 --%>
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
	<script type="text/javascript">
		let IMP = window.IMP
		IMP.init("imp03400706") //가맹점 식별 코드(개발자 콘솔)
		
		function kakaopay() {
			$.ajax("kakao", {
				success : function(json) {
					iamPay(json)
				}
			})
		}
		
		function iamPay(json) {
			IMP.request_pay({
				pg : "kakaopay",   //상점구분. 카카오페이
				pay_method : "card", //카드 결제
				merchant_uid : json.merchant_uid, //주문번호 : 주문별로 유일한 값이 필요. userid-session id 값으로 설정(내맘대로 설정)
				name : json.name, //주문상품명. 사과 외 n개
				amount : json.amount, //주문금액(합계)
				buyer_email : "zxc2289@naver.com", //결제내역 메일 전송(주문자 이메일)
			    buyer_name : json.buyer_name,
			    buyer_tel : json.buyer_tel,
			    buyer_addr : json.buyer_addr,
			    buyer_postcode : json.buyer_postcode
			}, function(rsp) {
				if(rsp.success) { //결제 응답 성공
					let msg = "결제가 완료되었습니다.";
					msg += "\n 고유 ID : " + rsp.imp_uid;
					msg += "\n 상점 ID : " + rsp.merchant_uid;
					msg += "\n 결제금액 : " + rsp.paid_amount;
					alert(msg);
					location.href="end";
				} else {
					alert("카카오페이 결제 시 오류 발생" + rsp.error_msg)
				}
			})
		}
	</script>
</body>
</html>