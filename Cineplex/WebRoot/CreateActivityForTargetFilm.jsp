<%@page import="com.yueguang.model.Film"%>
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
	<%
		Film film = (Film) request.getAttribute("film");
		List<Integer> plans = (List<Integer>) request.getAttribute("plans");
	%>
	<div class="ReSizeRow">
		<div class="row">
			<div class="col-xs-2">
				<label>电影编号</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="filmid"
					value="<%=film.getFilmid()%>" disabled>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>问题内容</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="content" value="">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>回答选项</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="options" value=""
					placeholder="多个选项用-分割  限2-4个">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3">
				<label>选择绑定的放映计划</label>
			</div>
			<div class="col-xs-9">

				<div>
					<ul class="pagination">
						<%
							int totalPlan = plans.size();
							int defaultSeatPerRow = 10;
							int rowcount = (totalPlan + defaultSeatPerRow - 1)
									/ defaultSeatPerRow;
							for (int i = 0; i < rowcount; i++) {
								int base = i * defaultSeatPerRow;
								int ceiling = (i + 1) * defaultSeatPerRow;
								ceiling = ceiling > totalPlan ? totalPlan : ceiling;

								for (int j = base ; j < ceiling; j++) {
						%>
						<li><a onclick="bindPlan(this)"
							style="width:40px;text-align:center"><%=plans.get(j)%></a>
						</li>
						<%
							}
							}
						%>
					</ul>
				</div>

			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>需要绑定plan</label>
			</div>
			<div class="col-xs-4">
				<input class="form-control" id="bindPlans" value=""
					placeholder="至少选择一个" disabled>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2 col-md-offset-2">
				<button class="btn btn-default" onclick="createActivity()">提交</button>
			</div>
		</div>
	</div>
</body>
</html>
