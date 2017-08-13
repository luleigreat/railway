<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="js/jquery-1.12.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="js/user/eduStatistics.js"></script>
	<script type="text/javascript">
		/*
		 * 对选中的标签激活active状态，对先前处于active状态但之后未被选中的标签取消active
		 * （实现左侧菜单中的标签点击后变色的效果）
		 */
		$(document).ready(function () {
			alert("hi");
			//LLPage_EduStatistics.init();
			alert("what?");
		});
	</script>
</head>
<body>
	<h1 class="page-header">
		<small>数据上传情况：
		</small>
	</h1>
	<div style="text-align: center">
        <table id = "blocktable" style="margin-bottom:8px;font-size:12px;width:739px;background-color:#eff7fa;border:solid 1px #c8e1e6;min-width: 719px;" class="xinxi3">
            <thead>
                <tr>
                    <th>表名</th>
                    <th>上传情况</th>
                    <th>操作</th>
                    <th>模板</th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</body>
</html>