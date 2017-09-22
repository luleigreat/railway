<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="../../css/bootstrap.css">
	<link rel="stylesheet" href="../../css/style.css">
	<script type="text/javascript" src="../../js/jquery-1.12.3.min.js"></script>
	<script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../../js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="../../js/admin/adminStatistics.js"></script>
</head>
<body>
	<%
	  int tableId = Integer.parseInt(request.getParameter("tableId")); 
	  String tableName = request.getParameter("tableName");
	  String encode = "ISO-8859-1";
	  String tableNameShow = tableName;
//	  if (tableName.equals(new String(tableName.getBytes(encode), encode))) {
//		  
//	  }

	  tableName = new String(tableName	.getBytes("ISO-8859-1"),"UTF-8");
	%>
	<script type="text/javascript">
		/*
		 * 对选中的标签激活active状态，对先前处于active状态但之后未被选中的标签取消active
		 * （实现左侧菜单中的标签点击后变色的效果）
		 */
		$(document).ready(function () {
			LLPage_AdminStatistics.init(<%=tableId%>);
		});
	</script>
	<h1 class="page-header">
		<small><%=tableNameShow%>  数据上传情况：<span id="uploadRate"></span>
		</small>
	</h1>
	<div class="table-responsive">
	<table id="blocktable" style="text-align:center;" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="text-align:center;">站段</th>
				<th style="text-align:center;">上传情况</th>
				<th style="text-align:center;">上传时间</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>
	<button id="summarize" class="btn" onclick="summarize(<%=tableId%>)">数据汇总并导出</button>
	</div>
</body>
</html>