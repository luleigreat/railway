package com.railway.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;
import com.railway.dao.UploadInfoMapper;
import com.railway.service.UploadService;

@Service("uploadService")  
public class UploadServiceImpl implements UploadService{
	@Autowired
    private UploadInfoMapper uploadDao; 
	
	@Override
	public int insert(UploadInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UploadInfo> selectBySelective(String userName,String year, List<Integer> list) {
		// TODO Auto-generated method stub
		return uploadDao.selectBySelective(userName,year, list);
	}
	
}
