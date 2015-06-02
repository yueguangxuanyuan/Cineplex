<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.MemberDues"%>
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
		<caption>会费缴纳记录</caption>
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
				if (duesIndex > duesPage) {
					duesIndex = duesPage;
				}
				int formerPage = duesIndex - 1;
				formerPage = formerPage > 0 ? formerPage : 1;
				int latterPage = duesIndex + 1;
				latterPage = latterPage > duesPage ? duesPage : latterPage;
				MemberDues memberDues;
				int duessize = memberDueses.size() > defaultSizePerPage
						* duesIndex ? defaultSizePerPage * duesIndex
						: memberDueses.size();
				for (int i = defaultSizePerPage * (duesIndex - 1); i < duessize; i++) {
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
</body>
</html>