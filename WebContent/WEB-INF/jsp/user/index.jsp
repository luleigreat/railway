<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="js/user/index.js"></script>
<title>${section_name}</title>
</head>
<body>
	<h1>${section_name}</h1>
	<a href="${ctx}/logout.do">退出</a>
	
	<label>请先把导入类别：</label> 
	<select name="type" id="">
		<option value="1">教育部统计报表</option>
		<option value="2">铁总数据预统计</option>
		<option value="3">铁总职教统计</option>
	</select>
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