package com.railway.service;

import java.util.List;

import com.railway.bean.User;

public interface UserService {
	public User getUserByUserName(String userName); 
	public List<User> getAll();
}
