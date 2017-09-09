/**
 * 教育部数据统计
 */

var LLPage_AdminStatistics = {
    init: function (tableId) {
    	
    	function initTable(tableId){
    		var table = {"id":tableId};
            $.ajax({
                type : "POST",
                url : "../../admin/statistics",
                data : JSON.stringify(table),
                contentType : 'application/json;charset=utf-8',
                dataType : 'json',
                success : function(data) {
                    if (data.error) {
                        alert(data.error);
                    }else{
                    	updateTable(data.result,tableId);
                    }
                },
                error : function(data) {
                    alert("error:" + data.code);
                }

            });
    	}
    	
    	function updateTable(data,tableId){
            var table = document.getElementById("blocktable");

            var htmlText = "";
            var id=1;
            for(item in data){
            	var uploadId = "upload_id_" + id++;
               // var tr = table.insertRow();
                htmlText += "<tr>";
                htmlText += "<td>" +  data[item].userName  + "</td>";
                if(data[item].uploaded){
					htmlText += "<td><a href=\"../../scan?tableId=" + tableId + "&type=0&filename=\"\"" + "\">已上传 "+ "</a></td>";
					htmlText += "<td>" + data[item].uploadTime + "</td>";

                }else{
                	htmlText += "<td>未上传</td>";
                	htmlText += "<td></td>";
                }
            }
            $("#blocktable tbody").html(htmlText);
    	}
    	
    	initTable(tableId);
    }
}

function summarize(tableId){
	var id = {"id":tableId};
    $.ajax({
        type : "POST",
        url : "../../admin/statistics/summarize",
        data : JSON.stringify(id),
        contentType : 'application/json;charset=utf-8',
        dataType : 'json',
        success : function(data) {
            if (data.error) {
                alert(data.error);
            }else{
            	alert(data.message);
            }
        },
        error : function(data) {
            alert("error:" + data.code);
        }

    });
}