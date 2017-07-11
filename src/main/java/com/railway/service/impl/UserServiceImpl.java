package com.railway.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.railway.Dao.IUserDao;
import com.railway.bean.User;
import com.railway.service.IUserService;

@Service("userService")  
public class UserServiceImpl implements IUserService {  
    @Resource  
    private IUserDao userDao;  
    @Override  
    public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  