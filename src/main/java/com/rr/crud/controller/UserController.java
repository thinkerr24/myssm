package com.rr.crud.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rr.crud.bean.User;
import com.rr.crud.service.UserService;

@Controller
public class UserController {
		
	@Autowired
	private UserService userService;
	
	// 跳转登录页面
	@RequestMapping(path = "/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	// 接受来自登录页表单的数据
	@PostMapping("/login")
	public String login(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userService.findUserByNameAndPassword(username, password);
		if (user != null) {
			return "redirect:/";
		} else {
			return "login";
		}
		
	}
}
