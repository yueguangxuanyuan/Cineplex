<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.Member"%>
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
	<%
		Member member = (Member) request.getAttribute("member");
	%>
	<div class="ReSizeRow maindiv">
		<div class="row">

			<div class="col-xs-4 col-md-offset-4">
				<h2>用户充值</h2>
			</div>
		</div>

		<div class="row">
			<label class="col-xs-4 col-md-offset-2">充值所用的银行卡号</label>
		</div>
		<div class="row ">
			<div class="col-md-6 col-md-offset-2">
				<input type="text" class="form-control" id="bankcard"
					name="bankcard" value=<%=member.getBankcard()%>>
			</div>
		</div>
		<div class="row ">
			<label class="col-xs-4 col-md-offset-2">充值金额</label>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-2">
				<input type="text" class="form-control" id="amount"
					name="amount" value="0">
			</div>
		</div>
		<div class="row ">
			<label class="col-xs-4 col-md-offset-2">密码</label>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-2">
				<input type="password" class="form-control" id="password"
					name="password" value="">
			</div>
		</div>
		<div class="row ">
			<label class="col-xs-4 col-md-offset-2">支付密码</label>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-2">
				<input type="password" class="form-control" id="paypassword"
					name="paypassword" value="">
			</div>
		</div>
		<div class="row">
			<button class="btn btn-default col-xs-2 col-md-offset-4"
				onclick="subbmitMemberRecharge()">充值</button>
		</div>
	</div>
</body>
</html>
