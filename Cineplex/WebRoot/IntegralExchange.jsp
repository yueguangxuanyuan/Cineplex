<%@page import="com.yueguang.model.Member"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<%
		Member member = (Member) request.getAttribute("member");
		float integralRate = (Float) request.getAttribute("integralRate");
	%>
<div class="">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<h2 style="align:center">积分兑换</h2>
		</div>
	</div>
	<div class="row">
		<label class="col-xs-4">当前积分</label>
		<label class="col-xs-4"><%=member.getIntegral()%>
	</div>
	<div class="row">
		<label class="col-xs-4">当前积分兑换比</label> <label class="col-xs-4"><%=integralRate%>
	</div>
	<div class="row">
		<label class="col-xs-4">兑换的积分</label>
		<div class="col-xs-4">
			<input type="text" class="form-control" id="integral"
				name="integral" value="0">
		</div>
	</div>
	<div class="row">
		<div class=" col-md-2 col-md-offset-4">
			<button class="btn btn-default" style="width :100%;"
			onclick="subbmitIntegralExchange()">兑换</button>
		</div>
		
	</div>
</div>

