<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Moonlights 로그인</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<link rel="stylesheet" href="/static/css/style.css" type="text/css">

</head>
<body>
	<div class="container">
		<section class="content container d-flex justify-content-center my-5">		
			<div class="mr-5">
				<img src="https://cdn.pixabay.com/photo/2016/09/17/07/03/instagram-1675670_960_720.png" width="450">
			</div>
			
			<div>
				<div class="login-box d-flex justify-content-center align-items-start bg-white border rounded">		
					<div class="w-100 p-5">			
						<h2 class="text-center">Moonlights</h2>
						<br>
						<form id="loginForm">
							<input type="text" id="loginIdInput" class="form-control mt-3" placeholder="아이디">
							<input type="password" id="passwordInput" class="form-control mt-3" placeholder="패스워드">
							<button id="loginBtn" type="submit" class="btn btn-primary btn-block mt-3">로그인</button>
							<hr/>
						</form>
						
						<div class="text-center text-secondary">비밀번호를 잊으셨나요?</div>
					</div>
					
				</div>
				<div class="mt-4 p-3 d-flex justify-content-center align-items-start bg-white  border rounded">
					계정이 없으신가요? <a href="/user/signup/view">가입하기</a>
				</div>
			</div>
		</section>
		<c:import url="/WEB-INF/jsp/include/footer.jsp" />
	</div>
	
	<script>
		$(document).ready(function() {
			
			$("#loginForm").on("submit", function(e){
			// $("#loginBtn").on("click", function(){
				
				// 해당 이벤트의 기능을 모두 취소한다
				// 위에$("#loginForm").on("submit", function(e)중
				//		function()안에 만약 이벤트의 기능을 조작하려면 e를 입력. 아니면 비워줌
				e.preventDefault();
				
				let loginId = $("#loginIdInput").val();
				let password = $("#passwordInput").val();
				
				if(loginId == ""){
					alert("아이디를 입력하세요");
					return;
				}
				
				if(password == ""){
					alert("비밀번호를 입력하세요");
					return;
				}
				
				$.ajax({
					type:"post"
					, url:"/user/signin"
					, data:{"loginId":loginId, "password":password}
					, success:function(data){
						if(data.result == "success"){
							location.href = "/post/timeline/view";
						}else{
							alert("아이디/비밀번호를 확인해주세요");
						}
					}
					, error:function(){
						alert("로그인 에러");
					}
				});
				
				return false;
			});
		});
	
	</script>

</body>
</html>