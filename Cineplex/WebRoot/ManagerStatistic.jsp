<%@page import="com.yueguang.model.CinemaStatistic"%>
<%@page import="com.yueguang.model.MemberStatistic"%>
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

<meta http-equiv="Content-Type" content="charset=utf8">

</head>

<body>
	<div class="ReSizeRow">
		<div class="row" id="memberStatisticDiv">
			<div class="row">
				<div class="col-xs-4 col-md-offset-4">
					<h2>会员统计信息</h2>
				</div>
			</div>
			<%
			      MemberStatistic memberStatistic=(MemberStatistic) request.getAttribute("memberStatistic");
			    %>
			<div class="row">
			    <div class="col-xs-4">
			    <label>统计月份</label>
			    </div>
			    <div class="col-xs-5">
			       <input type="text" class="form-control" disabled value="<%=memberStatistic.getTime()%>">
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>年龄统计</label>
			    </div>
			    <div class="col-xs-5">
			       <input type="text" class="form-control" disabled value="<%=memberStatistic.getAgestatistic()%>">
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>性别统计</label>
			    </div>
			    <div class="col-xs-5">
			       <input type="text" class="form-control" disabled value="<%=memberStatistic.getSexstatistic()%>">
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>居住地统计</label>
			    </div>
			    <div class="col-xs-5">
			       <textarea class="form-control" disabled><%=memberStatistic.getResidencestatistic()%></textarea>
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>会员卡有效程度统计</label>
			    </div>
			    <div class="col-xs-5">
			       <input type="text" class="form-control" disabled value="<%=memberStatistic.getValiditystatistic()%>">
			    </div>
			</div>
		</div>
		<div class="row" id="filmStatisticDiv">
			<div class="row">
				<div class="col-xs-4 col-md-offset-4">
					<h2>电影统计信息</h2>
				</div>
			</div>
			<%
			   CinemaStatistic cinemaStatistic =(CinemaStatistic) request.getAttribute("cinemaStatistic");
			 %>
			 <div class="row">
			    <div class="col-xs-4">
			    <label>统计月份</label>
			    </div>
			    <div class="col-xs-5">
			       <input type="text" class="form-control" disabled value="<%=cinemaStatistic.getTime()%>">
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>人数统计</label>
			    </div>
			    <div class="col-xs-5">
			       <textarea class="form-control" disabled><%=cinemaStatistic.getPeoplecount()%></textarea>
			    </div>
			</div>
			<div class="row">
			    <div class="col-xs-4">
			    <label>买票支付方式</label>
			    </div>
			    <div class="col-xs-5">
			       <textarea class="form-control" disabled><%=cinemaStatistic.getBuyticketstatistic()%></textarea>
			    </div>
			</div>
		</div>
	</div>
</body>
</html>
