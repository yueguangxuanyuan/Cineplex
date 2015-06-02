<%@page import="com.yueguang.model.*"%>
<%@page import="com.yueguang.dao.*"%>
<%@page import="com.yueguang.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="sx"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>服务员主页</title>

<meta http-equiv="Content-Type" content="charset=utf8">
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<link href="./css/global.css" rel="stylesheet">
<script type="text/javascript" src="./jquery/jquery-2.1.3.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="./js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script src="./js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
	//响应功能组件为mainDiv添加内容
	function getCertainFunctionPage(targetAction) {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (targetAction == "ChangeMemberInfoAction.action"
				|| targetAction == "GetRegisterMemberPage.action") {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("mainDiv").innerHTML = xmlhttp.responseText;
					setBirthdayDateTimePicker()
				}
			};
		} else {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("mainDiv").innerHTML = xmlhttp.responseText;
				}
			}

		}
		var staffid = document.getElementById("staffid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("staffid=" + staffid);
	}
	//为指定div 填充指定action的返回，自己传来传入值
	function getCertainActionforDiv(targetAction, targetDiv, sendInfo) {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (targetAction == "GetDoFilmPlanPage.action"
				|| targetAction == "GetModifyTargetPlanPage.action") {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById(targetDiv).innerHTML = xmlhttp.responseText;
					setDateTimePicker();

				}
			};
		} else {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById(targetDiv).innerHTML = xmlhttp.responseText;
				}
			};
		}

		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send(sendInfo);
	}
	//响应分页组件为特定Div添加内容  实现分页  (需要id为memberid的组件)
	function getCertainPageforDiv(targetAction, targetDiv, targetPage) {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById(targetDiv).innerHTML = xmlhttp.responseText;
			}
		};
		var staffid = document.getElementById("staffid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("staffid=" + staffid + "&targetPage=" + targetPage+"&memberid="+$('#memberid').val());
	}
	//设置 yyyy-MM-dd 的日期组件
	function setBirthdayDateTimePicker() {
		$('.form_date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
	}

	//设置 yyyy-MM-dd hh:mm:ss的时间组件
	function setDateTimePicker() {
		$('.form_datetime').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			forceParse : 0,
			showMeridian : 1
		});
	}
	//提交注册用户表单
	function subbmitRegisterMember() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("mainDiv").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "RegisterMember.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var name = $('#name').val();
		var password = $('#password').val();
		var bankcard = $('#bankcard').val();
		var paypassword = $('#paypassword').val();
		var birthday = $('#dtp_birthday').val();
		var sex = $('input[name="cmisex"]:checked').val();
		var city = $('#city').val();
		var area = $('#area').val();
		var street = $('#street').val();
		xmlhttp.send("name=" + name + "&password=" + password + "&bankcard="
				+ bankcard + "&paypassword=" + paypassword + "&birthday="
				+ birthday + "&sex=" + sex + "&city=" + city + "&area=" + area
				+ "&street=" + street);
	}

	//提交用户info搜索
	function searchMemberInfo() {
		//清空原有的新
		document.getElementById("memberInfoDiv").innerHTML = "";
		document.getElementById("ticketDiv").innerHTML = "";
		document.getElementById("rechargeDiv").innerHTML = "";
		document.getElementById("duesDiv").innerHTML = "";
		//给memberid组件设置值
		document.getElementById("memberid").value = $('#memberidinput').val();
		var sendInfo="memberid="+$('#memberid').val()+"&targetPage="+1;
		//开始查询
		getCertainActionforDiv("MemberInfoAction.action", 'memberInfoDiv',
				"memberid=" +$('#memberid').val());
		getCertainActionforDiv('TicketRecordAction.action', 'ticketDiv', sendInfo);
		getCertainActionforDiv('DuesRecordAction.action', 'duesDiv', sendInfo);
		getCertainActionforDiv('RechargeRecordAction.action', 'rechargeDiv', sendInfo);
	}
	//提交用户充值
	function subbmiRechargetMember() {
		var memberid = $('#memberid').val();
		var amount = $('#amount').val();
		var sendInfo = "memberid=" + memberid + "&amount=" + amount;
		getCertainActionforDiv("StaffRechargeMember.action",
				"rechargeMemberDiv", sendInfo);
	}

	//提交用户等级改变
	function changeMemberLevel() {
		var memberid = $('#memberid').val();
		var memberLevel = $('#memberLevel').val();
		var sendInfo = "memberid=" + memberid + "&memberLevel=" + memberLevel;
		getCertainActionforDiv("ChangeMemberLevel.action",
				"changeMemberLevelDiv", sendInfo);
	}

	//提交恢复会员资格
	function changeMemberLevel() {
		var memberid = $('#memberid').val();
		var sendInfo = "memberid=" + memberid;
		getCertainActionforDiv("RecoverMember.action", "recoverMemberDiv",
				sendInfo);
	}

	//提交计划
	function subbmitPlan() {
		var starttime = $('#starttime').val();
		var endtime = $('#endtime').val();
		var staffid = document.getElementById('staffid').innerHTML;
		var filmid = $('#filmid').val();
		var price = $('#price').val();
		var hallname = $('#hallname').val();
		var sendInfo = "filmid=" + filmid + "&staffid=" + staffid
				+ "&starttime=" + starttime + "&endtime=" + endtime + "&price="
				+ price + "&hallname=" + hallname;
		getCertainActionforDiv("SubbmitPlan.action", "doFilmPlanDiv", sendInfo);
	}

	//删除计划
	function deletePlan(planid) {
		var sendInfo = "planid=" + planid;
		getCertainActionforDiv("DeletePlan.action", "modifiablePlanDiv",
				sendInfo);
	}

	//生成修改计划的表单
	function getModifyTargetPlanPage(planid) {
		var sendInfo = "planid=" + planid;
		getCertainActionforDiv("GetModifyTargetPlanPage.action",
				"modifiablePlanDiv", sendInfo);
	}
	//修改计划
	function ModifyPlan() {
		var planid = $('#planid').val();
		var starttime = $('#starttime').val();
		var endtime = $('#endtime').val();
		var price = $('#price').val();
		var hallname = $('#hallname').val();
		price = price + 0.0;
		var sendInfo = "planid=" + planid + "&starttime=" + starttime
				+ "&endtime=" + endtime + "&price=" + price + "&hallname="
				+ hallname;
		getCertainActionforDiv("ModifyPlan.action", "modifiablePlanDiv",
				sendInfo);
	}
	//活动绑定 plan
	function bindPlan(a) {
		a.onclick = "return false";
		if ($('#bindPlans').val() == "") {
			$('#bindPlans').attr('value', a.innerHTML);
		} else {
			$('#bindPlans').attr('value',
					$('#bindPlans').val() + "-" + a.innerHTML);
		}
	}
	//创建活动
	function createActivity() {
		var filmid = $('#filmid').val();
		var content = $('#content').val();
		var options = $('#options').val();
		var bindPlans = $('#bindPlans').val();
		var sendInfo = "filmid=" + filmid + "&content=" + content + "&options="
				+ options + "&bindPlans=" + bindPlans;
		getCertainActionforDiv("StaffCreateActivity.action",
				"createActivityForTargetFilmDiv", sendInfo);
	}
	//用户选择票
	function addTicket(planid, seat,a) {
		var memberSeletedTicketCount = $('#memberSeletedTicketCount').val();
		var planids = $('#planids').val();
		var seats = $('#seats').val();
		var sendInfo = "planid=" + planid + "&seat=" + seat + "&planids="
				+ planids + "&seats=" + seats + "&memberSeletedTicketCount="
				+ memberSeletedTicketCount;
		getCertainActionforDiv("GetStaffSelectedTickets.action",
				"selectedTicketsDiv", sendInfo);
		$(a).parent().attr('class','disabled');
		$(a).attr('style','color:red');

	}
	function notmemberSubbmitBuyTickets() {
		var memberSeletedTicketCount = $('#memberSeletedTicketCount').val();
		var planids = $('#planids').val();
		var seats = $('#seats').val();
		var sendInfo ="planids=" + planids
				+ "&seats=" + seats + "&memberSeletedTicketCount="
				+ memberSeletedTicketCount;
		getCertainActionforDiv("PayByNotMember.action",
				"mainDiv", sendInfo);
	}
	function memberSubbmitBuyTickets() {
	    var memeberid = $('#memberid').val();
	    var payType=$('#payType').val();
	    var paypassword=$('#paypassword').val();
		var memberSeletedTicketCount = $('#memberSeletedTicketCount').val();
		var planids = $('#planids').val();
		var seats = $('#seats').val();
		var sendInfo ="planids=" + planids
				+ "&seats=" + seats + "&memberSeletedTicketCount="
				+ memberSeletedTicketCount+"&memberid="+memeberid+"&payType="+payType
				+"&paypassword="+paypassword;
		getCertainActionforDiv("PayByMember.action",
				"mainDiv", sendInfo);
				
	}
