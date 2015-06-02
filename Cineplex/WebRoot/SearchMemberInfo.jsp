<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="charse=utf8">

</head>

<body>
	<div class="ReSizeRow">
		<div class="row" id="searchBarDiv">
			<div class="row">
				<div class="col-xs-4 col-md-offset-4">
					<h2>搜索用户</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-2 col-md-offset-2">
					<label>用户id</label>
				</div>
				<div class="col-xs-4">
					<input type="text" id="memberidinput" class="form-control" value=""/>
					<input type="hidden" id="memberid" class="form-control" value=""/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-2 col-md-offset-4">
					<button class="btn btn-default" onclick="searchMemberInfo()">搜索</button>
				</div>
			</div>
		</div>
		<div class="row" id="memberInfoDiv"></div>
		<div class="row" id="ticketDiv"></div>
		<div class="row" id="rechargeDiv"></div>
		<div class="row" id="duesDiv"></div>
	</div>
</body>
</html>
