<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 제품 목록 ]</title>
<script type="text/javascript">
function productDelete(openUri){
	location.href = "/product/delete?openUri=" + openUri;
}

function moveToHome(){
	location.href = "/";
}

</script>
</head>
<body>
<h1>[ 제품 정보 ]</h1>

	<table border="1"> 
		<thead>
			<tr>
				<th>제품명</th>
				<th>제품이미지경로</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="productList" items="${productList }">
				<tr>
					<td>${productList.productDisplayName}</td>
					<td>${productList.openUri}</td>
					<td><input type="button" value="삭제" onclick="productDelete('${productList.openUri }')"></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="3"><input type="button" value="홈" onclick="moveToHome()"></td>
			</tr>
		</tfoot>
	</table>

	
</body>
</html>