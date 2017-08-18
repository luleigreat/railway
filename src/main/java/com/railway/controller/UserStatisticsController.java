package com.railway.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.railway.bean.Category;
import com.railway.bean.TableInfo;
import com.railway.bean.UploadInfo;
import com.railway.model.TableInfoView;
import com.railway.service.TableService;
import com.railway.service.UploadService;

@Controller  
public class UserStatisticsController {
	@Resource  
	private TableService tableService = null;
	@Resource  
	private UploadService uploadService = null;
	//无参类型接口
	@SuppressWarnings("deprecation")
	//包括：generateAddress、getLedgerVersion
	@RequestMapping(value = "/user/statistics", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object>  getStatisticsInfo(HttpSession session,Model model,@RequestBody Category type) {
		String userName = (String) session.getAttribute("userName");
		System.out.println("userName:" + userName + ",type=" +type.getId());
		int year = 2017;
		if(new Date().getYear() >= year){
			year = new Date().getYear();
		}
		List<TableInfo> listTable = tableService.getTableInfo(type.getId());
		List<Integer> listIds = new ArrayList<Integer>();
		for(TableInfo info : listTable){
			listIds.add(info.getId());
		}
		List<UploadInfo> listUpload = uploadService.selectBySelective(userName, year + "",listIds);	
		
		List<TableInfoView> listView = new ArrayList<TableInfoView>();
		for(TableInfo info : listTable){
			TableInfoView view = new TableInfoView();
			view.setTableName(info.getTableName());
			view.setTemplatePath(info.getTemplatePath());
			boolean bFound = false;
			for(UploadInfo upInfo : listUpload){
				if(upInfo.getTableId() == info.getId()){
					bFound = true;
				}
			}
			view.setUploaded(bFound);
			listView.add(view);
		}
		
		Map<String, Object> mapRet = new HashMap<String, Object>();
		mapRet.put("result", listView);
		return mapRet;
	}

	
}
