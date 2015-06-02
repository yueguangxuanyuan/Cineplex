<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="container">
<head>
<base href="<%=basePath%>">

<title>用户登陆</title>

<meta http-equiv="Content-Type" content="charset=utf8">

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

<div class = "login-frame col-md-6 col-md-offset-3" style="padding-top:10%" >

	<!-- <img class="center" src="pic/logo.jpg" /> -->
	<h1 class="text-center color-black">Cineplex 登录</h1>
	
	
	
	<div class="row" style = "padding-top:5%">
	
		<div class="col-md-10 col-md-offset-1 row login-panel">
			<div style="color:red">${errorMessage}
			</div>
			<%
			  session.removeAttribute("errorMessage");
			%>
			<form role="form" class="center" method="post" action="Login/login.do">
				<div class="row">
					<label class="col-md-offset-1 col-md-10"><h3 class="color-white">用户登录</h3><label>
				</div>
				<div class="row">
					<div class="form-group col-md-10 col-md-offset-1">
							<input type="text" class="form-control" id="account" name="account"
								placeholder="请输入账号">
						</div>
				</div>
				<div class="row">
					<div class="form-group col-md-10 col-md-offset-1">
						<input type="password" class="form-control" id="password" name="password"
									placeholder="请输入密码">
					</div>			
				</div>
				
				<div class="row">
					<div class="form-group col-md-3 col-md-offset-8">
				
					<button class="btn btn-default " id="submit-btn" type="submit" 
							style="text-align:center;">登录</button>
					</div>
				</div>
			</form>
			
		</div>

	</div>
</div>
</body>


</html>

<script type="text/javascript">

	function center_( item ){
		var height = item.height();
		var width = item.width();
		alert(height+","+width);
		
		item.css("position","absolute");
		var screen_height = window.document.body.offsetHeight;
		var screen_width = window.document.body.offsetWidth;
		

		pos_x = (screen_width - width )/2;
		pos_y = (screen_height - height )/2;
		alert(pos_x+","+pos_y);

		pos_x = pos_x >0 ? pos_x : 0;
		pos_y = pos_y >0 ? pos_y : 0;
		
		
		
		item.css("left",pos_x+"px");
		item.css("top",pos_y+"px");
	}

	//center_($("#login-frame"));


</script>

