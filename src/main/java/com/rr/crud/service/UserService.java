package com.rr.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rr.crud.bean.User;
import com.rr.crud.dao.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User findUserByNameAndPassword(String username, String password) {
		return userMapper.selectUserByNameAndPassword(username, password);
	}
}
