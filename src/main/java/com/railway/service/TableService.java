package com.railway.service;

import java.util.List;

import com.railway.bean.TableInfo;

public interface TableService {
	public List<TableInfo> getTableInfo(int type);
	public TableInfo getTableInfoById(int tableId);
}