</script>

</head>

<body>
	<!-- container-fluid把整个屏幕12等分 -->
	<div class="container">
		<div class="row">
			<header id="header">
			<a href="LoginAction.action"><h1>服务员主页</h1></a>
			<%
				String staffid = (String)session.getAttribute("userid");
			    StaffDao staffDao=(StaffDao) ApplicationContextUtil.getBean("staffDaoImpl");
				Staff staff =staffDao.findbyStaffid(staffid) ;
			    
			%>
			<p class="text-right">
				Hello, <strong id='staffid'><%=staffid%></strong>
			</p>
			</header>
		</div>
		<div style="padding:5%">
			<div id="functionDiv" class="col-md-2 col-xs-6 menu_nav">
				<ul>
<!--  				<li><a href="javascript:getCertainFunctionPage('GetRegisterMemberPage.action')">注册会员</a></li> -->
					<li><a href="javascript:getCertainFunctionPage('GetSearchMemberInfoPage.action')">查询会员信息</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetStaffRechargeMemberPage.action')">会员卡现金充值</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetChangeMemberLevelPage.action')">会员卡资格改变</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetRecoverMemberPage')">会员资格恢复</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetCreatePlanPage.action')">制定放映计划</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetModifyPlanPage.action')">修改/删除放映计划</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetCreateActivityPage.action')">制定活动</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetStaffBuyTicketPage.action')">购票</a></li>
				</ul>
			</div>

			<div id="mainDiv" class="main-div col-md-10 col-xs-6">
				<div id="userInfo">
					<table class="table">
						<caption>服务员个人信息</caption>
						<tbody>
							<tr>
								<td>id ：<%=staff.getStaffid()%></td>
							</tr>
							<tr>
								<td>姓名： <%=staff.getName()%></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
