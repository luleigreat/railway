package com.railway.dao;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;

public interface UploadInfoMapper {
    int deleteByPrimaryKey(UploadInfoKey key);

    int insert(UploadInfo record);

    int insertSelective(UploadInfo record);

    UploadInfo selectByPrimaryKey(UploadInfoKey key);

    int updateByPrimaryKeySelective(UploadInfo record);

    int updateByPrimaryKey(UploadInfo record);
}