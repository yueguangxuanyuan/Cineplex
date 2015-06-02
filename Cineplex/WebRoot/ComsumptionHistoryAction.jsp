<%@page import="com.yueguang.model.MemberDues"%>
<%@page import="com.yueguang.model.MemberRecharge"%>
<%@page import="com.yueguang.model.Ticket"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<%
	int defaultSizePerPage = 5;
%>
<div id="ticketDiv">
	<h2>购票记录</h2>

	<%
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
	<div style="padding:  0 ; background:white; border-radius:10px ">
	<table class="table table-striped" style=" ">
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
	</div>
	
	
	<ul class="pagination">
		<li>
			<a onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=formerPage%>)">&laquo;</a>
		</li>
		<%for (int i = 1; i <= ticketPage; i++) {
					if (i == ticketIndex) {%>

		<li class="active">
			<a onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=i%>)"><%=i%></a>
		</li>
		<%  }
		else 
		
		{%>	
		<li>
		<a onclick="getCertainPageforDiv('TicketRecordAction.action','ticketDiv', <%=i%>)"><%=i%></a>
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
</div>


<div id="rechargeDiv">
	<h2>充值记录</h2>
	<%
		List<MemberRecharge> recharges = (List<MemberRecharge>) request
				.getAttribute("recharges");
		int rechargePage = (recharges.size() + defaultSizePerPage - 1)
				/ defaultSizePerPage;
		if (rechargePage == 0) {
	%>
	<h2>暂无充值记录</h2>
	<%
		} else {
	%>
	<div style="padding:  0 ; background:white; border-radius:10px ">
	<table class="table table-striped" style="border-radius:10px; ">
		<tbody>
		<thead>
			<tr>
				<th>充值编号</th>
				<th>充值时间</th>
				<th>细节</th>
			</tr>
		</thead>
		<%
			int rechargeIndex = (Integer) request
						.getAttribute("rechargeIndex");
				int formerPage = rechargeIndex - 1;
				formerPage = formerPage > 0 ? formerPage : 1;
				int latterPage = rechargeIndex + 1;
				latterPage = latterPage > rechargePage ? rechargePage
						: latterPage;
				MemberRecharge memberRecharge;
				int rechargesize = recharges.size() > defaultSizePerPage
						* rechargeIndex ? defaultSizePerPage * rechargeIndex
						: recharges.size();
				for (int i = defaultSizePerPage * (rechargeIndex - 1); i < rechargesize; i++) {
					memberRecharge = recharges.get(i);
		%>

		<tr>
			<td><%=memberRecharge.getRechargeid()%></td>
			<td><%=memberRecharge.getTime()%></td>
			<td><%=memberRecharge.getDetail()%></td>
		</tr>



		<%
			}
		%>
		</tbody>

	</table>
	</div>
	<ul class="pagination">
		<li><a
			onclick="getCertainPageforDiv('RechargeRecordAction.action','rechargeDiv', <%=formerPage%>)">&laquo;</a>
		</li>
		<%
			for (int i = 1; i <= rechargePage; i++) {
					if (i == rechargeIndex) {
		%>
		<li class="active"><a
			onclick="getCertainPageforDiv('RechargeRecordAction.action','rechargeDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			} else {
		%>
		<li><a
			onclick="getCertainPageforDiv('RechargeRecordAction.action','rechargeDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			}
				}
		%>
		<li><a
			onclick="getCertainPageforDiv('RechargeRecordAction.action','rechargeDiv', <%=latterPage%>)">&raquo;</a>
		</li>
	</ul>
	<%
		}
	%>

</div>
<div id="duesDiv">
	<h2>会费缴纳记录</h2>

	<%
		List<MemberDues> memberDueses = (List<MemberDues>) request
				.getAttribute("memberDueses");
		int duesPage = (memberDueses.size() + defaultSizePerPage - 1)
				/ defaultSizePerPage;
		if (duesPage == 0) {
	%>
	<h2>暂无缴纳会费记录</h2>
	<%
		} else {
	%>
	<table class="table table-striped">
		<tbody>
		<thead>
			<tr>
				<th>缴费编号</th>
				<th>缴费时间</th>
				<th>细节</th>
			</tr>
		</thead>
		<%
			int duesIndex = (Integer) request.getAttribute("duesIndex");
				int formerPage = duesIndex - 1;
				formerPage = formerPage > 0 ? formerPage : 1;
				int latterPage = duesIndex + 1;
				latterPage = latterPage > duesPage ? duesPage : latterPage;
				MemberDues memberDues;
				int duessize = memberDueses.size() > defaultSizePerPage
						* duesIndex ? defaultSizePerPage * duesIndex
						: memberDueses.size();
				for (int i = defaultSizePerPage * (duesIndex - 1); i < memberDueses
						.size(); i++) {
					memberDues = memberDueses.get(i);
		%>

		<tr>
			<td><%=memberDues.getDuesid()%></td>
			<td><%=memberDues.getTime()%></td>
			<td><%=memberDues.getDetail()%></td>
		</tr>



		<%
			}
		%>
		</tbody>

	</table>
	<ul class="pagination">
		<li><a
			onclick="getCertainPageforDiv('DuesRecordAction.action','duesDiv', <%=formerPage%>)">&laquo;</a>
		</li>
		<%
			for (int i = 1; i <= duesPage; i++) {
					if (i == duesIndex) {
		%>
		<li class="active"><a
			onclick="getCertainPageforDiv('DuesRecordAction.action','duesDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			} else {
		%>
		<li><a
			onclick="getCertainPageforDiv('DuesRecordAction.action','duesDiv', <%=i%>)"><%=i%></a>
		</li>
		<%
			}
				}
		%>
		<li><a
			onclick="getCertainPageforDiv('DuesRecordAction.action','duesDiv', <%=latterPage%>)">&raquo;</a>
		</li>
	</ul>
	<%
		}
	%>

</div>

