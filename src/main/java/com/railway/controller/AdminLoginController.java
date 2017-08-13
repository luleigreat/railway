package com.railway.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.railway.bean.Admin;
import com.railway.service.AdminService;

@Controller  
public class AdminLoginController {
	private static Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
	@Resource
    private AdminService adminService;  
	
	/**
	 * 初始登陆界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin_login.do")
	public String tologin(HttpServletRequest request, HttpServletResponse response, Model model){
		logger.debug("来自IP[" + request.getRemoteHost() + "]的访问");
		return "admin/login";
	}
	
	/**
	 * 验证用户名和密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkAdminLogin.do")
	public String login(HttpServletRequest request,Model model) {
		String result = "admin/login";
		// 取得用户名
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try{
			Admin user = adminService.getAdminByUserName(username);
			if(user == null){
				result = "admin/login"; 
				model.addAttribute("error", "用户不存在");
				return result;
			}
			if(user.getPassword().equals(password)){
				result = "admin/index";
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "验证异常");
			result = "admin/login";//验证失败
		}
		return result;
	}
  
    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/admin_logout.do")  
    public String logout() {  
  
        String result = "admin/login";  
        return result;  
    }  
}
