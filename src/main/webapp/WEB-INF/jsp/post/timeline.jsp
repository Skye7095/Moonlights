<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>timeline</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<link rel="stylesheet" href="/static/css/style.css" type="text/css">

</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/jsp/include/header.jsp" />
		<section class="contents d-flex justify-content-center">
			<div class="timeline-box my-4">
				<!-- 입력박스. 로그인시에만 보임 -->
				<c:if test="${not empty userId}">
					<div class="input-box border rounded">
						<textarea rows="5" class="form-control border-0" placeholder="내용을 입력하세요" id="contentInput"></textarea>
						<div class="d-flex justify-content-between align-items-center my-2">
							<input type="file" class="d-none" id="fileInput">
							<div class="upload-icon ml-2" id="imageUploadBtn"><i class="bi bi-image"></i></div>
							<button type="button" class="btn btn-primary mr-2" id="postBtn">확인</button>
						</div>
					</div>
				</c:if>
				<!-- 입력박스 끝 -->
				
				<!-- 포스트 누구나 보임 -->
				<div class="card-list">
					
					<c:forEach var="postDetail" items="${postDetailList }">
						<div class="card my-3">
								<div class="d-flex justify-content-between align-items-center border-bottom">
									<h4 class="font-weight-bold ml-2 mt-1">${postDetail.userName }</h4>
									
									<%-- 로그인한 userId와 해당 게시글의 작성자 userId가 일치하는 경우만 more-btn을 보여줘라 --%>
									<c:if test="${userId eq postDetail.userId }">
										<!--  -->
										<div data-toggle="modal" data-target="#moreMenuModal" class="mr-2 more-btn" data-postdetail-id="${postDetail.id }"><i class="bi bi-three-dots"></i></div>
									</c:if>	
								</div> 
								<div class="image d-flex justify-content-center my-3">
									<img width="300" src="${postDetail.imagePath }" >
								</div>
								<div class="likes ml-2">
									<c:choose>
										<c:when test="${postDetail.like }">
											<i class="bi bi-heart-fill text-danger heart-fill-btn" data-postdetail-id="${postDetail.id }"></i>
										</c:when>
										<c:otherwise>
											<i class="bi bi-heart heart-btn" data-postdetail-id="${postDetail.id }"></i>
										</c:otherwise>
									</c:choose>
									좋아요 ${postDetail.likeNumber }개
								</div>
								<div class="postContent d-flex my-2 mx-3">
									<b class="col-2">${postDetail.userName }</b> ${postDetail.content }
								</div>
							
							
							<!-- 댓글 -->
							<div class="comment-box">
								<div class="commentInput-box d-flex mx-1">
									<input type="text" class="form-control mr-2" placeholder="댓글을 입력하세요" id="commentInput${postDetail.id }">
									<button type="button" class="btn btn-primary commentBtn" data-postdetail-id="${postDetail.id }">확인</button>
								</div>
								<div class="commentView-box d-flex justify-content-between border-top border-bottom mt-2">
									<h5 class="font-weight-bold pl-2">comments</h5>
								</div>
								<div class="commentContent-box mx-3 my-2">
									<c:forEach var="comment" items="${postDetail.commentList }">
										<b>${comment.userName }</b>${comment.content }
										<br>
									</c:forEach>
								</div>
							</div>
							<!-- 댓글 끝 -->
						</div>
					</c:forEach>
					
				</div>
				<!-- 포스트 끝 -->
			</div>
		</section>
		<c:import url="/WEB-INF/jsp/include/footer.jsp" />
	</div>
	
	
	<!-- Modal -->
	<div class="modal fade" id="moreMenuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	     
	      <div class="modal-body text-center">
	        	<a href="#" id="deleteBtn">삭제하기</a>
	      </div>
	      
	    </div>
	  </div>
	</div>
	
	
	<script>
		$(document).ready(function(){
			
			$(".more-btn").on("click", function(){
				// 해당 more-btn 태그에 있는 post-id를 모달의 a태그에 넣는다.
				let postId = $(this).data("postdetail-id");
				
				// data-post-id=""
				$("#deleteBtn").data("postdetail-id", postId);
			});
			
			$("#deleteBtn").on("click", function(){
				let postId = $(this).data("postdetail-id");
				
				$.ajax({
					type:"get"
					, url:"/post/delete"
					, data:{"postId":postId}
					, success:function(data){
						if(data.result == "success"){
							location.reload();
						}else{
							alert("삭제 실패");
						}
					}
					, error:function(){
						alert("삭제 에러");
					}
				});
			});
			
			$(".heart-fill-btn").on("click", function(){
				let postId = $(this).data("postdetail-id");
				
				$.ajax({
					type:"get"
					, url:"/post/unlike"
					, data:{"postId":postId}
					, success:function(data){
						if(data.result == "success"){
							location.reload();
						}else{
							alert("취소 실패");
						}
					}
					, error:function(){
						alert("취소 에러");
					}
				});
			});
			
			$(".commentBtn").on("click", function(){
				let postId = $(this).data("postdetail-id");
				
				// 형제태그 값을 얻어오기
				// $(this).siblings()[0];
				
				// 형제태그 값을 얻어오기2 > 버튼 바로 이전에 태그의 값을 불러오기. 그럼 postId 필요없음
				// let comment = $(this).prev().val();
				
				// id 셀랙터를 문자열 연산으로 완성
				let content = $("#commentInput" + postId).val();
			
				$.ajax({
					type:"post"
					, url:"/post/comment/create"
					, data:{"postId":postId, "content":content}
					, success:function(data){
						if(data.result == "success"){
							location.reload();
						}else{
							alert("댓글 실패");
						}
					}
					, error:function(){
						alert("댓글 에러");
					}
				});
			});
			
			$(".heart-btn").on("click", function(){
				
				// 해당하는 버튼에 대응되는 post id를 얻어와야한다.
				let postId = $(this).data("postdetail-id");
				
				$.ajax({
					type:"get"
					, url:"/post/like"
					, data:{"postId":postId}
					, success:function(data){
						if(data.result == "success"){
							location.reload();
						}else{
							alert("좋아요 실패");
						}
					}
					, error:function(){
						alert("좋아요 에러");
					}
				});
			});
			
			$("#imageUploadBtn").on("click", function(){
				// 파일 input을 클릭한 효과
				$("#fileInput").click();
			});
			
			$("#postBtn").on("click", function(){
				let content = $("#contentInput").val();
				
				if(content == ""){
					alert("내용을 입력하세요");
					return;
				}
				
				// 파일이 선택되지 않았을 때
				if($("#fileInput")[0].files.length == 0) {
					alert("파일을 선택해주세요");
					return;
				}
				
				var formData = new FormData();
				formData.append("content", content);
				formData.append("file", $("#fileInput")[0].files[0]);
				
				$.ajax({
					type:"post"
					, url:"/post/create"
					, data:formData
					, enctype: "multipart/form-data"
					, processData:false
					, contentType:false
					, success:function(data){
						if(data.result == "success"){
							location.reload();
						}else{
							alert("post 실패");
						}
					}
					, error:function(){
						alert("post 에러");
					}
				});
			});
		});
	</script>
</body>
</html>