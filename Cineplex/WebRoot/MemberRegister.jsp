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
	<div class="row" style="border-left:solid #969696 2px;">
		<div class="col-xs-5 col-md-offset-3">
			<div style="text-align:center;font-size:20px">注册用户</div>
			<div class="row">
				<div class="col-xs-10">
					<label for="name">姓名</label> <input type="text"
						class="form-control" id="name" name="name" value="" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-10">
					<label for="password">密码</label> <input type="password"
						class="form-control" id="password" name="password" value="" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label for="bankcard">银行卡</label> <input type="text"
						class="form-control" id="bankcard" name="bankcard" value="" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label for="payPassword">支付密码</label> <input type="password"
						class="form-control" id="paypassword" name="paypassword" value="" />
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label for="dtp_input2" class="col-md-2 control-label">生日</label>
					<div class="input-group date form_date col-md-10" data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_birthday"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text" value=""
							readonly> <span class="input-group-addon"><span
							class="glyphicon glyphicon-remove"></span> </span> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span> </span>
					</div>
					<input type="hidden" id="dtp_birthday" name="birthday" value="" /><br />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<lable> 性别 </lable>
					<label class="checkbox-inline"> <input type="radio"
						name="cmisex" id="optionsMale" value="男" checked> 男 </label> <label
						class="checkbox-inline"> <input type="radio" name="cmisex"
						id="optionsFemale" value="女"> 女 </label>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label>城市</label> <select class="input-sm" id="city" name="city">
						<option>南京</option>
					</select> <label>区域</label> <select class="input-sm" id="area" name="area">
						<%
							String[] areas = { "玄武区", "鼓楼区", "建邺区", "白下区", "秦淮区", "下关区",
									"雨花台区", " 浦口区", "栖霞区", " 江宁区", "六合区" };
							for (int i = 0; i < areas.length; i++) {
						%>
						<option><%=areas[i]%></option>
						<%
							}
						%>
					</select>
				</div>
			</div>
			<div class="row" name="street">
				<div class="form-group col-xs-10">
					<label>街道/其他</label><input type="text" id="street" name="street"
						value="" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6 col-md-offset-4">
					<button class="btn btn-default " onclick="subbmitRegisterMember()">提交</button>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
