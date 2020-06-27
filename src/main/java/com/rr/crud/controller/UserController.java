package com.rr.crud.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	// 添加cookie保存登录信息
	@PostMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userService.findUserByNameAndPassword(username, password);
		if (user != null) {
			/*
			 * Cookie cookie = new Cookie("currentUser", user.getUsername());
			 * cookie.setMaxAge(5 * 60); // 设置存在时间为5分钟 cookie.setPath("/"); // 设置作用域
			 * res.addCookie(cookie); // 将cookie添加到response的cookie数组中返回给客户端
			 */			
			session.setAttribute("currentUser", user);
			return "redirect:/";
		} else {
			return "login";
		}
	}
	
	// 注销
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("currentUser");
		return "redirect:/";
	}
}
