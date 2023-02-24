package com.moonlight.moonlights.common;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class PermissionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler) throws IOException {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");  // userId가 null이냐 아니냐에 따라서 판단. 그래서 null을 반환할 수 있는 Integer형을 사용해야 함
		
		// /user/signin/view
		String uri = request.getRequestURI();
		
		if(userId != null) {
			// 로그인이 되어 있는 경우
			// 회원가입페이지, 로그인 페이지를 접근할 경우
			// /user로 시작하는 페이지 접근할 경우
			if(uri.startsWith("/user")) {
				// 메모 리스트로 페이지 이동
				response.sendRedirect("/post/timeline/view");
				return false;
			}
			
		}else {
			// 로그인이 되어 있지 않은 경우
			// 메모리스트, 입력화면, 디테일화면으로 접근할 경우
			// /post...
			if(uri.startsWith("/post")) {
				// 로그인 페이지로 이동
				response.sendRedirect("/user/signin/view");
				return false;
			}
		
		}

		return true;
	}
	
	// response 끝나고 돌아올 대 호출된 handle
	@Override
	public void postHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, ModelAndView modelAndView) {
		
	}
	
	// 모든 과정이 다 끝날때
	@Override
	public void afterCompletion(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, Exception ex) {
		
	}
}
