<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[ 제품 등록 폼 ]</title>
<script type="text/javascript" src = "/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
function moveToHome(){
	location.href = "/";
}

$(function(){
	
		$("#enrollBtn").on("click", function(){ 
			
			var productDisplayName = $("#productDisplayName").val();
			var openUri = $("#openUri").val();
	
			$.ajax({
					url : "/product/enroll",
					type : "post",
					data : {
						productDisplayName : productDisplayName,
						openUri : openUri					
					},
					dataType : "json",
					success : function(data){

						if(data == "1"){
							alert("제품 등록 완료")
						}else{
							alert("제품 등록 실패")
						}

					},
					error : function(e){
						console.log(e);
						alert("제품 등록 실패")
					}
		});
	});
});	
	

</script>
</head>
<body>
<h1>[ 제품 등록 ]</h1>

		<table border="1">
			<thead>
			</thead>
			<tbody>
				<tr>
					<td><label for="productDisplayName">제품명</label></td>
					<td><input type="text" id="productDisplayName" name="productDisplayName" value="Suillus"></td>
				</tr>
				<tr>
					<td><label for="openUri">제품이미지경로</label></td>
					<td><input type="text" id="openUri" name="openUri" value=""></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<input type="button" value="등록" id="enrollBtn">
						<input type="button" value="홈" onclick="moveToHome()">				
					</td> 
				</tr>	
			</tfoot>
		</table>

</body>
</html>