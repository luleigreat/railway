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
                    	updateTable(data.result,data.uploadRate,tableId);
                    }
                },
                error : function(data) {
                    alert("error:" + data.code);
                }

            });
    	}
    	
    	function updateTable(data,rate,tableId){
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
            $("#uploadRate").html("(" + rate + ")");
    	}
    	
    	initTable(tableId);
    }
}

function summarize(tableId){
	var url = "../../admin/statistics/summarize?tableId=" + tableId;
    $.ajax({
        url : url,
//        data : JSON.stringify(id),
        contentType : 'application/json;charset=utf-8',
//        dataType : 'json',
        success: function(response, status, request) {
            var disp = request.getResponseHeader('Content-Disposition');
            if (disp && disp.search('attachment') != -1) {  //判断是否为文件
                var form = $('<form method="POST" action="' + url + '">');
                $('body').append(form);
                form.submit(); //自动提交
            }
        },
        error : function(data) {
            alert("error:" + data.code);
        }

    });
}