package com.railway.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.railway.bean.Admin;
import com.railway.bean.Category;
import com.railway.bean.TableInfo;
import com.railway.service.AdminService;
import com.railway.service.CategoryService;
import com.railway.service.TableService;

@Controller  
public class AdminLoginController {
	private static Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
	@Resource
    private AdminService adminService;  
	@Resource
    private CategoryService categoryService;
	@Resource
    private TableService tableService;
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
	public ModelAndView  login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/login");
		// 取得用户名
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try{
			Admin user = adminService.getAdminByUserName(username);
			if(user == null){
				mav.addObject("error", "用户不存在");
				return mav;
			}
			if(user.getPassword().equals(password)){
				LinkedHashMap<String,LinkedHashMap<Integer,String>> mapRet = new LinkedHashMap<String,LinkedHashMap<Integer,String>>();
				List<Category> list = categoryService.selectAll();
				for(Category type:list){
					List<TableInfo> listTable = tableService.getTableInfo(type.getId());
					LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
					for(TableInfo info : listTable){
						map.put(info.getId(), info.getTableName());
					}
					mapRet.put(type.getTypeName(), map);
				}
				mav.addObject("mapRet", mapRet);
				mav.setViewName("admin/index");
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			mav.addObject("error", "验证异常");
		}
		return mav;
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
