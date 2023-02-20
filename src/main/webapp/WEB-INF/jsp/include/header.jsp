<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<header class="d-flex align-items-center justify-content-between mt-3">
			<h2 class="ml-3">Moonlights</h2>	
			<%-- session에 "userId"값이 저장되어 있으면 로그인된 상태 --%>
			<c:if test="${not empty userId }">
				<div class="mr-3">${userName }님 <a href="/user/signout">로그아웃</a><i class="bi bi-box-arrow-right"></i></div>
			</c:if>
		</header>
		<hr>