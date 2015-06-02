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

<title>My JSP 'ModifiablePlan.jsp' starting page</title>

<meta http-equiv="Content-Type" content="charset=utf8">

</head>

<body>
	<div>
		<%
			int defaultSizePerPage = 5;
			List<Plan> plans = (List<Plan>) request.getAttribute("plans");
			int planIndex = (Integer) request.getAttribute("planIndex");
			int planPage = (plans.size() + defaultSizePerPage - 1)
					/ defaultSizePerPage;
			if (planPage == 0) {
		%>
		<h2>无可修改记录</h2>
		<%
			} else {
		%>
		<table class="table table-striped">
			<caption>可修改的记录</caption>
			<tbody>
			<thead>
				<tr>
					<th>放映计划编号</th>
					<th>放映电影编号</th>
					<th>放映电影介绍</th>
					<th>开场时间</th>
					<th>散场时间</th>
					<th>票价</th>
					<th>放映厅</th>
					<th></th>
				</tr>
			</thead>
			<%
				if (planIndex > planPage) {
						planIndex = planPage;
					}
					int formerPage = planIndex - 1;
					formerPage = formerPage > 0 ? formerPage : 1;
					int latterPage = planIndex + 1;
					latterPage = latterPage > planPage ? planPage : latterPage;
					Plan plan;
					int plansize = plans.size() > defaultSizePerPage * planIndex ? defaultSizePerPage
							* planIndex
							: plans.size();
					for (int i = defaultSizePerPage * (planIndex - 1); i < plansize; i++) {
						plan = plans.get(i);
			%>

			<tr onclick="getModifyTargetPlanPage(<%=plan.getPlanid()%>)">
				<td><%=plan.getPlanid()%></td>
				<td><%=plan.getFilm().getFilmid()%></td>
				<td><%=plan.getFilm().getIntroduction()%></td>
				<td><%=plan.getStarttime()%></td>
				<td><%=plan.getEndtime()%></td>
				<td><%=plan.getPrice()%></td>
				<td><%=plan.getVedioHall().getHallname()%></td>
				<td><button class="btn btn-default"
						onclick="deletePlan(<%=plan.getPlanid()%>)">删除</button>
				</td>
			</tr>



			<%
				}
			%>
			</tbody>

		</table>
		<ul class="pagination">
			<li><a
				onclick="getCertainPageforDiv('GetTargetModifiablePlanPage.action','modifiablePlanDiv', <%=formerPage%>)">&laquo;</a>
			</li>
			<%
				for (int i = 1; i <= planPage; i++) {
						if (i == planIndex) {
			%>
			<li class="active"><a
				onclick="getCertainPageforDiv('GetTargetModifiablePlanPage.action','modifiablePlanDiv', <%=i%>)"><%=i%></a>
			</li>
			<%
				} else {
			%>
			<li><a
				onclick="getCertainPageforDiv('GetTargetModifiablePlanPage.action','modifiablePlanDiv', <%=i%>)"><%=i%></a>
			</li>
			<%
				}
					}
			%>
			<li><a
				onclick="getCertainPageforDiv('GetTargetModifiablePlanPage.action','modifiablePlanDiv', <%=latterPage%>)">&raquo;</a>
			</li>
		</ul>
		<%
			}
		%>
	</div>
</body>
</html>
