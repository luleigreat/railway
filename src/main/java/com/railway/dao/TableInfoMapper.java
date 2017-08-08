package com.railway.dao;

import com.railway.bean.TableInfo;

public interface TableInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableInfo record);

    int insertSelective(TableInfo record);

    TableInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TableInfo record);

    int updateByPrimaryKey(TableInfo record);
}