package com.railway.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.User;
import com.railway.dao.UserMapper;
import com.railway.service.UserService;

@Service("userService")  
public class UserServiceImpl implements UserService {  
	@Autowired
    private UserMapper userDao;  
    @Override  
    public User getUserByUserName(String userName) {  
        System.out.println("getUserByUserName:" + userName);
        return this.userDao.selectByPrimaryKey(userName);  
    }  
  
}  