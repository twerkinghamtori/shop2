<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />    
<%-- /shop1/src/main/webapp/layout/gdulayout.jsp --%>    

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property="title" /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" >
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>
<link rel="shortcut icon" href="${path}/images/favicon.png" type="image/x-icon">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet">
<script type="text/javascript" src= 
"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2caf41877e5cc64a48762982cb8b7dd2"></script>
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
<sitemesh:write property="head" />
</head>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
  <span class="w3-bar-item w3-right">
	<c:if test="${empty sessionScope.loginUser}">
	 <a href="${path}/user/login">로그인</a>
	 <a href="${path}/user/join">회원가입</a>
	</c:if>   
	<c:if test="${!empty sessionScope.loginUser}">
	${sessionScope.loginUser.username}님이 로그인 하셨습니다.&nbsp;&nbsp;
	 <a href="${path}/user/logout">로그아웃</a>
	</c:if>   
  </span>
</div>
<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="${path}/img/dochi.jpg" 
         class="w3-circle w3-margin-right" style="width:100px">
    </div>
    <div class="w3-col s8 w3-bar">
      <c:if test="${!empty sessionScope.loginUser}">
      <span>반갑습니다, <strong>${sessionScope.loginUser.username}님</strong></span><br>
      </c:if>
      <c:if test="${empty sessionScope.loginUser}">
      <span><strong>로그인하세요</strong></span><br>
      </c:if>
    </div>
  </div>
  <hr>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Close Menu</a>
    <a href="${path}/user/mypage?userid=${loginUser.userid}" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="user" }'>w3-blue</c:if>">
    <i class="fa fa-users fa-fw"></i>&nbsp; 회원관리</a>
    <a href="${path}/item/list" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="item" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 상품관리</a>
    <a href="${path}/chat/chat" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="chat" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 채팅하기</a>
    <hr>
    <a href="${path}/board/list?boardid=1" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="board" && boardid=="1" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 공지사항</a>
    <a href="${path}/board/list?boardid=2" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="board" && boardid=="2" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 자유게시판</a>
    <a href="${path}/board/list?boardid=3" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url=="board" && boardid=="3" }'>w3-blue </c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; QnA</a>
    <hr>
    <a href="${path }/naver/search" 
    class="w3-bar-item w3-button w3-padding <c:if test='${url ==  "naver"}'>w3-blue</c:if>">
    <i class="fa fa-eye fa-fw"></i>&nbsp; 네이버 검색</a>
  </div>
  <br>    
  <div id="map" style="width:300px;height:300px;"></div>
  <script>
  	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
  	mapOption = { 
      	center: new kakao.maps.LatLng(37.47309138434624, 126.89053743266287), // 지도의 중심좌표
      	level: 3 // 지도의 확대 레벨
  	};
	//지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	// 마커가 표시될 위치입니다 
	var markerPosition = new kakao.maps.LatLng(37.47309138434624, 126.89053743266287); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

	// 마커가 드래그 가능하도록 설정합니다 
	marker.setDraggable(true); 
	</script>
  <div class="w3-container">
  	<div id="exchange"></div>
  </div>

