<%@page import="com.yueguang.model.Question"%>
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
	<%
		Question question = (Question) request.getAttribute("question");
		Map<String, Integer> questiontoStatistic = (Map<String, Integer>) request
				.getAttribute("questiontoStatistic");
	     int filmid =(Integer)request.getAttribute("filmid");
	%>
	<div class="row">
	   <div class="col-xs-3 col-md-offset-2">
	     <h2>处理问题</h2>
	   </div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<label>问题编号</label>
		</div>
		<div class="col-xs-4">
			<input class="form-control" id="questionid"
				value="<%=question.getQuestionid()%>" disabled="disabled">
		</div>
		<div class="col-xs-2">
			<label>电影编号</label>
		</div>
		<div class="col-xs-4">
			<input class="form-control" id="questionid"
				value="<%=filmid%>" disabled="disabled">
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<label>问题内容</label>
		</div>
		<div class="col-xs-8">
			<input class="form-control" id="content"
				value="<%=question.getContent()%>" disabled="disabled">
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6">
			<%
				Set<String> keys = questiontoStatistic.keySet();
				for (String key : keys) {
			%>
			<label class="checkbox-inline"> <input type="radio"
				name="questionoption" value="<%=key%>"><%=key%>--<%=questiontoStatistic.get(key)%>人选</label>
			<%
				}
			%>
		</div>
	</div>
	<div class="row">
	   <div class="col-xs-2">
			<label>奖励积分</label>
		</div>
		<div class="col-xs-4">
			<input class="form-control" id="bonus"
				value="0">
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2 col-md-offset-2">
			<button class="btn btn-default form-control" onclick="submitHandleActivity('<%=question.getQuestionid()%>',<%=filmid%>)">确定</button>
		</div>
	</div>
</body>
</html>
