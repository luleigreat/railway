package com.railway.dao;

import java.util.List;

import com.railway.bean.Category;
import com.railway.bean.TableInfo;

public interface TableInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableInfo record);

    int insertSelective(TableInfo record);

    TableInfo selectByPrimaryKey(Integer id);
    
    List<TableInfo> selectByTypeId(Integer type_id);

    int updateByPrimaryKeySelective(TableInfo record);

    int updateByPrimaryKey(TableInfo record);
}