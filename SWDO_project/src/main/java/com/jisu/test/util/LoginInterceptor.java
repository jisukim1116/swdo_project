package com.jisu.test.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jisu.test.vo.UserVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{
		
		HttpSession session = request.getSession();
		
		UserVO user = (UserVO) session.getAttribute("loginVO");
		
		//로그인되지 않은 경우 로그인 페이지로 이동
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/user/loginForm");
			return false;
		}
		
		//로그인 된 경우 요청한 경로로 진행
		return super.preHandle(request, response, handler);
	}
}
