<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 게시글 생성 ]</title>
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

<style type="text/css">
input::placeholder{
	font-size: medium;
}
td{
	word-break: break-word;
}
</style>

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
				<a href="/user/detail?user_id=${sessionScope.loginVO.user_id }" style="" title="">					
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

<section class="row" style="padding-top: 3em">

	<div style="display: flex;justify-content: center;">
		<form action="/board/boardWrite" method = "post" enctype="multipart/form-data">
			<div style="display: flex;justify-content: center;">
				<table class="table" style="table-layout:fixed;width:50%;">
					<tr>
						<th>제목</th>
						<td style="width: 80%"><input type ="text" name = "board_title"> </td>
					</tr>
					<tr>
						<th>내용</th>
						<td style="width: 80%;height: 10em">
							<textarea style="width:100%;height:100%;resize: none" name="board_content"></textarea>
						</td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td style="width: 80%">
							<input type = "file" name="upload">
						</td>
					</tr>
				</table>
			</div>
			<div style="display: flex;justify-content: center;margin-bottom: 4em">
				<div style="display: flex;justify-content: flex-end; width: 50%">		
					<div>
						<input type="submit" value="등록" style="height:2em;width:4.5em;"> 
					</div>
				</div>
			</div>
		</form>
	</div>

</section>

<%-- 	<form action="/board/boardUpdate" method="post" enctype="multipart/form-data">
		글 번호 : ${boardDetail.board_no} <br>
		글 제목 : <input type="text" name="board_title" value="${boardDetail.board_title }"> <br>
		작성자 : ${boardDetail.user_id } <br>
		조회수 : ${boardDetail.board_hits } <br>
		글 내용 : <textarea rows="10" cols="10" name="board_content">${boardDetail.board_content }</textarea> <br>
		작성일 : ${boardDetail.board_indate } <br>
		
		<c:if test="${boardDetail.board_original }">
			저장된 파일명: ${boardDetail.board_original } <br>
		</c:if> 

		첨부파일 : <input type="file" name = "upload"> <br> 
		
		<input type="hidden" name="board_no" value="${boardDetail.board_no}"> 
		<input type="submit" value="수정 완료">
	</form> --%>


</body>
</html>