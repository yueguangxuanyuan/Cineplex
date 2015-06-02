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
		<div id="rechargeMemberDiv">
			<div class="row">

				<div class="col-xs-4 col-md-offset-4">
					<h2>用户充值</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-2 col-md-offset-2">
					<label>用户id</label>
				</div>
				<div class="col-xs-4">
					<input type="text" id="memberid" class="form-control" value="" />
				</div>
			</div>
			<div class="row ">
				<div class="col-xs-2 col-md-offset-2">
					<label>充值金额</label>
				</div>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="amount" name="amount"
						value="0">
				</div>
			</div>
			<div class="row">
				<button class="btn btn-default col-xs-2 col-md-offset-4"
					onclick="subbmiRechargetMember()">充值</button>
			</div>
		</div>
	</div>
</body>
</html>
