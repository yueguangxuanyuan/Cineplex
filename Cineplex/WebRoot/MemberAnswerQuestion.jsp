<%@page import="com.yueguang.model.Ticket"%>
<%@page import="com.yueguang.model.QuestiontoPlan"%>
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

<title>My JSP 'MemberAnswerQuestion.jsp' starting page</title>

<meta http-equiv="Content-Type" content="charset=utf8">

</head>

<body>
	<div class="row">
		<div class="col-xs-4 col-md-offset-4">
			<h2>活动问题列表</h2>
		</div>
	</div>
	<div class="row">
		<%
			int defaultSizePerPage = 5;
			Ticket ticket = (Ticket) request.getAttribute("ticket");
			List<QuestiontoPlan> questiontoPlans = (List<QuestiontoPlan>) request
					.getAttribute("questiontoPlans");
			int Page = (questiontoPlans.size() + defaultSizePerPage - 1)
					/ defaultSizePerPage;
			if (Page == 0) {
		%>
		<h2>无可参加的活动</h2>
		<%
			} else {
		%>
		<table class="table table-striped">
			<tbody>
				<%
					int index = (Integer) request.getAttribute("targetPage");
						if (index > Page) {
							index = Page;
						}
						int formerPage = index - 1;
						formerPage = formerPage > 0 ? formerPage : 1;
						formerPage = formerPage <= Page ? formerPage : Page;
						int latterPage = index + 1;
						latterPage = latterPage > Page ? Page : latterPage;
						QuestiontoPlan questiontoPlan;
						int size = questiontoPlans.size() > defaultSizePerPage * index ? defaultSizePerPage
								* index
								: questiontoPlans.size();
						for (int i = defaultSizePerPage * (index - 1); i < size; i++) {
							questiontoPlan = questiontoPlans.get(i);
				%>

				<tr>
					<td><%=questiontoPlan.getQuestion().getQuestionid()%></td>
					<td><%=questiontoPlan.getQuestion().getContent()%></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<%
							String[] options = questiontoPlan.getQuestion()
											.getOptions().split("-");
									for (int optionid = 0; optionid < options.length; optionid++) {
						%> <label class="checkbox-inline"><input type="radio"
							name=<%=i - defaultSizePerPage * (index - 1) + 1%>
							value=<%=ticket.getTicketid() + "-"
								+ questiontoPlan.getQtpid() + "-"
								+ options[optionid]%>
							checked> <%=options[optionid]%></label> <%
 	}
 %>
					</td>
				</tr>
				<%
					}
				%>
			</tbody>

		</table>
		<ul class="pagination">
			<li><a
				onclick="getQuestionByTicketid(<%=ticket.getTicketid()%>,<%=formerPage%>)">&laquo;</a>
			</li>
			<%
				for (int i = 1; i <= Page; i++) {
						if (i == index) {
			%>
			<li class="active"><a
				onclick="getQuestionByTicketid(<%=ticket.getTicketid()%>,<%=i%>)"><%=i%></a>
			</li>
			<%
				} else {
			%>
			<li><a
				onclick="getQuestionByTicketid(<%=ticket.getTicketid()%>,<%=i%>)"><%=i%></a>
			</li>
			<%
				}
					}
			%>
			<li><a
				onclick="getQuestionByTicketid(<%=ticket.getTicketid()%>,<%=latterPage%>)">&raquo;</a>
			</li>
		</ul>
		<div class="row">
			<div class="col-xs-3 col-md-offset-4">
				<input type="hidden" id="questionsCount"
					value=<%=size - defaultSizePerPage * (index - 1)%> />
				<button class="btn btn-default"
					onclick="subbmitQuestions($('#questionsCount').val())">提交</button>
			</div>
		</div>
		<%
			}
		%>
	</div>

</body>
</html>
