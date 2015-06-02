<%@page import="com.yueguang.model.MemberLevel"%>
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
		<%
			List<MemberLevel> memberLevels = (List<MemberLevel>) request
					.getAttribute("memberLevels");
		%>
		<div id="changeMemberLevelDiv">
			<div class="row">
				<div class="col-xs-4 col-md-offset-4">
					<h2>更改用户等级</h2>
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
					<label for="memberLevel">会员等级选择列表</label>
				</div>
				<div class="col-xs-4">
					 <select id="memberLevel" class="form-control">
						<%
							for (MemberLevel memberLevel : memberLevels) {
							String option = "会员等级： "+memberLevel.getLevel()+"-折扣："+memberLevel.getDiscount()+"-年费: "+memberLevel.getCharge();
						%>
						<option value=<%=memberLevel.getLevel()%>><%=option %></option>
						<%
							}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<button class="btn btn-default col-xs-2 col-md-offset-4" onclick="changeMemberLevel()">确认更改</button>
			</div>
		</div>
	</div>
</body>
</html>
