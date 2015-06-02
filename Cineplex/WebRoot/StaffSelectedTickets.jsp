<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.yueguang.model.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="charset-utf8">

</head>
<body>
	<!--用户选择的票-->
	<%
		Ticket[] ticketArray = (Ticket[]) request
				.getAttribute("ticketArray");
		Double price = (Double) request.getAttribute("price");
		String warning = (String) request.getAttribute("warning");
		String planids = "";
		String seats = "";
	%>
	<div class="row">
		<h2>用户已选择的票</h2>
	</div>
	<div class="row">
		<%
			if (ticketArray == null || ticketArray.length == 0) {
		%>
		<p>暂无选票</p>
		<%
			} else {
		%>
		<table class="table table-striped">
			<tbody>
			<thead>
				<tr>
					<th>场次编号</th>
					<th>座位</th>
					<th>票价</th>
				</tr>
			</thead>
			<%
				for (int i = 0; i < ticketArray.length; i++) {
			%>
			<tr>
				<td><%=ticketArray[i].getPlan().getPlanid()%></td>
				<td><%=ticketArray[i].getSeat()%></td>
				<td><%=ticketArray[i].getPlan().getPrice()%></td>
			</tr>
			<%
				if (!planids.equals("")) {
							planids += "-";
							seats += "-";
						}
						planids += ticketArray[i].getPlan().getPlanid();
						seats += ticketArray[i].getSeat();

					}

				}
			%>
			</tbody>
		</table>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<label>需要支付的金额</label>
		</div>
		<div class="col-xs-4">
			<p><%=price%></p>
		</div>
		<input type="hidden" id="memberSeletedTicketCount"
			value=<%=ticketArray.length%> /> <input type="hidden" id="planids"
			value=<%=planids%> /> <input type="hidden" id="seats"
			value=<%=seats%> />
	</div>
	<%
		if (warning != null && !warning.equals("")) {
	%>
	<div class="row">
		<div class="col-xs-6 col-xs-offset-3">
			<p class="text-warning"><%=warning%>
			</p>
		</div>
	</div>
	<%
		}
	%>
	<div class="row">
		<div class="col-xs-3 col-md-offset-1">
			<%
				if (ticketArray != null && ticketArray.length > 0) {
			%>

			<button class="button button-default form-control" onclick="notmemberSubbmitBuyTickets()">非会员支付</button>
			<%
				} else {
			%>
			<button class="button button-default form-control" disabled>非会员支付</button>
			<%
				}
			%>
		</div>
		<div class="col-xs-3 col-md-offset-1">
		<%
				if (ticketArray != null && ticketArray.length > 0) {
			%>

			<button class="button button-default form-control" onclick="getCertainActionforDiv('PayByMember.jsp', 'payDiv', '')">会员支付</button>
			<%
				} else {
			%>
			<button class="button button-default form-control" disabled>会员支付</button>
			<%
				}
			%>
			
		</div>
	</div>
</body>
</html>
