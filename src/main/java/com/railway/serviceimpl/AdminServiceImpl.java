package com.railway.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.Admin;
import com.railway.dao.AdminMapper;
import com.railway.service.AdminService;

@Service("adminService") 
public class AdminServiceImpl implements AdminService {
	@Autowired
    private AdminMapper adminDao;  
	@Override
	public Admin getAdminByUserName(String userName) {
        System.out.println("geAdminByUserName:" + userName);
        return this.adminDao.selectByPrimaryKey(userName);  
	}

}
