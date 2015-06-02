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
	<div class="ReSizeRow">
		<div id="onShelfFilmDiv">
			<div class="row">
				<%
					int defaultSizePerPage = 5;
					List<Film> films = (List<Film>) request.getAttribute("films");
					int filmPage = (films.size() + defaultSizePerPage - 1)
							/ defaultSizePerPage;
					if (filmPage == 0) {
				%>
				<h2>无可做活动的电影</h2>
				<%
					} else {
				%>
				<table class="table table-striped">
					<caption>可做活动的电影</caption>
					<tbody>
					<thead>
						<tr>
							<th>电影编号</th>
							<th>电影名称</th>
							<th>电影介绍</th>
							<th>上架时间</th>
							<th>下架时间</th>
						</tr>
					</thead>
					<%
						int filmIndex = (Integer) request.getAttribute("filmIndex");
							if (filmIndex > filmPage) {
								filmIndex = filmPage;
							}
							int formerPage = filmIndex - 1;
							formerPage = formerPage > 0 ? formerPage : 1;

							int latterPage = filmIndex + 1;
							latterPage = latterPage > filmPage ? filmPage : latterPage;
							Film film;
							int filmsize = films.size() > defaultSizePerPage * filmIndex ? defaultSizePerPage
									* filmIndex
									: films.size();
							for (int i = defaultSizePerPage * (filmIndex - 1); i < filmsize; i++) {
								film = films.get(i);
								String sendInfo = "filmid=" + film.getFilmid();
					%>

					<tr
						onclick="getCertainActionforDiv('GetCreateActivityForTargetFilmPage.action','createActivityForTargetFilmDiv','<%=sendInfo%>')">
						<td><%=film.getFilmid()%></td>
						<td><%=film.getName()%></td>
						<td><%=film.getIntroduction()%></td>
						<td><%=film.getOntime()%></td>
						<td><%=film.getOfftime()%></td>
					</tr>



					<%
						}
					%>
					</tbody>

				</table>
				<ul class="pagination">
					<li><a
						onclick="getCertainPageforDiv('GetTargetOnShelfFilmPageForActivity.action','onShelfFilmDiv', <%=formerPage%>)">&laquo;</a>
					</li>
					<%
						for (int i = 1; i <= filmPage; i++) {
								if (i == filmIndex) {
					%>
					<li class="active"><a
						onclick="getCertainPageforDiv('GetTargetOnShelfFilmPageForActivity.action','onShelfFilmDiv', <%=i%>)"><%=i%></a>
					</li>
					<%
						} else {
					%>
					<li><a
						onclick="getCertainPageforDiv('GetTargetOnShelfFilmPageForActivity.action','onShelfFilmDiv', <%=i%>)"><%=i%></a>
					</li>
					<%
						}
							}
					%>
					<li><a
						onclick="getCertainPageforDiv('GetTargetOnShelfFilmPageForActivity.action','onShelfFilmDiv', <%=latterPage%>)">&raquo;</a>
					</li>
				</ul>
				<%
					}
				%>
			</div>
		</div>
		<div id="createActivityForTargetFilmDiv"></div>
	</div>
</body>
</html>
