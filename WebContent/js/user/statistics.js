/**
 * 教育部数据统计
 */

var LLPage_EduStatistics = {
    init: function (typeId) {
    	
    	function initTable(){
    		var type = {"id":typeId};
            $.ajax({
                type : "POST",
                url : "../../user/statistics",
                data : JSON.stringify(type),
                contentType : 'application/json;charset=utf-8',
                dataType : 'json',
                success : function(data) {
                    if (data.error) {
                        alert(data.error);
                    }else{
                    	updateTable(data.result,typeId);
                    }
                },
                error : function(data) {
                    alert("error:" + data.code);
                }

            });
    	}
    	
    	function updateTable(data,typeId){
            var table = document.getElementById("blocktable");

            var htmlText = "";
            var id=1;
            for(item in data){
            	var uploadId = "upload_id_" + id++;
               // var tr = table.insertRow();
                htmlText += "<tr>";
                htmlText += "<td>" +  data[item].tableName  + "</td>";
                if(data[item].uploaded){
					htmlText += "<td>" + "已上传 "+ "</td>";
//					htmlText += "<td><a href=\"javascript:;\">更新 </a>&nbsp;&nbsp;<a href=\"#\">浏览 </a></td>";
					htmlText += "<td><a href=\"javascript:;\" class=\"upload_file\">更新 " +
										"<input type=\"file\" name=\"file\" id=\"" + uploadId + "\" onchange=\"ajaxUpload('"  + uploadId + "','" + data[item].tableId + "');\">" +
									"</a>&nbsp;&nbsp;" +
									"<a href=\"#\">浏览 " +
									"</a>" +
								"</td>";
                }else{
                	htmlText += "<td>" + "未上传 "+ "</td>";
                	htmlText += "<td><a href=\"javascript:;\" class=\"upload_file\">上传" +
                			"<input type=\"file\" name=\"file\" id=\"" + uploadId + "\" onchange=\"ajaxUpload('"  + uploadId + "','" + data[item].tableId + "');\">" +
                			"</a></td>";
                }

//                htmlText += "<td><a href='#' onclick=\"downloadTable('" + typeId + "','" + data[item].tableName + "')\">下载</a> </td>";
                htmlText += "<td><a href=\"../../download?type=" + typeId + "&filename=" + data[item].tableName + "\">下载</a> </td>";
                htmlText += "</tr>";
            }
            $("#blocktable tbody").html(htmlText);
    	}
    	
    	initTable(typeId);
    },
    ajaxUpload:function(id,tableId){
    	var content= $("#"+id).val();   
	    if(content.length==''){  
	       alert("请选择文件!");  
	       return false;  
	    }  
	    
	    var param = {"tableId":tableId};
	    $.ajaxFileUpload  
	    (    
	        {
	            url: "../../file/upload", //你处理上传文件的服务端  
	            secureuri:false,  
	            data:param,
	            fileElementId:id,  
	            dataType: 'json',  
	            success: function (data, status)  
	            {              
	            	if(data.result=="success"){
	            		alert("上传成功!");
	            		location.reload();
	            	}
	            	else
	            		alert("上传失败!");
	                  
	            },  
	            error: function (data, status, e)  
	            {    
	               alert("上传失败!");  
	            }  
	        }  
	    );  
	    return false;  
	 }
}