<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />   
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title'/></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" >
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/full/ckeditor.js"></script>
<link rel="shortcut icon" href="${path}/images/favicon.png" type="image/x-icon">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet">

<style>
  .w3-bar-block .w3-bar-item {padding:20px}
	a:hover{color:black;}
  #mySidebar{
    display:none;
    z-index:2;
    width:20%;
    min-width:300px;
  }
  #mySidebar a, #top, #footer, #footer h3{font-family: 'Dongle', sans-serif;}
  #mySidebar > a {font-size: 1.2em;}
  #demoAcc1 a{font-size: 1.4em;}
  #demoAcc2 a{font-size: 1.4em;}
  #flip-horizontal {
    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);
    filter: FlipH;
    -ms-filter: "FlipH";
  }
  #top{border-bottom: 1px solid rgba(0,0,0,0.3);}
  #top .w3-white{
    padding-left: 20px;
    padding-right: 20px;
  }
  #top a{text-decoration: none;}
  #top #center{font-size: 36px;}
  #center a:hover {color: inherit;}
  
  /* 푸터부분 */
  #footer.w3-padding-32 {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  /*  상단으로가는 버튼*/
#myBtn123 {
  position: fixed; 
  bottom: 30px; 
  right: 30px; 
  z-index: 99;
  cursor: pointer;
  padding: 15px; 
  border-radius: 10px; 
}
#exchange {	
	font-size:20px;
	font-family: 'Dongle', sans-serif;
}
</style>
<sitemesh:write property='head'/>
</head>
<body style="position : relative">
<button class="w3-btn w3-black" onclick="topFunction()" id="myBtn123"><i class="fa fa-chevron-up"></i></button>
<!-- Sidebar (hidden by default) -->
<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-bar-item w3-button"><i id="flip-horizontal" class="fa fa-sign-out"></i></a>
  <a href="${path}/user/mypage?userid=${loginUser.userid}" class="w3-bar-item w3-button">회원관리</a>
  <a href="${path}/item/list"  class="w3-bar-item w3-button">상품관리</a>
  <a href="${path}/chat/chat"  class="w3-bar-item w3-button">채팅하기</a>
  <hr>
  <a onclick="myAccFunc()" href="javascript:void(0)" class="w3-button w3-block w3-white w3-left-align" id="myBtn1">게시판 &nbsp;<i class="fa fa-caret-down"></i></a>
  <div id="demoAcc1" class="w3-bar-block w3-hide w3-padding-large w3-medium">
  	<a href="${path}/board/list?boardid=1" class="w3-bar-item w3-button">공지사항</a>
	  <a href="${path}/board/list?boardid=2"  class="w3-bar-item w3-button">자유게시판</a>
	  <a href="${path}/board/list?boardid=3"  class="w3-bar-item w3-button">QnA</a>
	</div>
  <div class="container">
  	<div id="exchange"></div>
  </div>
</nav>

<!-- Top menu -->
<div id="top" class="w3-top">
  <div class="w3-white w3-xlarge" style="display:flex;justify-content: space-between;align-items: center;position:relative;">
    <div class="w3-button w3-padding-16" onclick="w3_open()">&#9776;</div>
    <div class="w3-padding-16 text-center" style="position:absolute; left:45%"><a href="${path}"><img src="${path}/img/hamtori.jpg"  width="130px" height="100px"></a></div>
    
    <c:if test="${!empty sessionScope.loginUser}">
    	<div class=w3-padding-16>
    		<p style="display:inline" id="unreadMsg"></p>&nbsp;&nbsp;
		    <a href="${path}/user/mypage?userid=${sessionScope.loginUser.userid}">마이페이지</a>&nbsp;&nbsp;
	    	<a href="${path}/user/logout">로그아웃</a>&nbsp;&nbsp;
	    </div>
    </c:if>
    <c:if test="${empty sessionScope.loginUser}">
	    <div class=w3-padding-16>
		    <a href="${path}/user/login">로그인</a>&nbsp;&nbsp;
		    <a href="${path}/user/join">회원가입</a>&nbsp;&nbsp;
		  </div>
    </c:if>
    
 
  </div>
</div>
	<hr id="about">
  

<div class="w3-main w3-content w3-padding" style="max-width:120vw;margin-top:50px;min-height:100vh;">

  <sitemesh:write property='body'/>
  
</div>

