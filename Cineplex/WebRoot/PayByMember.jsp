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

<meta http-equiv="Content-Type" content="charset=utf8">
</head>

<body>
	<div class="ReSizeRow">
		<div class="row">
			<div class="col-xs-4 col-md-offset-4">
				<label>会员支付</label>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>memberid</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="memberid">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>支付方式</label>
			</div>
			<div class="col-xs-4">
				<select id="payType">
				   <option value="现金支付">现金支付</option>
				   <option value="会员卡支付">会员卡支付</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>支付密码</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="paypassword"
					placeholder="选择会员卡支付才需要填">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2 col-md-offset-2 ">
				<button class="btn btn-default" onclick="memberSubbmitBuyTickets()">提交</button>
			</div>
		</div>
	</div>
</body>
</html>
