package com.railway.service;

import com.railway.bean.UploadInfo;
import com.railway.bean.UploadInfoKey;

public interface UserStatisticsService {
	public UploadInfo getUploadInfoByKey(UploadInfoKey key);
	public int insert(UploadInfo info);
	
}
