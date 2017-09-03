package com.railway.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.UploadInfo;
import com.railway.dao.UploadInfoMapper;
import com.railway.service.UploadService;

@Service("uploadService")  
public class UploadServiceImpl implements UploadService{
	@Autowired
    private UploadInfoMapper uploadDao; 
	
	@Override
	public int insert(UploadInfo info) {
		return uploadDao.insert(info);
	}

	@Override
	public List<UploadInfo> selectBySelective(String userName,String year, List<Integer> list) {
		return uploadDao.selectBySelective(userName,year, list);
	}

	@Override
	public List<UploadInfo> selectByTableId(int tableId) {
		return uploadDao.selectByTableId(tableId);
	}
	
}
