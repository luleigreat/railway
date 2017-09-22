package com.railway.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.railway.bean.TableInfo;
import com.railway.bean.UploadInfo;
import com.railway.bean.User;
import com.railway.model.UploadInfoView;
import com.railway.service.TableService;
import com.railway.service.UploadService;
import com.railway.service.UserService;
import com.railway.utils.CommonUtil;
import com.railway.utils.Position;
import com.railway.utils.SqlUtil;

@Controller  
public class AdminStatisticsController {

	@Resource
    private UserService userService; 
	@Resource  
	private UploadService uploadService = null;
	@Resource  
	private TableService tableService = null;
	
	@RequestMapping(value = "/admin/statistics", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object>  getStatisticsInfo(HttpSession session,Model model,@RequestBody TableInfo table) {
		System.out.println(table.getId());
		List<User> listUser = userService.getAll();
		Map<String, Object> mapRet = new HashMap<String, Object>();
		try{
			//String sInfoTable = CommonUtil.getInfoTableName(tableId);
			List<UploadInfo> listUpload = uploadService.selectByTableId(table.getId());
			
			List<UploadInfoView> listView = new ArrayList<UploadInfoView>();
			int uploadCount = 0;
			for(User user : listUser){
				boolean occured = false;
				Date date = new Date();
				for(UploadInfo upload : listUpload){
					if(user.getUsername().equals(upload.getUserName())){
						occured = true;
						date = upload.getUpdateTime(); 
						break;
					}
				}
				UploadInfoView view = new UploadInfoView();
				view.setUserName(user.getUsername());
				view.setUploaded(occured);
				if(occured){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			        String dateStr = sdf.format(date);  
					view.setDate(dateStr);
					uploadCount ++;
				}
				listView.add(view);
			}
			mapRet.put("result", listView);
			mapRet.put("uploadRate", uploadCount + "/" + listUser.size());
		}catch(Exception e){
			mapRet.put("error", "执行异常");
			e.printStackTrace();
		}
		
		
		return mapRet;
	}

//	@RequestMapping(value = "/admin/statistics/summarize", method = RequestMethod.POST, consumes = "application/json")  //上传了之后浏览
//	@ResponseBody
//	public void summarize(HttpSession session,HttpServletRequest request,HttpServletResponse response,@RequestBody TableInfo table)throws Exception{
//		int tableId = table.getId();
	 @RequestMapping("/admin/statistics/summarize")  
	public void summarize(HttpSession session,HttpServletRequest request,HttpServletResponse response,@RequestParam("tableId") int tableId)throws Exception{
	   	TableInfo tableInfo = tableService.getTableInfoById(tableId);
	   	String filename="";
	   	String type="";
	   	if(tableInfo != null){
	   		filename = tableInfo.getTableName();
	   		type = tableInfo.getTypeId().toString();
	   	}
       String path=request.getServletContext().getRealPath("/template");  //获取文件所在路径
       path = path + File.separator + type;
       
       System.out.println("statistics-filename:" + filename);
       
       response.reset();
       response.setContentType("application/msexcel");
       response.setContentType("application/octet-stream");
       response.setHeader("Content-disposition","attachment;  filename=\""  + URLEncoder.encode(filename + "_统计结果"  + ".xls", "utf-8") + "\"");				

	   OutputStream out = null;
       try {
	        File file=new File(path + File.separator+ filename + ".xls");
	        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));  
			 //读取excel模板  
	        HSSFWorkbook wb = new HSSFWorkbook(fs);  	
	        List<User> listUser = userService.getAll();
	        List<List<List<String>>> listData = new ArrayList<List<List<String>>>();
	        for(User user : listUser){
	        	List<List<String>> listTmp = getUserData(user.getUsername(),tableId);	
	        	listData.add(listTmp);
	        }
	        
	        List<List<Integer>> listSum = summarize(listData);
	        
	        //职工继续教育表，特殊处理
	        int extra = 0;
	        if(tableId == 2){
	        	List<Integer> listExtra = getExtraData(tableId);
		        extra = summarizeList(listExtra);
	        }
	        
	        boolean bRet = data2Excel(wb,listSum,extra,tableId);
	        if(bRet){
	        	out = response.getOutputStream();
				wb.write(out);
	        }
	        
//			//修改模板内容导出新模板  
	        FileOutputStream out2 = new FileOutputStream("d:/export.xls");  
	        wb.write(out2);  
	        out2.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
	        if(out!=null){
	        	out.close();
	        }
		}
   }
	
	private List<List<String>> getUserData(String userName,int tableId){
		String sDataTable = CommonUtil.getDataTableName(userName, tableId);
		boolean isDataTableExist = SqlUtil.checkTableExists(sDataTable);
		if(!isDataTableExist)
			return null;
		try{
			JSONObject obj = CommonUtil.getTableSql(tableId);
			String selectSql = obj.getJSONObject("dataTable").getString("select");
			selectSql = String.format(selectSql, sDataTable);
			System.out.println(selectSql);
			List<List<String>> listData = SqlUtil.executeSql(selectSql);
			return listData;	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Integer> getExtraData(int tableId){
		String sInfoTable = CommonUtil.getInfoTableName(tableId);
		try{
			JSONObject obj = CommonUtil.getTableSql(tableId);
			String selectSql = "select * from " + sInfoTable;
			List<List<String>> listInfo = SqlUtil.executeSql(selectSql);
			List<Integer> listRet = new ArrayList<Integer>();
			for(List<String> list :listInfo){
				String value = list.get(5);
				listRet.add(Integer.parseInt(value));
			}
			return listRet;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private List<List<Integer>> summarize(List<List<List<String>>> listData){
		List<List<Integer>> listRet = new ArrayList<List<Integer>>();
		for(int i=0; i<listData.size(); i++){
			List<List<String>> list = listData.get(i);
			for(int j=0; j<list.size(); j++){
				List<String> list2 = list.get(j);
				List<Integer> listAdd = null;
				if(listRet.size() <= j){
					listAdd = new ArrayList<Integer>();
					listRet.add(listAdd);
				}else{
					listAdd = listRet.get(j);
				}
				for(int k=0; k<list2.size(); k++){
					if(listAdd.size() <= k){
						listAdd.add(Integer.parseInt(list2.get(k)));
					}else{
						listAdd.set(k, listAdd.get(k) + Integer.parseInt(list2.get(k)));
					}
				}
				listRet.set(j, listAdd);
			}
		}
		return listRet;
	}
	
	private int summarizeList(List<Integer> listExtra){
		int sum = 0;
		for(Integer num : listExtra){
			sum += num;
		}
		return sum;
	}
	
	
	
    private boolean data2Excel(HSSFWorkbook wb,List<List<Integer>> listData,int extra,int tableId) throws Exception{
		try{
			//读取了模板内所有sheet内容  
	        HSSFSheet sheet = wb.getSheetAt(0);  
			JSONObject coord = CommonUtil.getTableCoordinate(tableId);
			
			//读取统计数据
			JSONArray dataArr = coord.getJSONArray("dataTable");
			Position startPos = CommonUtil.parseCoord(dataArr.getString(0));
			Position endPos = CommonUtil.parseCoord(dataArr.getString(1));
			for (int i = startPos.row; i <= endPos.row; i++) {
				Row row = sheet.getRow(i);
				for(int j=startPos.column; j<=endPos.column; j++){
					Cell c = row.getCell(j);
					Integer value = listData.get(i-startPos.row).get(j-startPos.column);
					c.setCellValue(value);
				}
			}
			//extra...
			if(tableId == 2){
				Row row = sheet.getRow(15);
				Cell c = row.getCell(16);
				c.setCellValue(extra);
			}
		}catch(Exception e){
			return false;
		}
        
		return true;
    }
}
