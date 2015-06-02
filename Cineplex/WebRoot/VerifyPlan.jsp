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

<title>My JSP 'VerifyPlan.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
</head>

<body>
	<div class="ReSizeRow">
		<div class="row" id="verifyPlanDiv">
			<%
				int defaultSizePerPage = 5;
				List<Plan> plans = (List<Plan>) request.getAttribute("plans");
				int planPage = (plans.size() + defaultSizePerPage - 1)
						/ defaultSizePerPage;
				if (planPage == 0) {
			%>
			<h2>暂无可售票的电影</h2>
			<%
				} else {
			%>
			<table class="table table-striped">
				<tbody>
				<thead>
					<tr>
						<th>场次编号</th>
						<th>电影编号</th>
						<th>电影名称</th>
						<th>放映厅</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>票价</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<%
					int planIndex = (Integer) request.getAttribute("planIndex");
						int formerPage = planIndex - 1;
						formerPage = formerPage > 0 ? formerPage : 1;
						formerPage = formerPage <= planPage ? formerPage : planPage;
						int latterPage = planIndex + 1;
						latterPage = latterPage > planPage ? planPage : latterPage;
						Plan plan;
						int size = plans.size() > defaultSizePerPage * planIndex ? defaultSizePerPage
								* planIndex
								: plans.size();
						for (int i = defaultSizePerPage * (planIndex - 1); i < size; i++) {
							plan = plans.get(i);
							String approval = "planid=" + plan.getPlanid()
									+ "&verifyDecision=已通过";
							String disagree = "planid=" + plan.getPlanid()
									+ "&verifyDecision=修改中";
				%>

				<tr
					onclick="getCertainActionforDiv('MemberGetPlanInfo.action','planInfoDiv','planid=<%=plan.getPlanid()%>')">
					<td><%=plan.getPlanid()%></td>
					<td><%=plan.getFilm().getFilmid()%></td>
					<td><%=plan.getFilm().getName()%></td>
					<td><%=plan.getVedioHall().getHallname()%></td>
					<td><%=plan.getStarttime()%></td>
					<td><%=plan.getEndtime()%></td>
					<td><%=plan.getPrice()%></td>
					<td><button class="btn btn-default"
							onclick="getCertainActionforDiv('SubbmitVerifyPlanResult.action','mainDiv','<%=approval%>')">批准</button>
					</td>
					<td><button class="btn btn-default"
							onclick="getCertainActionforDiv('SubbmitVerifyPlanResult.action','mainDiv','<%=disagree%>')">打回</button>
					</td>
				</tr>

				<%
					}
				%>
				</tbody>

			</table>
			<ul class="pagination">
				<li><a
					onclick="getCertainPageforDiv('GetTargetVerifiablePlanPage.action','verifyPlanDiv', <%=formerPage%>)">&laquo;</a>
				</li>
				<%
					for (int i = 1; i <= planPage; i++) {
							if (i == planIndex) {
				%>
				<li class="active"><a
					onclick="getCertainPageforDiv('GetTargetVerifiablePlanPage.action','verifyPlanDiv', <%=i%>)"><%=i%></a>
				</li>
				<%
					} else {
				%>
				<li><a
					onclick="getCertainPageforDiv('GetTargetVerifiablePlanPage.action','verifyPlanDiv', <%=i%>)"><%=i%></a>
				</li>
				<%
					}
						}
				%>
				<li><a
					onclick="getCertainPageforDiv('GetTargetVerifiablePlanPage.action','verifyPlanDiv', <%=latterPage%>)">&raquo;</a>
				</li>
			</ul>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>
