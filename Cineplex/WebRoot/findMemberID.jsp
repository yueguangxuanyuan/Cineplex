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
<meta http-equiv="Content-Type" content="charset=utf-8">

<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<link href="./css/global.css" rel="stylesheet">


<script type="text/javascript" src="./jquery/jquery-2.1.3.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="./js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>

</head>


<body>
	<div class="container" style="padding-top:10%">
		<div class="col-xs-12 col-md-offset-3 col-md-6" style="background:url(./pic/window_bg.jpg);border-radius:15px; padding:3%;">
			<div style="text-align:center;font-size:20px">找回帐号</div>
			<form class="form-horizontal register-form" style="padding-top:3%">
				<div class="form-group">
					<label for="name" class="col-md-3 control-label" >姓名</label>
					<div class=" col-md-8">	
						<input type="text" class="form-control" id="name" name="name" value="" />
					</div>
				</div>

				<div class="form-group">
					<label for="bankcard" class="col-md-3 control-label" >银行卡</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="bankcard" name="bankcard" value="" />
					</div>
				</div>
				
				<div class="form-group">
				    <div class="col-sm-offset-5 col-sm-10">
						<button class="btn btn-default" onclick="subbmitRegisterMember()">提交</button>
				    </div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
