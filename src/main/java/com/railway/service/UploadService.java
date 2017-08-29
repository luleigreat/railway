package com.railway.service;

import java.util.List;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;

public interface UploadService {
	//插入
	public int insert(UploadInfo info);
	//查找tableId在list中的记录
	public List<UploadInfo> selectBySelective(String userName,String year,List<Integer> list);
	//更新
	public int updateBySelective(String userName,String year,int tableid,int uploaded);
}
