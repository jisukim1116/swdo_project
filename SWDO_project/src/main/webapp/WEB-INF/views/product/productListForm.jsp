<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 제품 목록 ]</title>
<!-- 앱의 클라이언트 ID 지정 -->
<meta name="google-signin-client_id" content="895850026348-73a2do4bd75ujv8rirts59gu3i977ck5.apps.googleusercontent.com">
<!-- google login&out 관련 js를 받아옴 -->
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>	

<!-- google Font:Abril Fatface -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap" rel="stylesheet">

<script type="text/javascript" src = "/resources/js/jquery-3.6.0.js"></script> 

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

<link rel="stylesheet" href="./../../resources/css/custom.css">
<style type="text/css">
input::placeholder{
	font-size: x-large;
}
</style>

</head>
<body>
<header class="row">
	<div id="grid" class="col-lg-9">
		<div style="padding-left: 2.5em; margin-top: -10px">	
			<a href="/"> 
				<span style="font-size:2.7em; font-family: 'Abril Fatface', cursive;">MUSHPEDIA <span style="font-size:55%; color: rgb(119,119,119);">mushroom classification</span></span>
			</a>
		</div>	
	</div>
	<div id="grid" class="col-lg-3" style="margin-top: -15px;">
	
		<a href="/product/productListForm"><span style="padding-left: 1em; text-decoration: underline;text-underline-position: under;font-size: 140%">검색</span></a>
		
		<c:choose>
			<c:when test="${empty sessionScope.loginVO }">			
				<a href="/user/loginForm" style="" title="">
					<span style="padding-left: 1em">로그인</span>
				</a>								
				<a href="/user/joinForm" style="" title="">
					<span style="padding-left: 1em;">회원가입</span>
				</a>														
			</c:when>				
			<c:otherwise>				
				<a href="/user/logout" style="" title="" onclick="signOut();">
					<span style="padding-left: 1em">로그아웃</span>
				</a>
			<c:if test="${sessionScope.loginVO.user_id ne 'admin'}">
				<a href="user/detail?user_id=${sessionScope.loginVO.user_id }" style="" title="">					
					<span style="padding-left: 1em">회원정보</span>
				</a>
			</c:if>	
			</c:otherwise>						
		</c:choose>	
			
		<c:choose>
				<c:when test="${sessionScope.loginVO.user_id eq 'admin'}">
					<div id="grid">
						<a href="/product/listForm">
							<span class="" style="padding-left: 1em;font-weight: bold;"><em>제품목록</em></span>
						</a>				
						<a href="/product/enrollForm">
							<span class="" style="padding-left: 1em;font-weight: bold;"><em>제품등록</em></span>
						</a>
						<a href="/user/listForm">
							<span class="" style="padding-left: 1em;font-weight: bold;"><em>전체회원관리</em></span>
						</a>
					</div>	
				</c:when>
		</c:choose>   
			
	</div>	
</header>

<nav style="display: flex;margin-top: 10px">
	<div id="grid" style="margin-left:1.5em;margin-right:1.5em; flex-grow: 1; ">
		<input type="text" id="searchText" name="searchText" placeholder="검색을 위해 입력하세요" style="width: 93%; margin-bottom: 0.5em; padding-top: 2em; padding-bottom:0.5em; border-bottom: 2px solid black">
	</div>
</nav>

<input type="hidden" value="${searchText }" id="st">
<input type="hidden" value="${navi.currentPage }" id="cp">	
<input type="hidden" value="${navi.totalPageCount }" id="tpc">

<section class="" id="productDisplay" style="padding-top: 3em;"></section>

<script type="text/javascript">

//비동기 검색을 위한 코드
var oldVal="";
var flag_search=true;
var flag_scroll=true;
 
$(function(){

	//새로고침 시 검색 초기화
	$(document).ready(function(){
		$("#productDisplay *").remove(); //별표 주의
	});
	
	//비동기 검색
	$("#searchText").on("propertychange change keyup paste input", function() {
	    var currentVal = $(this).val();
		
	    
	    if(currentVal == oldVal || currentVal == "") {
	        return;
	    }
	 
	    oldVal = currentVal;
	    $("#productDisplay *").remove(); //별표 주의

	    console.log(currentVal);
		
		if(flag_search){
			flag_search = false;
		    $.ajax({
	
				url : "/product/productList",
				type : "get",
				data : {
					currentPage : 1,
					searchText : currentVal
				},
				success : function(data){

					productSelectAll(data);

					$("#st").val(data.searchText);
					$("#tpc").val(data.navi.totalPageCount);
					
					flag_search = true;
				},
			    error : function(e){
					console.log(e);
				}
			})
		}
	});

	//무한 스크롤
	$(window).scroll(function(){
		
		var scrollHeight = $(window).scrollTop() + $(window).height();
		var documentHeight = $(document).height();

		var currentPage = $("#cp").val(); 
		var searchText = $("#st").val(); 
		var totalPageCount = $("#tpc").val(); 

		console.log("--------------------------------------------")
		console.log("scrollHeight : " + scrollHeight);
		console.log("documentHeight : " + documentHeight);
		console.log("currentPage1 : " + currentPage);
		console.log("totalPageCount : " + totalPageCount);
		
		if(scrollHeight >= (documentHeight*0.001) && currentPage <= totalPageCount){ 
			console.log("currentPage2 : " + currentPage);

			if (flag_scroll) {
				flag_scroll = false;

				$.ajax({

					url : "/product/productList",
					type : "get",
					data : {
						currentPage : currentPage,
						searchText : searchText
					},
					//dataType : "json",
					success : function(data){
						console.log(data);
						productSelectAll(data);
						flag_scroll = true;
					},
					error : function(e){
						console.log(e);
					}
				})
			}
			
			
		}
	});
	
});  

function productSelectAll(data){

	console.log(data);
	var content = '';
	var cnt = 0;

	$.each(data.productList, function(index, item){

		cnt += 1;
		
		if((cnt % 4) == 1){
 			content += '<div class="row">' 
		}	 

		content += '<div class="col-sm-3">';
		content += '<div style="margin: 1em">'
		content += '<div>'
		content += '<img style="width: 100%;" src="' + item.openUri + '">';
		content += '</div>'
		content += '<span>'
		content += item.productDisplayName
		content += '</span>'
		content += '</div>'
		content += '</div>'	

 		if((cnt % 4) == 0){
			content += '</div>' 
		}	 
				 			
	});


	$("#productDisplay").append(content);
	$("#cp").val(data.navi.currentPage);

	console.log($('#cp').val());

}

//구글 로그아웃 시작
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}

function onLoad() {
    gapi.load('auth2', function() {
      gapi.auth2.init();
    });
}
//구글 로그아웃 끝

</script>
</body>
</html>