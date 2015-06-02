<%@page import="com.yueguang.model.Film"%>
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
		Film film = (Film) request.getAttribute("film");
		List<Integer> questionids = (List<Integer>) request
				.getAttribute("questionids");
		if (questionids.size() == 0) {
	%>
	<div class="row">
		<div class="col-xs-2">
			<label>当前选择的问题</label>
		</div>
		<div class="col-xs-4">
			<input class="form-control" value="<%=film.getFilmid()%>" id="filmid"
				disabled>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3 col-md-offset-4">
			<h2>无未处理问题</h2>
		</div>
	</div>
	<%
		} else {
	%>
	<div class="row">
		<div class="col-xs-2">
			<label>当前选择的问题</label>
		</div>
		<div class="col-xs-4">
			<input class="form-control" value="<%=film.getFilmid()%>" id="filmid"
				disabled>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-3 col-md-offset-4">
			<h2 style="text-align:center">未处理问题列表</h2>
		</div>
	</div>
	<div class="row">
		<%
			int totalquestions = questionids.size();
				int defaultSeatPerRow = 10;
				int rowcount = (totalquestions + defaultSeatPerRow - 1)
						/ defaultSeatPerRow;
				for (int i = 0; i < rowcount; i++) {
					int base = i * defaultSeatPerRow;
					int ceiling = (i + 1) * defaultSeatPerRow;
					ceiling = ceiling > totalquestions ? totalquestions
							: ceiling;
		%>
		<div class="col-md-6 col-md-offset-3">
			<ul class="pagination">
				<%
					for (int j = base; j < ceiling; j++) {
								String sendInfo = "filmid=" + film.getFilmid()
										+ "&questionid=" + questionids.get(j);
				%>
				<li><a style="width:40px;text-align:center"
					onclick="getCertainActionforDiv('GetStatisticofAQuestiononAFilm.action','statisticofQuestiononAFilmDiv','<%=sendInfo%>')"><%=questionids.get(j)%></a>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		<%
			}
		%>
	</div>
	<%
		}
	%>
</body>
</html>
