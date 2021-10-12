<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 회원 가입 폼]</title>

<!-- 앱의 클라이언트 ID 지정 -->
<meta name="google-signin-client_id" content="895850026348-73a2do4bd75ujv8rirts59gu3i977ck5.apps.googleusercontent.com">
<!-- google login&out 관련 js를 받아옴 -->
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>	
	
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

<!-- google Font:Abril Fatface -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap" rel="stylesheet">

<link rel="stylesheet" href="./../../resources/css/custom.css">

<script type="text/javascript" src = "/resources/js/jquery-3.6.0.js"></script>   
<script type="text/javascript">

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

//유효성 검사
function formCheck(){
	
	var id = document.getElementById("user_id").value;
	var pw = document.getElementById("user_pw").value;
	var pwCheck = document.getElementById("user_pwCheck").value;
	var name = document.getElementById("user_name").value;
	
	if(id == "" || id.length == 0){
		alert("아이디를 입력해주세요");
		return false;
	}else if(id.length < 3 || id.length > 8){
		alert("아이디는 3~8글자 사이로 입력해주세요");
		return false;
	}

	if(pw == "" || pw.length == 0){
		alert("비밀번호를 입력해주세요");
		return false;
	}

	if(pwCheck != pw){
		alert("동일한 비밀번호를 입력해주세요");
		return false;
	}
	
	if(name == "" || name.length == 0){
		alert("이름을 입력해주세요");
		return false;		
	}
	
	return true;
}
</script>

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

<nav class="row" style="display: flex;justify-content: center;">
	<div id="grid" class="row">
		<div id="grid" class="col-sm-4">
		<a href="/" style="padding-top: 2em;width:33%"><span style="padding: 2em;font-weight: bolder;width: 2em;">FIND</span> </a>
		</div>
		<div id="grid" class="col-sm-4">
		<a href="/board/boardListForm" style="padding-top: 2em;width:33%"><span style="padding: 2em;font-weight: bolder;width: 2em;">FORUM</span> </a>
		</div>
		<div id="grid" class="col-sm-4">
		<a href="/product/productListForm" style="padding-top: 2em;width:33%"><span style="padding: 2em;font-weight: bolder;width: 2em;">MUSHROOMS</span> </a>
		</div>
	</div>
</nav>

<section class="row" style="display: flex;justify-content: center;">
	<div id="grid" class="">
		<div id="grid">
			<h2>회원가입</h2>
		</div>	
		<form action="/user/join" method="post" onsubmit="return formCheck();">
			<div id="grid">
				<input type="text" class="" style="border-bottom: 1px solid;" id="user_name" name="user_name" placeholder="이름">
			</div>
			<div id="grid">
				<input type="text" class="" style="border-bottom: 1px solid;" id="user_id" name="user_id" placeholder="아이디">
			</div>
			<div id="grid">
				<input type="text" class="" style="border-bottom: 1px solid;" id="user_pw" name="user_pw" placeholder="비밀번호">
			</div>
			<div id="grid">
				<input type="text" class="" style="border-bottom: 1px solid;" id="user_pwCheck" name="user_pwCheck" placeholder="비밀번호 확인">
			</div>
			<div id="grid" style="display: flex">
				<button type="submit" class="" style="width: 100%;background-color: black;color:white; "><small>회원 가입</small></button>
			</div>
		</form>
	</div>
</section>

</body>
</html>