<hr>
<!-- Footer -->
<footer id="footer" class="w3-row-padding w3-padding-32 text-center">
  <div class="w3-third">
    <h3><i class="fa fa-map-marker"></i> 구디아카데미 GDJ62기 두리두리차두리 정진규, 강수빈</h3>
    <h3><i class="fa fa-envelope"></i> rritjy@naver.com | zxc2289@naver.com</h3>
    <h3><i class="fa fa-instagram"></i> @jeongjingyu63 | @sub__b.in</h3>
  </div>
  <hr>
    <div>
    	<span id="si">
    		<select name="si" onchange="getText('si')">
    			<option value="">시도를 선택하세요</option>
    		</select>
    	</span>
    	<span id="gu">
    		<select name="gu" onchange="getText('gu')">
    			<option value="">구군을 선택하세요</option>
    		</select>
    	</span>
    	<span id="dong">
    		<select name="dong" onchange="getText('dong')">
    			<option value="">동리를 선택하세요</option>
    		</select>
    	</span>
    </div>
</footer>

<!-- End page content -->


<script>
  function w3_open() {$("#mySidebar").show();}
  function w3_close() {$("#mySidebar").hide();}

  $(document).ready(function() {
    $('#myBtn1, #demoAcc1').mouseenter(function() {
      $('#demoAcc1').addClass('w3-show');
    });

    $('#myBtn1, #demoAcc1').mouseleave(function() {
      $('#demoAcc1').removeClass('w3-show');
    });
  })  
  window.onscroll = function() {scrollFunction()};

  function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
      document.getElementById("myBtn123").classList.add("scroll");
    } else {
      document.getElementById("myBtn123").classList.remove("scroll");
    }
  }

  function topFunction() {
    document.body.scrollTop = 0; 
    document.documentElement.scrollTop = 0;
  }
</script>
<script type="text/javascript">
	$(function() {
		getSido();
//		exchangeRate();
		exchangeRate2();
	})
	function getSido() { //서버에서 리스트객체를 배열로 직접 전달 받음
		$.ajax({
			url : "${path}/ajax/select",
			success : function(arr) {
				console.log(arr)
				$.each(arr, function(i,item) {
					$("select[name=si]").append(function() {
						return "<option>" + item + "</option>"
					})
				})
			},error : function(e) {
				alert("도시정보" + e.status)
			}
		})
	}
	function getSido2() { //서버에서 문자열로 전달 받기
		$.ajax({
			url : "${path}/ajax/select2",
			success : function(data) {
				console.log(data)
				let arr = data.substring(data.indexOf('[')+1, data.indexOf(']')).split(",")
				$.each(arr, function(i,item) {
					$("select[name=si]").append(function() {
						return "<option>" + item + "</option>"
					})
				})
			},error : function(e) {
				alert(e.status)
			}
		})
	}
	function getText(name) { //name= si || gu
		let city = $("select[name='si']").val()
		let gun = $("select[name='gu']").val()
		let disname =''
		let toptext = '구/군을 선택하세요'
		let params=''
		if(name=='si') {
			params = "si=" + city.trim()
			disname = "gu"
		} else if(name=='gu') {
			params = "si=" + city.trim() + "&gu=" + gun.trim()
			disname = "dong"
			toptext = '동/리를 선택하세요'
		} else {
			return;
		}
		$.ajax({
			url : "${path}/ajax/select",
			type : "POST",
			data : params,
			success : function(arr) {
				console.log(arr)
				$("select[name=" + disname + "] option").remove() //출력 select 태그의 option 제거. append에 추가 방지
				$("select[name=" + disname + "]").append(function(){
					return "<option>" + toptext + "</option>"
				})
				$.each(arr, function(i,item){
					$("select[name=" + disname + "]").append(function(){
						return "<option>" + item + "</option>"
					})
				})
			},
			error : function(e) {
				alert(e.status)
			}
		})
	}
	function exchangeRate() {
		$.ajax("${path}/ajax/exchange", {
			success : function(data) {
				console.log(data)
				$("#exchange").html(data)
			},
			error : function(e) {
				alert("환율조회"+e.status)
			}
		})
	}
	function exchangeRate2() { //map 객체로 데이터 수신
		$.ajax("${path}/ajax/exchange2", {
			success : function(data) {
				console.log(data) //json타입으로 전송됨
				let text="<h4 class='w3-center'>수출입은행<br>" + data.exdate + "</h4>";
				text+="<table class='table table-hover table-bordered'>";
				text+="<tr class='table-dark'><th>통화</th><th>기준율</th><th>받으실때</th><th>보내실때</th></tr>";
				$.each(data.trlist, function(i, tds) {
					text += "<tr><td>" + tds[0] + "<br>" + tds[1] + "</td><td>" + tds[4] + "</td>";
					text += "<td>" + tds[2] + "</td><td>" + tds[3] + "</td></tr>"
				})
				$("#exchange").html(text)
			},
			error : function(e) {
				alert("환율조회"+e.status)
			}
		})
	}
</script>
</body>
</html>