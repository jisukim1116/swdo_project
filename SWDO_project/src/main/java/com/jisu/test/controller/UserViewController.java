package com.jisu.test.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jisu.test.vo.UserVO;
import com.jisu.test.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserViewController {
	
	@Autowired
	private UserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UserViewController.class);
	
	@RequestMapping(value = "/joinForm", method = RequestMethod.GET)
	public String moveToJoinForm() {
		return "user/joinForm";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO user) {
		String path = service.userInsert(user);
		return path; 
	}
	
	@RequestMapping(value = "/listForm", method = RequestMethod.GET)
	public String listForm(Model model) {
		ArrayList<UserVO> list = service.userSelectAll();
		model.addAttribute("userList", list);
		return "user/listForm";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(UserVO user) {
		
		service.userDelete(user);
		service.userLogout();
		return "redirect:/user/listForm";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String moveToDetailForm(UserVO user, Model model, @RequestParam(name="cnt_upt", defaultValue="2")String cnt_upt) {
		UserVO result = service.userSelectOne(user);
		model.addAttribute("detail", result);
		model.addAttribute("cnt_upt", cnt_upt);
		return "user/detailForm";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(UserVO user) {
		int cnt = service.userUpdate(user);
		String cnt_upt = Integer.toString(cnt);
		
		return "redirect:/user/detail?user_id=" + user.getUser_id() + "&cnt_upt=" + cnt_upt;
	}
	
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String moveToLoginForm() {
		return "user/loginForm";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserVO user) {
		
		logger.info("--------------------");
		logger.info("user : {}", user);
		
		String path = service.userLogin(user);
		return path;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		String path = service.userLogout();
		return path;
	}
	
}
