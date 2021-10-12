package com.jisu.test.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.test.dao.UserDAO;
import com.jisu.test.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private HttpSession session;
	
	public String userInsert(UserVO user) {
		int cnt = dao.userInsert(user);
		String path;
		
		if(cnt == 0) {
			System.out.println("회원가입 실패");
			path = "redirect:/user/joinForm";
		}else {
			System.out.println("회원가입 성공");

			path = "redirect:/";
		}
		
		return path;
	}
	
	public ArrayList<UserVO> userSelectAll(){
		ArrayList<UserVO> list = dao.userSelectAll();
		return list;
	}
	
	public void userDelete(UserVO user) {
		int cnt = dao.userDelete(user);
		
		if(cnt == 0) {
			System.out.println("회원정보 삭제 실패");
		}else {
			System.out.println("회원정보 삭제 성공");
		}
	}
	
	public UserVO userSelectOne(UserVO user) {
		UserVO result = dao.userSelectOne(user);
		return result;
	}
	
	public int userUpdate(UserVO user) {
		
		int cnt = dao.userUpdate(user);

		if(cnt == 0) {
			System.out.println("회원정보 갱신 실패");

		}else {
			System.out.println("회원정보 갱신 성공");
		}
		
		return cnt;
	}
	
	public String userLogin(UserVO user) {
		UserVO result = dao.userLogin(user);
		String path;
		
		if (result == null) {
			System.out.println("ID가 존재하지 않습니다.");
			path = "redirect:/user/loginForm";
		} else {
			System.out.println("ID가 존재합니다.");
			
			if (result.getUser_pw().equals(user.getUser_pw())) {
				System.out.println("로그인 성공");
				session.setAttribute("loginVO", result);
				
				path = "redirect:/";
				
			} else {
				System.out.println("로그인 실패");
				path = "redirect:/user/loginForm";
			}
		}
		
		return path;
	}
	
	public String userLogout() {
		session.removeAttribute("loginVO");
		System.out.println("로그아웃 성공");
		
		return "redirect:/";
	}
	
	public void userGoogleLogin(UserVO user) {
		session.setAttribute("loginVO", user);
		System.out.println("구글 로그인 성공");
	}
	

}