</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">

  <!-- Header -->
  <header class="w3-container" style="padding-top:22px">
    <h5><b><i class="fa fa-dashboard"></i> My Dashboard</b></h5>
  </header>

  <div class="w3-row-padding w3-margin-bottom">
    <div class="w3-half">
    	<div class="w3-container w3-teal w3-padding-16 w3-center">
    		<input type="radio" name="pie" onchange="piegraph(2)" checked="checked">자유게시판 &nbsp;&nbsp;
    		<input type="radio" name="pie" onchange="piegraph(3)">QNA &nbsp;&nbsp;
    		<div id="piecontainer" style="width:100%; border:1px solid #ffffff">
    			<canvas id="canvas1" style="width:100%"></canvas>
    		</div>
    	</div>
    </div>
    <div class="w3-half">
      <div class="w3-container w3-orange w3-padding-16 w3-center">
        <input type="radio" name="barline" onchange="barlinegraph(2)" checked="checked">자유게시판 &nbsp;&nbsp;
    		<input type="radio" name="barline" onchange="barlinegraph(3)">QNA &nbsp;&nbsp;
    		<div id="barcontainer" style="width:100%; border:1px solid #ffffff">
    			<canvas id="canvas2" style="width:100%"></canvas>
    		</div>
      </div>
    </div>
  </div>

  <div class="w3-panel">
  <sitemesh:write property="body" />
  </div>
  <hr>
  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <h4>FOOTER</h4>
    <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
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
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script type="text/javascript">
	$(function() {
		getSido();
//		exchangeRate();
		exchangeRate2();
		piegraph(2);
		barlinegraph(2);
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
	let randomColorFactor = function() {
		return Math.round(Math.random() * 255)
	}
	let randomColor = function(opa) {
		return "rgba("+randomColorFactor() + "," + randomColorFactor() + "," + randomColorFactor() + "," + (opa || '.3') + ")"
	}
	function piegraph(id) {
		$.ajax("${path}/ajax/graph1?id="+id, {
			success : function(arr) {
				let canvas = "<canvas id='canvas1' style='width:100%'></canvas>"
				$("#piecontainer").html(canvas) //기존 canvas 지우고 새로운 canvas 객체 생성
				pieGraphPrint(arr, id)
			}, error: function(e) {
				alert("piegraph : " + e.status())
			}
		})
	}
	function pieGraphPrint(arr, id) {
		let colors = []
		let writers = []
		let datas = []
		$.each(arr, function(index) {
			colors[index] = randomColor(0.5)
			for(key in arr[index]) { //arr[0] : {"홍길동":10} , key : "홍길동"
				writers.push(key)
				datas.push(arr[index][key]) //.getKey("홍길동")?
			}
		})
		let title = (id==2)?"자유게시판":"QNA"
		let config = {
				type : 'pie',
				data : {
					datasets : [{data:datas, backgroundColor:colors}],
					labels : writers
				}, 
				options : {
					responsive : true,
					legend : {display:true, position:"right"},
					title : {
						display:true,
						text:'글쓴이 별 ' + title + ' 등록 건수',
						position:'top'
					}
				}
		}
		let ctx = document.getElementById("canvas1")
		new Chart(ctx, config)
	}
	function barlinegraph(id) {
		$.ajax("${path}/ajax/graph2?id="+id, {
			success : function(arr) {
				let canvas = "<canvas id='canvas2' style='width:100%'></canvas>"
				$("#barcontainer").html(canvas)
				barGraphPrint(arr, id)
			}, error: function(e) {
				alert("bargraph : " + e.status())
			}
		})
	}
	function barGraphPrint(arr, id) {
		let colors = []
		let regdates = []
		let datas = []
		$.each(arr, function(index) {
			colors[index] = randomColor(0.5)
			for(key in arr[index]) {
				regdates.push(key)
				datas.push(arr[index][key])
			}
		})
		let title = (id==2)?"자유게시판":"QNA";
		let chartData = {
				labels : regdates,
				datasets : [{
					type : 'line',
					borderWidth : 2,
					borderColor : colors,
					label : '건수',
					fill : false,
					data : datas
				},{
					type : 'bar',
					backgroundColor : colors,
					label : '건수',
					data : datas
				}]
		}
		let config = {
				type : 'bar',
				data : chartData,
				options : {
					responsive : true,
					title : {
						display : true,
						text : '최근 7일 ' + title + ' 등록 건수',
						position : 'bottom'
					},
					legend : {display : false},
					scales : {
						xAxes : [{
							display : true,
							stacked : true
						}],
						yAxes : [{
							display : true,
							stacked : true
						}]
					}
				}
		}
		let ctx = document.getElementById("canvas2")
		new Chart(ctx, config)
	}
</script>
</body>
</html>