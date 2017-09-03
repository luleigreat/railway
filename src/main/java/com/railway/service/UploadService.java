package com.railway.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.railway.bean.UploadInfo;

public interface UploadService {
	//插入
	public int insert(UploadInfo info);
	//查找tableId在list中的记录,list:tableId集合
	public List<UploadInfo> selectBySelective(String userName,String year,List<Integer> list);
	
	List<UploadInfo> selectByTableId(@Param("tableId")int tableId);
}
