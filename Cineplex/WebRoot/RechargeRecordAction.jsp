<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.MemberRecharge"%>
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
		int defaultSizePerPage = 5;
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
	<table class="table table-striped">
		<caption>充值记录</caption>
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
				if (rechargeIndex > rechargePage) {
					rechargeIndex = rechargePage;
				}
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
</body>
</html>
