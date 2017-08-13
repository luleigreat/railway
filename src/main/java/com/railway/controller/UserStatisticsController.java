package com.railway.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.railway.bean.Category;

@Controller  
public class UserStatisticsController {
	//无参类型接口
	//包括：generateAddress、getLedgerVersion
	@RequestMapping(value = "/user/statistics", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object>  getStatisticsInfo(HttpSession session,Model model,@RequestBody Category type) {
		Map<String, Object> mapRet = new HashMap<String, Object>();
		String userName = (String) session.getAttribute("userName");
		System.out.println("userName:" + userName + ",type=" +type.getId());	
		mapRet.put("result", "success");
		return mapRet;
	}

}
