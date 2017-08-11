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

import com.railway.bean.User;
import com.railway.service.UserService;

  
@Controller  
public class UserController {  
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource
    private UserService userService;  
      
//    @RequestMapping("/showUser")  
//    public String toIndex(HttpServletRequest request,Model model){  
//        int userId = Integer.parseInt(request.getParameter("id"));  
//        //User user = this.userService.getUserById(userId);  
//       // model.addAttribute("user", user);  
//        return "showUser";  	
//    }  
    
	/**
	 * 初始登陆界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/login.do")
	public String tologin(HttpServletRequest request, HttpServletResponse response, Model model){
		logger.debug("来自IP[" + request.getRemoteHost() + "]的访问");
		return "user/login";
	}
	
	/**
	 * 验证用户名和密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkLogin.do")
	public String login(HttpServletRequest request,Model model) {
		String result = "user/login";
		// 取得用户名
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try{
			User user = userService.getUserByUserName(username);
			if(user == null){
				result = "user/login"; 
				model.addAttribute("error", "用户不存在");
				return result;
			}
			if(user.getPassword().equals(password)){
				result = "index";
				model.addAttribute("section_name", user.getSectionName());
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "验证异常");
			result = "user/login";//验证失败
		}
		return result;
	}
  
    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout.do")  
    public String logout(HttpServletRequest request) {  
  
        String result = "user/login";  

        return result;  
    }  
}  