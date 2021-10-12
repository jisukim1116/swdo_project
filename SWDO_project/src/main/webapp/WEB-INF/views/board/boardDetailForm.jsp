<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 게시글 상세 조회 ]</title>

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

function moveToBoardUpdateForm(board_no){
	location.href = "/board/boardUpdateForm?board_no=" + board_no;
} 
function boardDelete(board_no){
	location.href = "/board/boardDelete?board_no=" + board_no;
}

$(function(){ 

	function replySelectAll(data){

		var content = "";

		$.each(data.replyList, function(index,item){

			content += '<tr>';
			content += '<td>'+item.user_id+'</td>';
			content += '<td style="width:30%">'+item.reply_content+'</td>';
			content += '<td>'+item.reply_indate+'</td>';

			if(data.user_id == item.user_id){
				content += '<td style="width:30%">';
				content += '<textarea style="width:100%;resize: none" id = "updated_reply_content" placeholder="댓글 입력" ></textarea>';
				content += '</td>';
				content += '<td>';
				content += '<input type="button" class="replyUptBtn" value="수정" style="margin-bottom: 0.5em">';
				content += '<input type="button" class="replyDelBtn" value="삭제">';
				content += '<input type="hidden" class="replyNo" value="' + item.reply_no + '">';
				content += '<input type="hidden" class="boardNo" value="' + item.board_no + '">';
				content += '</td>';
			}

			content += '</tr>';
		});

			$("#replyDiv").html(content); // 새로 작성한 리플을 추가해서 보여준다.
			$("#reply_content").val(""); // 작성한 리플 내용을 초기화한다.
	} 

	function replyDelete(){
		$(".replyDelBtn").on("click", function(){
			var reply_no = $(this).nextAll(".replyNo").val();
			var board_no = $(this).nextAll(".boardNo").val();
			
			$.ajax({
				url : "/board/replyDelete",
				type : "post",
				data : {
					reply_no : reply_no,
					board_no : board_no
				},
				dataType : "json",
				success : function(data){
					console.log(data);

					replySelectAll(data);

					replyDelete(); 

					replyUpdate();

				},
				error : function(e){
					console.log(e);
				}
				
			});
		});
	}

	function replyUpdate(){
		$(".replyUptBtn").on("click", function(){
			
			var reply_no = $(this).nextAll(".replyNo").val();
			var board_no = $(this).nextAll(".boardNo").val();
			var updated_reply_content = $(this).parent().next().find("#updated_reply_content").val();
			
			$.ajax({
				url : "/board/replyUpdate",
				type : "post",
				data : {
					reply_no : reply_no,
					board_no : board_no,
					reply_content : updated_reply_content
				},
				dataType : "json",
				success : function(data){
					console.log(data);

					replySelectAll(data);

					replyDelete();
					
					replyUpdate();
				},
				error : function(e){
					console.log(e);
				}
				
			});
		});
	}	
	

	$("#replyBtn").on("click", function(){ //#replyBtn 버튼에 click 이벤트를 부여
		
		var reply_content = $("#reply_content").val();

		var board_no = $("#board_no").val();

		$.ajax({
				url : "/board/replyInsert",
				type : "post",
				data : {
					reply_content : reply_content,
					board_no : board_no
				},
				dataType : "json",
				success : function(data){
					console.log(data.user_id);

					replySelectAll(data);
					replyDelete(); 
					replyUpdate();
					
				},
				error : function(e){
					console.log(e);

				}
		});
	});

	replyDelete();
	replyUpdate();
	
});

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
		<table class="table" style="table-layout:fixed;width:50%;">
			<tr>
				<th>제목</th>
				<td style="width: 80%">${boardDetail.board_title } </td>
			</tr>
			<tr>
				<th>작성자</th>
				<td style="width: 80%">${boardDetail.user_id } </td>
			</tr>
			<tr>
				<th>내용</th>
				<td style="width: 80%;height: 10em">${boardDetail.board_content } </td>
			</tr>
			<tr>
				<th>조회수</th>
				<td style="width: 80%">${boardDetail.board_hits } </td>
			</tr>
			<tr>
				<th>작성일</th>
				<td style="width: 80%">${boardDetail.board_indate } </td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td style="width: 80%"><a href = "/board/download?board_no=${boardDetail.board_no }">${boardDetail.board_original }</a> </td>
			</tr>
		</table>
	</div>
	
	<c:if test="${sessionScope.loginVO.user_id == boardDetail.user_id }">
	<div style="display: flex;justify-content: center;margin-bottom: 4em">
		<div style="display: flex;justify-content: flex-end; width: 50%">
		
			<div>
				<input type="button" value="수정" onclick="moveToBoardUpdateForm(${boardDetail.board_no })" style="height:2em;width:4.5em;"> 
			</div>
			
			<div>
				<input type="button" value="삭제" onclick="boardDelete(${boardDetail.board_no })" style="height:2em;width:4.5em;margin-left: 1em"> 
			</div>
			
		</div>
	</div>
	</c:if> 
	
	<div style="display: flex;justify-content: center;">
		<table class="table" style="table-layout:fixed; width:50%;">
			<tbody id="replyDiv">
				<c:forEach var="reply" items="${map.replyList }" varStatus = "status">
					<tr>
						<td>${reply.user_id }</td>
						<td style="width:30%">${reply.reply_content }</td>
						<td>${reply.reply_indate }</td>
						<c:if test="${sessionScope.loginVO.user_id == reply.user_id }">
						<td style="width:30%">
							<textarea style="width:100%;resize: none" id = "updated_reply_content" placeholder="댓글 입력" ></textarea>
						</td>
						<td>
							<input type="button" class="replyUptBtn" value="수정" style="margin-bottom: 0.5em">
							<input type="button" class="replyDelBtn" value="삭제">
							
							<input type="hidden" class="replyNo" value="${reply.reply_no }">
							<input type="hidden" class="boardNo" value="${reply.board_no }">	
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div> 
	
	<div style="display: flex;justify-content: center; margin-bottom: 1em">
		<div style="display: flex;justify-content: center;width:50%;background-color: rgb(244,244,244);">
			<textarea id = "reply_content" style="width:100%;margin:0.5em; resize: none;"></textarea>
		</div>
	</div>
		
	<div style="display: flex;justify-content: center;">
		<div style="width:50%;display: flex;justify-content: flex-end;">
			<div>
				<input type = "button" value = "댓글등록" id = "replyBtn" style="height:2em;width:6em;">
			</div>
		</div>
	</div>
	
	<input type = "hidden" value = "${boardDetail.board_no }" id = "board_no">
	

</section>
	
	
<!-- <form action="/board/boardWrite" method = "post" enctype="multipart/form-data">
	<table border="1">
		<tr>
			<td>글 제목</td>
			<td>
			<input type ="text" name = "board_title">
			</td>
		</tr>
		<tr>
			<td>글 내용</td>
			<td>
				<textarea rows="10" cols="10" name = "board_content"></textarea>
			</td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td>
				<input type = "file" name="upload"> name은 아무 거나 상관 없음. 어차피 VO에 저장 안함
			</td>
		</tr>
		<tr>
			<td colspan="2"> 
			<input type = "submit" value = "등록">	
			</td>
		</tr>
	</table>
</form> -->
	
</body>
</html>