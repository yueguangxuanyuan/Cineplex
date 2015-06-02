<%@page import="com.yueguang.model.Member"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yueguang.model.Plan"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="" >
	<div class="row">
		<div class="col-xs-3 col-md-offset-5">
			<h2>售票</h2>
		</div>
	</div>
	<div class="row">
		<div id="AvailableFilmsDiv">
			<%
				int defaultSizePerPage = 5;
				Plan[] plans = (Plan[]) request.getAttribute("plans");
				Map<Integer, Integer> soldTicketsCount = (Map<Integer, Integer>) request
						.getAttribute("soldTicketsCount");
				int planPage = (plans.length + defaultSizePerPage - 1)
						/ defaultSizePerPage;
				if (planPage == 0) {
			%>
			<h2>暂无可售票的电影</h2>
			<%
				} else {
			%>
			<table class="table table-striped">
				<tbody>
				<thead>
					<tr>
						<th>场次编号</th>
						<th>电影编号</th>
						<th>电影名称</th>
						<th>放映厅</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>票价</th>
						<th>已售票数</th>
						<th>总票数</th>
					</tr>
				</thead>
				<%
					int planIndex = (Integer) request.getAttribute("planIndex");
						int formerPage = planIndex - 1;
						formerPage = formerPage > 0 ? formerPage : 1;
						formerPage = formerPage <= planPage ? formerPage : planPage;
						int latterPage = planIndex + 1;
						latterPage = latterPage > planPage ? planPage : latterPage;
						Plan plan;
						int size = plans.length > defaultSizePerPage * planIndex ? defaultSizePerPage
								* planIndex
								: plans.length;
						for (int i = defaultSizePerPage * (planIndex - 1); i < size; i++) {
							plan = plans[i];
				%>

				<tr
					onclick="getCertainActionforDiv('MemberGetPlanInfo.action','planInfoDiv','planid=<%=plan.getPlanid()%>')">
					<td><%=plan.getPlanid()%></td>
					<td><%=plan.getFilm().getFilmid()%></td>
					<td><%=plan.getFilm().getName()%></td>
					<td><%=plan.getVedioHall().getHallname()%></td>
					<td><%=plan.getStarttime()%></td>
					<td><%=plan.getEndtime()%></td>
					<td><%=plan.getPrice()%></td>
					<td><%=soldTicketsCount.get(plan.getPlanid())%></td>
					<td><%=plan.getVedioHall().getSeatcount()%></td>
				</tr>



				<%
					}
				%>
				</tbody>

			</table>
			<ul class="pagination">
				<li><a
					onclick="getCertainPageforDiv('GetAvailableFilms.action','AvailableFilmsDiv', <%=formerPage%>)">&laquo;</a>
				</li>
				<%
					for (int i = 1; i <= planPage; i++) {
							if (i == planIndex) {
				%>
				<li class="active"><a
					onclick="getCertainPageforDiv('GetAvailableFilms.action','AvailableFilmsDiv', <%=i%>)"><%=i%></a>
				</li>
				<%
					} else {
				%>
				<li><a
					onclick="getCertainPageforDiv('GetAvailableFilms.action','AvailableFilmsDiv', <%=i%>)"><%=i%></a>
				</li>
				<%
					}
						}
				%>
				<li><a
					onclick="getCertainPageforDiv('GetAvailableFilms.action','AvailableFilmsDiv', <%=latterPage%>)">&raquo;</a>
				</li>
			</ul>
			<%
				}
			%>
		</div>
	</div>
	<div class="row">
		<!--查看电影场次细节  电影信息 剩余的座位-->
		<div id="planInfoDiv">
			<h2 class="disabled">未选择电影信息</h2>
		</div>
	</div>
	<div class="row">
		<!--用户选择的票-->
		<% 
		  Member member = (Member) request.getAttribute("member");
		%>
		<div id="selectedTicketsDiv">
			<div class="row">
				<h2>用户已选择的票</h2>
			</div>
			<div class="row">
				<p>暂无选票</p>
			</div>
			<div class="row">
				<div class="col-xs-2">
					<label>需要支付的金额</label>
				</div>
				<div class="col-xs-4">
					<p>0.0</p>
				</div>
				<div class="col-xs-2">
					<label>会员卡内余额</label>
				</div>
				<div class="col-xs-4">
					<p><%=member.getBalance()%></p>
				</div>
				<input type="hidden" id="memberSeletedTicketCount" value="0" /> <input
					type="hidden" id="planids" value="" /> <input type="hidden"
					id="seats" value="" />
			</div>
			<div class="row">
				<div class="col-xs-3">
					<label>支付用的银行卡</label>
				</div>
				<div class="col-xs-4">
					<input type="text" id="bankcard" class="form-control"
						placeholder="不填默认用会员卡支付" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-3">
					<label>支付密码</label>
				</div>
				<div class="col-xs-4">
					<input type="password" id="paypassword" class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-3 col-md-offset-4">
					<button class="button button-default form-control" disabled>确认购买</button>
				</div>
			</div>
		</div>
	</div>
</div>

