package com.railway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.railway.utils.CipherUtil;

  
@Controller  
public class UserController {  
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
//	@Resource
//    private UserService userService;  
//      
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
		return "login";
	}
	
	/**
	 * 验证用户名和密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkLogin.do")
	public String login(HttpServletRequest request) {
		String result = "login";
		// 取得用户名
		String username = request.getParameter("username");
		//取得 密码，并用MD5加密
		String password = CipherUtil.generatePassword(request.getParameter("password"));
		
		System.out.println("Login info:" + username + "," + password);
		//String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();
		try {
			System.out.println("----------------------------");
			if (!currentUser.isAuthenticated()){//使用shiro来验证
				token.setRememberMe(true);
				currentUser.login(token);//验证角色和权限
			}
			System.out.println("result: " + result);
			result = "index";//验证成功
		} catch (Exception e) {
			logger.error(e.getMessage());
			result = "login";//验证失败
		}
		return result;
	}
  
    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout.do")  
    @ResponseBody  
    public String logout() {  
  
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "login";  
        currentUser.logout();  
        return result;  
    }  
}  