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

<title>My JSP 'HandleActivity.jsp' starting page</title>

<meta http-equiv="Content-Type" content="charset=utf8">

</head>

<body>
	<div class="ReSizeRow">
		<div class="row" id="unsolvedFilmsDiv">
			<%
				List<Film> films = (List<Film>) request.getAttribute("films");
			%>
			<div class="col-xs-8">
				<table class="table table-striped">
					<caption>存在未处理活动的电影列表</caption>
					<thead>
						<th>电影编号</th>
						<th>电影名称</th>
					</thead>
					<tbody>
						<%
							for (Film film : films) {
						%>
						<tr onclick="getCertainActionforDiv('GetQuestionsofAFilmPage.action','questionsofAFilmDiv','filmid=<%=film.getFilmid()%>')">
							<td><%=film.getFilmid()%></td>
							<td><%=film.getName()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<div class="Row" id="questionsofAFilmDiv"></div>
		<div class="Row" id="statisticofQuestiononAFilmDiv"></div>
	</div>
</body>
</html>
