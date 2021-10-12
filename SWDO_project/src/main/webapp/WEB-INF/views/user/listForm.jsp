<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 회원 목록 ]</title>
<script type="text/javascript">

function userDelete(user_id){
	var flag = confirm("정말로 삭제하시겠습니까?")
	if(flag) location.href = "/user/delete?user_id=" + user_id;
}

</script>
</head>
<body>
<h1>[ 회원 정보 ]</h1>

<table border="1">
	<thead>
		<tr>
			<th>회원번호</th>
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			<th>가입일</th>
			<th>식제</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${userList }">
			<tr>
				<td>${user.user_no }</td>
				<td><a href="/user/detail?user_id=${user.user_id }">${user.user_id }</a></td>
				<td>${user.user_pw }</td>
				<td>${user.user_name }</td>
				<td>${user.user_indate }</td>
				<td>
					<input type="button" value="삭제" onclick="userDelete('${user.user_id }')">
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</body>
</html>