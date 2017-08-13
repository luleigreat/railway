/**
 * 教育部数据统计
 */

var LLPage_EduStatistics = {
    init: function () {
    	
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
                    	alert(data.result);
                    }
                },
                error : function(data) {
                    alert("error:" + data.code);
                }

            });
    	}
    	
    	initTable();
    }
}