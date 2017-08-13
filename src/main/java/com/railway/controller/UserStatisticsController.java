package com.railway.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  
public class UserStatisticsController {
	//无参类型接口
	//包括：generateAddress、getLedgerVersion
	@RequestMapping(value = "/user/statistics", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void getStatisticsInfo(HttpSession session,Model model,Integer type) {
		
		String userName = (String) session.getAttribute("userName");
		System.out.println("userName:" + userName + ",type=" +type);
		model.addAttribute("result", "success");		
	}

}
