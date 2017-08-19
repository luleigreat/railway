/**
 * 教育部数据统计
 */

var LLPage_EduStatistics = {
    init: function (typeId) {
    	
    	function initTable(){
    		var type = {"id":1};
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
            for(item in data){
               // var tr = table.insertRow();
                htmlText += "<tr>";
                htmlText += "<td>" +  data[item].tableName  + "</td>";
                if(data[item].uploaded){
					htmlText += "<td>" + "已上传 "+ "</td>";
					htmlText += "<td><a href=\"javascript:;\">更新 </a>&nbsp;&nbsp;<a href=\"#\">浏览 </a></td>";
                }else{
                	htmlText += "<td>" + "未上传 "+ "</td>";
                	htmlText += "<td><a href=\"javascript:;\">上传</a></td>";
                }

//                htmlText += "<td><a href='#' onclick=\"downloadTable('" + typeId + "','" + data[item].tableName + "')\">下载</a> </td>";
                htmlText += "<td><a href=\"../../download?type=" + typeId + "&filename=" + data[item].tableName + "\">下载</a> </td>";
                htmlText += "</tr>";
            }
            $("#blocktable tbody").html(htmlText);
    	}
    	
    	initTable(typeId);
    },
	 download:function(typeId,tableName){
		var table = {
			"tableName":tableName,
			"typeId":typeId
		}
		$.ajax({
	        type : "POST",
	        url : "../../file/down",
	        data : JSON.stringify(table),
	        contentType : 'application/json;charset=utf-8',
	        dataType : 'json',
	        success : function(data) {
	            if (data.error) {
	                alert(data.error);
	            }else{
	            	//updateTable(data.result,typeId);
	            }
	        },
	        error : function(data) {
	            alert("error:" + data.code);
	        }
	
	    });
	 }
}