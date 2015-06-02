<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.Plan"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div>
	<div class="row">
		<div class="col-xs-3 col-md-offset-4">
			<h2>电影信息</h2>
		</div>
	</div>
	<%
		Plan plan = (Plan) request.getAttribute("plan");
		List<Integer> seats = (List<Integer>) request.getAttribute("seats");
	%>
	<div class="row">
		<div class="col-xs-2">
			<label>场次id</label>
		</div>
		<div class="col-xs-4">
			<p><%=plan.getPlanid()%></p>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<label>电影id</label>
		</div>
		<div class="col-xs-4">
			<p><%=plan.getFilm().getFilmid()%></p>
		</div>
		<div class="col-xs-2">
			<label>电影名称</label>
		</div>
		<div class="col-xs-4">
			<p><%=plan.getFilm().getName()%></p>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-4">
			<label>电影简介</label>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-10 col-md-offset-2">
			<p><%=plan.getFilm().getIntroduction()%></p>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<label>上市时间</label>
		</div>
		<div class="col-xs-4">
			<p><%=plan.getFilm().getOntime()%></p>
		</div>
		<div class="col-xs-2">
			<label>下架时间</label>
		</div>
		<div class="col-xs-4">
			<p><%=plan.getFilm().getOfftime()%></p>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3 col-md-offset-4">
			<h2 style="text-align:center">座位信息</h2>
		</div>
	</div>
	<div class="row">
	<%
		int totalSeat = plan.getVedioHall().getSeatcount();
		int defaultSeatPerRow = 10;
		int rowcount = (totalSeat + defaultSeatPerRow - 1)
				/ defaultSeatPerRow;
		for (int i = 0; i < rowcount; i++) {
			int base = i * defaultSeatPerRow;
			int ceiling = (i + 1) * defaultSeatPerRow;
			ceiling = ceiling > totalSeat ? totalSeat : ceiling;
	%>
		<div class="col-md-6 col-md-offset-3">
			<ul class="pagination">
				<%
					for (int j = base + 1; j <= ceiling; j++) {
							if (seats.contains(j)) {
				%>
				<li class="disabled"><a style="width:40px;text-align:center"><%=j%></a>
				</li>
				<%
					} else {
				%>
				<li><a onclick="addTicket('<%=plan.getPlanid()%>','<%=j%>',this)" style="width:40px;text-align:center"><%=j%></a></li>
				<%
					}
						}
				%>
			</ul>
		</div>
	<%
		}
	%>
	</div>
</div>

