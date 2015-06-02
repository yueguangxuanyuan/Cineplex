<%@page import="java.sql.Timestamp"%>
<%@page import="com.yueguang.model.VedioHall"%>
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
		List<VedioHall> vedioHalls = (List<VedioHall>) request
				.getAttribute("vedioHalls");
		Timestamp now = new Timestamp(new Date().getTime());
	%>
	<div class="ReSizeRow">

		<div class="row">
			<div class="col-md-2">
				<label>开始时间</label>
			</div>
			<div class="col-md-10">
				<div class="input-group date form_datetime col-md-5"
					data-date="1979-09-16T05:25:07Z"
					data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="starttime">
					<input class="form-control" size="16" type="text"
						value=<%=now%> readonly> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span> </span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span> </span>
				</div>
				<input type="hidden" id="starttime" value=<%=now%> /><br />
			</div>
		</div>
		<div class="row">
			<div class="col-md-2">
				<label>结束时间</label>
			</div>
			<div class="col-md-10">
				<div class="input-group date form_datetime col-md-5"
					data-date="1979-09-16T05:25:07Z"
					data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="endtime">
					<input class="form-control" size="16" type="text"
						value=<%=film.getOfftime()%> readonly> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span> </span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span> </span>
				</div>
				<input type="hidden" id="endtime" value=<%=film.getOfftime()%> /><br />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<label>电影编号</label>
			</div>
			<div class="col-xs-4">
				<input type="text" class="form-control" id="filmid"
					value=<%=film.getFilmid()%> disabled />
			</div>
			<div class="col-xs-2">
				<label>票价</label>
			</div>
			<div class="col-xs-4">
				<input type="text" class="form-control" id="price" value='0' />
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
					%>
					<option value="<%=vedioHall.getHallname()%>"><%=vedioHall.getHallname()%></option>
					<%
						}
					%>
				</select>
			</div>
		</div>
		<div class="row ">
			<div class="col-md-2 col-md-offset-2">
				<button class="btn btn-default" onclick="subbmitPlan()">提交审核</button>
			</div>
		</div>
	</div>
</body>
</html>
