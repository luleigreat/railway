package com.railway.dao;

import com.railway.bean.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String username);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}