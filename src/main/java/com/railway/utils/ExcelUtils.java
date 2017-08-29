package com.railway.utils;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;


public class ExcelUtils {
	public static List<Object> ExcelForList(MultipartFile file, Class<?>  beanclazz,Boolean titleExist) {
        List<Object> list = new ArrayList<Object>();
        try {
            // IO流读取文件
            InputStream input = file.getInputStream();
            // 创建文档
            Workbook wb = new HSSFWorkbook(input);
            // 得到第一张工作表
            Sheet sheet = wb.getSheetAt(0);
            int i;
            if (titleExist) {
                i = 1;
            } else {
                i = 0;
            }
            // 行的遍历
            for (; i <= sheet.getLastRowNum(); i++) {
                // 得到行
                Row row = sheet.getRow(i);
                // 单元格的遍历
                // 实例化对象
                Object object = beanclazz.newInstance();

                Field[] fields = beanclazz.getDeclaredFields();
                int j = 0;
                for (Field field : fields) {
                    String fieldName = field.getName();
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, beanclazz);
                    Method getMethod = pd.getWriteMethod();
                    Cell cell = row.getCell(j++);
                    try{
                    int type = cell.getCellType();
                      
                    if (type == cell.CELL_TYPE_BOOLEAN) {
                        // 返回布尔类型的值
                        boolean value = cell.getBooleanCellValue();
                        getMethod.invoke(object, value);
                        System.out.println(object);
                        System.out.println(value);
                    } else if (type == cell.CELL_TYPE_NUMERIC) {
                        // 返回数值类型的值
                        Double d = cell.getNumericCellValue();
                        int value = d.intValue();
                        getMethod.invoke(object, new Integer(value));
                    } else if (type == cell.CELL_TYPE_STRING) {
                        // 返回字符串类型的值
                        String value = cell.getStringCellValue();
                        getMethod.invoke(object, new String(value));
                    }
                
                }catch (Exception e) {
                   System.out.println("");
                }
                }
                list.add(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
	
}
