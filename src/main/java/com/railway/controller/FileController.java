package com.railway.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.railway.bean.TableInfo;
 
@Controller  
public class FileController {  
    /**  
     * 文件上传功能  http://blog.csdn.net/linwei_1029/article/details/8720754
     * @param file  
     * @return  
     * @throws IOException   
     */  
    @RequestMapping(value="/file/upload",method=RequestMethod.POST)  
    @ResponseBody  
    public String upload(MultipartFile file,HttpServletRequest request) throws IOException{  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();    
        File dir = new File(path,fileName);          
        if(!dir.exists()){  
            dir.mkdirs();  
        }  
        //MultipartFile自带的解析方法  
        file.transferTo(dir);  
        return "ok!";  
    }  
      
    /**  
     * 文件下载功能   参考：http://www.cnblogs.com/sonng/p/6664964.html
     * @param request  
     * @param response  
     * @throws Exception  
     */  
     @RequestMapping("/download")  //上传了之后再下载
    public ResponseEntity<byte[]> download(HttpServletRequest request,@RequestParam("filename") String filename,@RequestParam("type") String type,Model model)throws Exception{
        String path=request.getServletContext().getRealPath("/template");  //获取文件所在路径
        path = path + File.separator + type;
        System.out.println(filename);
        filename=new String(filename.getBytes("ISO-8859-1"),"UTF-8");     //不知何故，result.jsp的请求参数是ISO-8859-1编码的，但明明设置了charset=utf-8
        filename += ".xls";
        File file=new File(path+File.separator+filename);
        HttpHeaders headers=new HttpHeaders();
        String downloadFileName=new String(filename.getBytes("UTF-8"),"ISO-8859-1");  //少了这句，可能导致下载中文文件名的文档，只有后缀名的情况
        headers.setContentDispositionFormData("attachment", downloadFileName);//告知浏览器以下载方式打开
       // headers.setContentType("multipart/form-data"); 
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//设置MIME类型
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);//
        //用FileUpload组件的FileUtils读取文件，并构建成ResponseEntity<byte[]>返回给浏览器
        //HttpStatus.CREATED是HTTP的状态码201
    }
}  
