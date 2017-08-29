package com.railway.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.railway.bean.TableInfo;
import com.railway.bean.UploadInfo;
import com.railway.bean.User;
import com.railway.service.TableService;
import com.railway.service.UploadService;
import com.railway.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);  
    private ApplicationContext ac = null;  
    @Resource  
    private UserService userService = null;  
    private TableService tableService = null;
    private UploadService uploadService = null;
  
  @Before  
  public void before() {  
      ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");  
      userService = (UserService) ac.getBean("userService");  
      tableService = (TableService) ac.getBean("tableService");
      uploadService = (UploadService) ac.getBean("uploadService");
  }  
  
    @Test  
    public void test1() {  
        User user = userService.getUserByUserName("gzz");  
        // System.out.println(user.getUserName());  
        // logger.info("值："+user.getUserName());  
        logger.info(user);  
        
        List<TableInfo> listTable = tableService.getTableInfo(1);
        for(TableInfo info:listTable){
        	logger.info(info.getTableName());
        }
        
        List<Integer> listIds = new ArrayList<Integer>();
        listIds.add(1);
        listIds.add(2);
        
        List<UploadInfo> listUpload = uploadService.selectBySelective("gzz","2017", listIds);
        for(UploadInfo info : listUpload){
        	logger.info(info.getTableId());
        }
    }  
}  
