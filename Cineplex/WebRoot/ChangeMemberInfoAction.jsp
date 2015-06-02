<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.yueguang.model.Member"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<div id="changeMemberInfoDiv" class="row">
	<%
		Member member = (Member) request.getAttribute("member");
		String residence = member.getResidence();
		String area = residence.split("-")[1];
		String street = residence.split("-")[2];
	%>
	<div class="col-md-6">
		<table class="table">
			<caption>用户个人信息</caption>
			<tbody>
				<tr>
					<td>姓名： <%=member.getName()%></td>
				</tr>
				<tr>
					<td>性别： <%=member.getSex()%></td>
				</tr>
				<tr>
					<td>生日： <%=member.getBirthday()%></td>
				</tr>
				<tr>
					<td>性别： <%=member.getSex()%></td>
				</tr>
				<tr>
					<td>绑定银行卡： <%=member.getBankcard()%></td>
				</tr>
				<tr>
					<td>住址： <%=member.getResidence()%></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="col-md-6 " style="border-left:solid #969696 2px;">
		<div>
			<div style="text-align:center;font-size:13px">修改用户信息</div>
			<div class="row">
				<div class="col-xs-10">
					<label for="name">姓名</label> <input type="text"
						class="form-control" id="name" name="name"
						value=<%=member.getName()%>>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-10">
					<label for="password">密码</label> <input type="password"
						class="form-control" id="password" name="password"
						value=<%=member.getPassword()%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label for="bankcard">银行卡</label> <input type="text"
						class="form-control" id="bankcard" name="bankcard"
						value=<%=member.getBankcard()%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label for="payPassword">支付密码</label> <input type="password"
						class="form-control" id="paypassword" name="paypassword"
						value=<%=member.getPaypassword()%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<div class ="col-md-2" style="padding:0 ; padding-top:10px ;font-size:13px">
						<label for="dtp_input2" class=" control-label "  style="">出生日期：</label>
					</div>
					<div class="input-group date form_date col-md-10 " data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_birthday"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text"
							value=<%=member.getBirthday()%> readonly> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-remove"></span> </span> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span> </span>
					</div>
					<input type="hidden" id="dtp_birthday" name="birthday" value=<%=member.getBirthday()%> /><br />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<lable> 性别 </lable>
					<%
						if (member.getSex().equals("男")) {
					%>
					<label class="checkbox-inline"> <input type="radio"
						name="cmisex" id="optionsMale" value="1" checked> 男 </label> <label
						class="checkbox-inline"> <input type="radio" name="cmisex"
						id="optionsFemale" value="2"> 女 </label>
					<%
						} else {
					%>
					<label class="checkbox-inline"> <input type="radio"
						name="cmisex" id="optionsMale" value="1"> 男 </label> <label
						class="checkbox-inline"> <input type="radio" name="sex"
						id="cmioptionsFemale" value="2" checked> 女 </label>
					<%
						}
					%>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-10">
					<label>城市</label> <select class="input-sm" id="city" name="city">
						<option>南京</option>
					</select> <label>区域</label> <select class="input-sm" id="area" name="area">
						<%
							String[] areas = { "玄武区", "鼓楼区", "建邺区", "白下区", "秦淮区", "下关区",
									"雨花台区", " 浦口区", "栖霞区", " 江宁区", "六合区" };
							for (int i = 0; i < areas.length; i++) {
								if (areas[i].equals(area)) {
						%>
						<option selected="selected"><%=areas[i]%></option>
						<%
							} else {
						%>
						<option><%=areas[i]%></option>
						<%
							}
							}
						%>
					</select>
				</div>
			</div>
			<div class="row" name="street">
				<div class="form-group col-xs-10">
					<label>街道/其他</label><input type="text" id="street" name="street"
						value=<%=street%> />
				</div>
			</div>
			<p>确认密码</p>
			<div class="row">
				<div class="form-group col-xs-5">
					<label>密码</label><input type="password" id="oldpassword"
						name="oldpassword" placeholder="原先的密码" />
				</div>
				<div class="form-group col-xs-5">
					<label>支付密码</label><input type="password" id="oldpaypassword"
						name="oldpaypassword" placeholder="原先的支付密码" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6 col-md-offset-3">
					<button onclick="subbmitChangeMemberInfo()" class="btn btn-default ">提交</button>
				</div>
			</div>
		</div>

	</div>
</div>

