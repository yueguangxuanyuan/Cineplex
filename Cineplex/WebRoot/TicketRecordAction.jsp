<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.Ticket"%>
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
	<h2>购票记录</h2>

	<%
		int defaultSizePerPage = 5;
		List<Ticket> tickets = (List<Ticket>) request
				.getAttribute("tickets");
		int ticketPage = (tickets.size() + defaultSizePerPage - 1)
				/ defaultSizePerPage;
		if (ticketPage == 0) {
	%>
	<h2>暂无购票记录</h2>
	<%
		} else {
	%>
	<table class="table table-striped">
		<tbody>
		<thead>
			<tr>
				<th>电影票编号</th>
				<th>购票时间</th>
				<th>电影名称</th>
				<th>开场时间</th>
				<th>结束时间</th>
				<th>放映厅</th>
				<th>座位号</th>
				<th>付款方式</th>
			</tr>
		</thead>
		<%
			int ticketIndex = (Integer) request.getAttribute("ticketIndex");
				if (ticketIndex > ticketPage) {
					ticketIndex = ticketPage;
				}
				int formerPage = ticketIndex - 1;
				formerPage = formerPage > 0 ? formerPage : 1;
				int latterPage = ticketIndex + 1;
				latterPage = latterPage > ticketPage ? ticketPage : latterPage;
				Ticket ticket;
				int ticketsize = tickets.size() > defaultSizePerPage
						* ticketIndex ? defaultSizePerPage * ticketIndex
						: tickets.size();
				for (int i = defaultSizePerPage * (ticketIndex - 1); i < ticketsize; i++) {
					ticket = tickets.get(i);
		%>

		<tr>
			<td><%=ticket.getTicketid()%></td>
			<td><%=ticket.getBuytime()%></td>
			<td><%=ticket.getPlan().getFilm().getName()%></td>
			<td><%=ticket.getPlan().getStarttime()%></td>
			<td><%=ticket.getPlan().getEndtime()%></td>
			<td><%=ticket.getPlan().getVedioHall().getHallname()%></td>
			<td><%=ticket.getSeat()%></td>
			<td><%=ticket.getPayType()%></td>
		</tr>
		<%
			}
		%>
		</tbody>

	</table>
	<ul class="pagination">
		<li><a
			onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=formerPage%>)">&laquo;</a>
		</li>
		<%
			for (int i = 1; i <= ticketPage; i++) {
					if (i == ticketIndex) {
		%>

		<li class="active"><a
			onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			} else {
		%>
		<li><a
			onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			}
				}
		%>
		<li><a
			onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=latterPage%>)">&raquo;</a>
		</li>
	</ul>
	<%
		}
	%>

</body>
</html>
