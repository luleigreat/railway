package com.railway.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.railway.bean.UploadInfo;
import com.railway.service.UploadService;
import com.railway.utils.CommonUtil;
import com.railway.utils.ExecuteSql;
import com.railway.utils.Position;
import com.railway.utils.ReadMergeRegionExcel;
import com.railway.utils.SqlUtil;
 
@Controller  
public class FileController {  

	@Resource  
	private UploadService uploadService = null;
    /**  
     * 文件上传功能  http://blog.csdn.net/linwei_1029/article/details/8720754
     * @param file  
     * @return  
     * @throws IOException   
     */  
    @RequestMapping(value="/file/upload",method=RequestMethod.POST)  
    @ResponseBody  
    public String upload(HttpSession session,@RequestParam("tableId") int tableId,
    		@RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException{
        try{
            String fileName = file.getOriginalFilename();
            System.out.println("upload: " + fileName);
            
//          uploadToDir(file,request,"upload");
            String userName = (String) session.getAttribute("userName");
            
            boolean bRet = excel2Sql(userName,tableId,file.getInputStream());
            if(bRet){
            	//先在上传信息表中查找
            	String year = CommonUtil.getYear() + "";
            	List<Integer> listTid = new ArrayList<Integer>();
            	listTid.add(tableId);
            	List<UploadInfo> list = uploadService.selectBySelective(userName, year, listTid);
            	if(list.size() == 0){
	               	//插入到上传信息表
	            	UploadInfo info = new UploadInfo();
	            	info.setUserName(userName);
	            	info.setYear(year);
	            	info.setTableId(tableId);
	            	info.setUploaded(1);
	            	uploadService.insert(info);         		
            	}
            }
            if(bRet)
            	return "{'status':200,'result':'success'}";  
            else
            	return "{'status':200,'result':'failed'}";
        } catch (Exception e) { 
        	e.printStackTrace();
            return e.getLocalizedMessage();  
        } 
 
    }  

    @SuppressWarnings("unused")
	private void uploadToDir(MultipartFile file,HttpServletRequest request,String directory) throws IllegalStateException, IOException{
      String path = request.getSession().getServletContext().getRealPath(directory);
      String fileName = file.getOriginalFilename();
      File dir = new File(path,fileName);          
      if(!dir.exists()){  
          dir.mkdirs();  
      }
      
      //MultipartFile自带的解析方法  
      file.transferTo(dir);
    }
   
    @SuppressWarnings("deprecation")
	private boolean excel2Sql(String userName,int tableId,InputStream inputStream){    	
    	ExecuteSql exeSql = new ExecuteSql();
		try {
	    	String sInfoTable = CommonUtil.getInfoTableName(tableId);
	    	String sDataTable = CommonUtil.getDataTableName(userName, tableId);
	    	boolean isInfoTableExist = SqlUtil.checkTableExists(sInfoTable);
	    	boolean isDataTableExist = SqlUtil.checkTableExists(sDataTable);

	    	//初始化事务
	    	exeSql.initParam();
	    	exeSql.beginTrans();

	    	if(isInfoTableExist){
	    		JSONObject obj = CommonUtil.getTableSql(tableId);
	        	String deleteSql = obj.getJSONObject("infoTable").getString("delete");
	        	deleteSql = String.format(deleteSql, sInfoTable,userName);
	        	exeSql.executeTrans(deleteSql);
	    	}
	    	if(isDataTableExist){
	    		JSONObject obj = CommonUtil.getTableSql(tableId);
	        	String dropSql = obj.getJSONObject("dataTable").getString("drop");
	        	dropSql = String.format(dropSql, sDataTable);
	        	exeSql.executeTrans(dropSql);
	    	}
	    	
	    	//建表
	    	if(!isInfoTableExist){
	    		createInfoTable(exeSql,sInfoTable,tableId);
	    	}
	    	//数据表每次都要新建
	    	createDataTable(exeSql,sDataTable,tableId);

	    	
			Workbook wb = new HSSFWorkbook(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			ReadMergeRegionExcel excel = new ReadMergeRegionExcel();
			
			//读取表格信息
    		List<String> listArgs = new ArrayList<String>();
    		listArgs.add(userName);
    		JSONObject coord = CommonUtil.getTableCoordinate(tableId);
    		JSONArray infoObj = coord.getJSONArray("infoTable");
    		for(int i=0; i<infoObj.length(); i++){
    			String sPos = infoObj.getString(i);
    			Position pos = CommonUtil.parseCoord(sPos);
    			String info = excel.getMergedRegionValue(sheet, pos.row, pos.column);
    			listArgs.add(info);
    			System.out.println(info);
    		}
    		
    		//插入表格信息
    		JSONObject obj = CommonUtil.getTableSql(tableId);
    		String sInsertSql = obj.getJSONObject("infoTable").getString("insert");
    		sInsertSql = String.format(sInsertSql, sInfoTable);
    		sInsertSql = CommonUtil.formatInsertSqlInfo(sInsertSql, listArgs);
    		exeSql.executeTrans(sInsertSql);
    		
    		//读取统计数据
    		JSONArray dataArr = coord.getJSONArray("dataTable");
    		Position startPos = CommonUtil.parseCoord(dataArr.getString(0));
    		Position endPos = CommonUtil.parseCoord(dataArr.getString(1));
    		for (int i = startPos.row; i <= endPos.row; i++) {
    			Row row = sheet.getRow(i);
    			List<Integer> listInsertArgs = new ArrayList<Integer>();
    			for(int j=startPos.column; j<=endPos.column; j++){
    				Cell c = row.getCell(j-1);
    				c.setCellType(1);
    				listInsertArgs.add(Integer.parseInt(c.getStringCellValue().trim()));
    			}
    			String sInsertSqlData = obj.getJSONObject("dataTable").getString("insert");
    			sInsertSqlData = String.format(sInsertSqlData, sDataTable);
    			sInsertSqlData = CommonUtil.formatInsertSqlData(sInsertSqlData, listInsertArgs);
    			//插入数据
    			exeSql.executeTrans(sInsertSqlData);
    		}
    		
    		//提交事务
    		return exeSql.endTrans(true);
		}catch (Exception e) {
			e.printStackTrace();
			exeSql.endTrans(false);
			return false;
		}
    }
    
    private void createInfoTable(ExecuteSql exeSql,String tableName,int tableId){
    	JSONObject obj = CommonUtil.getTableSql(tableId);
    	String createSql = obj.getJSONObject("infoTable").getString("create");
    	createSql = String.format(createSql, tableName);
    	System.out.print(createSql);
    	try {
    		exeSql.executeTrans(createSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void createDataTable(ExecuteSql exeSql,String tableName,int tableId){
    	JSONObject obj = CommonUtil.getTableSql(tableId);
    	String createSql = obj.getJSONObject("dataTable").getString("create");
    	createSql = String.format(createSql, tableName);
    	System.out.print(createSql);
    	try {
    		exeSql.executeTrans(createSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
        
        if(CommonUtil.getEncoding(filename).equals("ISO-8859-1"))
        	filename=new String(filename.getBytes("ISO-8859-1"),"UTF-8");     //不知何故，result.jsp的请求参数是ISO-8859-1编码的，但明明设置了charset=utf-8
        System.out.println("download:fileName:" + filename);
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
