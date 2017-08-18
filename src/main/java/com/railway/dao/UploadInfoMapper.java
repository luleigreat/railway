package com.railway.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;

public interface UploadInfoMapper {
    int deleteByPrimaryKey(UploadInfoKey key);

    int insert(UploadInfo record);

    int insertSelective(UploadInfo record);

    UploadInfo selectByPrimaryKey(UploadInfoKey key);

    int updateByPrimaryKeySelective(UploadInfo record);

    int updateByPrimaryKey(UploadInfo record);
    
    List<UploadInfo> selectBySelective(@Param("userName")String userName,@Param("year")String year,@Param("list")List<Integer> list);
}