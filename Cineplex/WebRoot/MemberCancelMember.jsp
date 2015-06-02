<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<div>
	<div class="row">
		<div class="col-xs-4 col-md-offset-4">
			<h2>取消用户资格</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2 col-md-offset-1" style="padding:10px" >
			<label>确认密码</label>
		</div>
		<div class="col-xs-4 col-md-offset-1" style="padding:5px">
			<input type="password" class="form-control" id="password"
				name="password" />
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3 col-md-offset-4" style="padding:5px">
			<button class="btn btn-default form-control"
				onclick="subbmitCancelMember()">确认</button>
		</div>
	</div>
</div>

