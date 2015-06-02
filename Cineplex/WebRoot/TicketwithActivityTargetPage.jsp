<%@page import="com.yueguang.model.Ticket"%>
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

<title>My JSP 'TicketwithActivityTargetPage.jsp' starting page</title>

<meta http-equiv="Content-Type" content="charset=utf8">

</head>

<body>
	<div class="row">
		<div class="col-xs-4 col-md-offset-4">
			<h2>可参加活动的票</h2>
		</div>
	</div>
	<%
		int defaultSizePerPage = 5;
		ArrayList<Ticket> ticketsWithQuestions = (ArrayList<Ticket>) request
				.getAttribute("ticketsWithQuestions");
		int Page = (ticketsWithQuestions.size() + defaultSizePerPage - 1)
				/ defaultSizePerPage;
		if (Page == 0) {
	%>
	<h2>无可参加的活动</h2>
	<%
		} else {
	%>
	<div class="row">
		<table class="table table-striped">
			<tbody>
			<thead>
				<tr>
					<th>票编号</th>
					<th>购买时间</th>
					<th>场次编号</th>
				</tr>
			</thead>
			<%
				int index = (Integer) request.getAttribute("index");
					if (index > Page) {
						index = Page;
					}
					int formerPage = index - 1;
					formerPage = formerPage > 0 ? formerPage : 1;
					formerPage = formerPage <= Page ? formerPage : Page;
					int latterPage = index + 1;
					latterPage = latterPage > Page ? Page : latterPage;
					Ticket ticket;
					int size = ticketsWithQuestions.size() > defaultSizePerPage
							* index ? defaultSizePerPage * index
							: ticketsWithQuestions.size();
					for (int i = defaultSizePerPage * (index - 1); i < size; i++) {
						ticket = ticketsWithQuestions.get(i);
			%>

			<tr>
				<td><%=ticket.getTicketid()%></td>
				<td><%=ticket.getBuytime()%></td>
				<td><%=ticket.getPlan().getPlanid()%></td>
			</tr>
			<%
				}
			%>
			</tbody>

		</table>
		<ul class="pagination">
			<li><a
				onclick="getCertainPageforDiv('GotoTicketwithActivityTargetPage','TicketwithActivityDiv', <%=formerPage%>)">&laquo;</a>
			</li>
			<%
				for (int i = 1; i <= Page; i++) {
						if (i == index) {
			%>
			<li class="active"><a
				onclick="getCertainPageforDiv('GotoTicketwithActivityTargetPage','TicketwithActivityDiv', <%=i%>)"><%=i%></a>
			</li>
			<%
				} else {
			%>
			<li><a
				onclick="getCertainPageforDiv('GotoTicketwithActivityTargetPage','TicketwithActivityDiv', <%=i%>)"><%=i%></a>
			</li>
			<%
				}
					}
			%>
			<li><a
				onclick="getCertainPageforDiv('GotoTicketwithActivityTargetPage','TicketwithActivityDiv', <%=latterPage%>)">&raquo;</a>
			</li>
		</ul>
		<%
			}
		%>
	</div>
</body>
</html>
