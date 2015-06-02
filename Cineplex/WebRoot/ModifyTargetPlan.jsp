<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.yueguang.model.VedioHall"%>
<%@page import="com.yueguang.model.Plan"%>
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
		Plan plan = (Plan) request.getAttribute("plan");
		List<VedioHall> vedioHalls = (List<VedioHall>) request
				.getAttribute("vedioHalls");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(plan.getStarttime());
		String start = sdf.format(plan.getStarttime());
		System.out.println(start);
		String end = sdf.format(plan.getEndtime());
	%>
	<div class="ReSizeRow">

		<div class="row">
			<div class="col-xs-4 col-md-offset-4">
				<h2>修改放映计划</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>放映计划编号</label>
			</div>
			<div class="col-xs-4">
				<input type="text" class="form-control" id="planid"
					value=<%=plan.getPlanid()%> disabled />
			</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label>开始时间</label>
			</div>
			<div>
				<div class="input-group date form_datetime col-md-5"
					data-date="1979-09-16T05:25:07Z"
					data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="starttime">
					<input class="form-control" size="16" type="text"
						value="<%=start%>" readonly> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span>
					</span> <span class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span>
					</span>
				</div>
				<input type="hidden" id="starttime" value="<%=start%>" /><br />
			</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label>结束时间</label>
			</div>
			<div>
				<div class="input-group date form_datetime col-md-5"
					data-date="1979-09-16 05:25:07"
					data-date-format="yyyy-mm-ddThh:ii:ss" data-link-field="endtime">
					<input class="form-control" size="16" type="text" value="<%=end%>"
						readonly> <span class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span> </span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span> </span>
				</div>
				<input type="hidden" id="endtime" value="<%=end%>" /><br />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>电影编号</label>
			</div>
			<div class="col-xs-4">
				<input type="text" class="form-control" id="filmid"
					value=<%=plan.getFilm().getFilmid()%> disabled />
			</div>
			<div class="col-xs-2">
				<label>票价</label>
			</div>
			<div class="col-xs-4">
				<input type="text" class="form-control" id="price"
					value=<%=plan.getPrice()%> />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>放映厅</label>
			</div>
			<div class="col-xs-4">
				<select id="hallname">
					<%
						for (VedioHall vedioHall : vedioHalls) {
							if (vedioHall.getHallname().equals(
									plan.getVedioHall().getHallname())) {
					%>

					<option value="<%=vedioHall.getHallname()%>" selected="selected"><%=vedioHall.getHallname()%></option>
					<%
						} else {
					%>
					<option value="<%=vedioHall.getHallname()%>"><%=vedioHall.getHallname()%></option>
					<%
						}
						}
					%>
				</select>
			</div>
		</div>
		<div class="row ">
			<div class="col-md-2 col-md-offset-2">
				<button class="btn btn-default" onclick="ModifyPlan() ">提交修改</button>
			</div>
		</div>
	</div>
</body>
</html>
