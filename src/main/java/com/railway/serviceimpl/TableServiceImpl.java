package com.railway.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.TableInfo;
import com.railway.dao.TableInfoMapper;
import com.railway.service.TableService;

@Service("tableService")  
public class TableServiceImpl implements TableService{

	@Autowired
    private TableInfoMapper tableDao; 
	
	@Override
	public List<TableInfo> getTableInfo(int type) {
		return tableDao.selectByTypeId(type);
	}

}
