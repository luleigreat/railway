package com.railway.service;

import java.util.List;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;

public interface UploadService {
	public int insert(UploadInfo info);
	public List<UploadInfo> selectBySelective(String userName,String year,List<Integer> list);
}
