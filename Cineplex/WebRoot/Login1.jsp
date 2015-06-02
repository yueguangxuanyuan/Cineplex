<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户登陆</title>

<meta http-equiv="Content-Type" content="charset=utf8">
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript" src="./jquery/jquery-1.8.3.min.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="./js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>

</head>

<div class="container">

	<h1 class="text-center">Cineplex 登陆</h1>

	<div class="row">
		<div class="col-xs-6 col-md-offset-3"
			style="background-color: #dedef8;box-shadow: 
         inset 1px -1px 1px #444, inset -1px 1px 1px #444;">
			<div style="color:red">
				<s:actionerror />
				<s:actionmessage />
			</div>
			<form role="form" method="post" action="LoginAction.action">
				<div class="checkbox-inline">
					<div class="radio">
						<label> <input type="radio" name="logintype"
							id="logintype1" value="member" checked> 会员</label>
					</div>
					<div class="radio">
						<label> <input type="radio" name="logintype"
							id="logintype2" value="staff"> 服务员</label>
					</div>
					<div class="radio">
						<label> <input type="radio" name="logintype"
							id="logintype3" value="manager"> 经理 </label>
					</div>
				</div>
				<div class="form-group">
					<label for="account">账号</label> <input type="text"
						class="form-control" id="account" name="account"
						placeholder="请输入账号">
				</div>
				<div class="form-group">
					<label for="password">密码</label> <input type="password"
						class="form-control" id="password" name="password"
						placeholder="请输入密码">
				</div>
				<button type="submit" class="btn btn-default "
					style="text-align:center;">提交</button>
			</form>
		</div>

	</div>
</div>
</html>
