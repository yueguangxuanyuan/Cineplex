<%@page import="com.yueguang.model.Member"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

			Member member = (Member) request.getAttribute("member");
%>

 <link href="./css/global.css" rel="stylesheet" > 
			<%if (member == null) {
%>
		<div id="userInfo">
			<div class="row">
				<h2>无用户信息</h2>
			</div>
		</div>
<%
	} else {
%>
		<div id="userInfo">
			<table class="table">
				<caption>用户个人信息</caption>
				<tbody>
					<tr>
						<td>id ：<%=member.getMemberid()%></td>
						<td>积分： <%=member.getIntegral()%></td>
					</tr>
					<tr>
						<td>姓名： <%=member.getName()%></td>
						<td>性别： <%=member.getSex()%></td>
					</tr>
					<tr>
						<td>生日： <%=member.getBirthday()%></td>
						<td>居住地： <%=member.getResidence()%></td>
					</tr>
					<tr>
						<td>会员等级： <%=member.getMemberLevel().getLevel()%></td>
						<td>会员到期日期： <%=member.getValidity()%></td>
					</tr>
					<tr>
						<td>卡余额： <%=member.getBalance()%></td>
					</tr>
	
				</tbody>
			</table>
		</div>
<%
	}
%>

