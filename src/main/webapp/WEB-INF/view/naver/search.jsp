<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 검색</title>
<script type="text/javascript">
		function naversearch(start) {
			$.ajax({
				type:"POST",
				url:"naversearch",
				data: {"data":$("#data").val(), "display":$("#display").val(), "start":start, "type":$("#type").val()},
				success : function(json) {
					console.log(json);
					let total = json.total;
					let html = "";					
					let maxpage = Math.ceil(total / parseInt($("#display").val()));
					let startpage = Math.ceil((start/10)-1) * 10 +1;
					let endpage = startpage + 9;					
					if(endpage > maxpage) endpage = maxpage;
					let display = json.display;
					let num = start*display - (display-1);
					html += "<table class='w3-table'>" + "<tr><td colspan='5' align='center'>전체 조회 건수 :  " + total + ", 현재페이지 : " + start + "/" + endpage + "</td></tr>";
					$.each(json.items, function(i,item) {
						html += "<tr><td>" + num 
						+ "</td><td>" + item.title;
						if($("#type").val() == 'image') {
							html += "</td><td><img src=" + item.link + " style='width:200px; height:200px;'>"
						}
						else html +="</td><td><a target='_blank' href='" + item.link + "'>" + item.link + "</a>"
						html += "</td><td>" + item.description
						+"</td><td>" + item.pubDate + "</td></tr>";
						num ++;
					})
					html += "<tr><td colspan='5' align='center'>";
					if(start > 1) {
						html += "<a href='javascript:naversearch(" + (start-1) + ")'>[이전]</a>";
					}
					for(let i = startpage; i<=endpage; i++) {
						html += "<a href='javascript:naversearch(" + i + ")'>[" + i + "]</a>";						
					}
					if(maxpage > start) {
						html += "<a href='javascript:naversearch(" + (start+1) + ")'>[다음]</a>";
					}
					html += "</td></tr></table>";
					$("#result").html(html);
				}
			})
		}
	</script>
</head>
<body>
	<select id="type" class="w3-select w3-border">
		<option value="blog">블로그</option>
		<option value="news">뉴스</option>
		<option value="book">책</option>
		<option value="encyc">백과사전</option>
		<option value="cafearticle">카페글</option>
		<option value="kin">지식인</option>
		<option value="local">지역</option>
		<option value="webkr">웹문서</option>
		<option value="image">이미지</option>
		<option value="shop">쇼핑</option>
		<option value="doc">전문자료</option>
	</select>
	검색 개수 : 
	<select id="display">
		<option>10</option>
		<option>20</option>
		<option>50</option>
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	검색어 : <input type="text" id="data" placeholder="검색어를 입력하세요.">
	<button class="w3-btn w3-blue" onclick="naversearch(1)">검색</button>
	<div id="result"></div>
	
</body>
</html>