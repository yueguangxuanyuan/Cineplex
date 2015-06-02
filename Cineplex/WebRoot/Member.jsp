<%@page import="com.yueguang.model.*"%>
<%@page import="com.yueguang.service.*"%>
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

<title>用户主页</title>

<meta http-equiv="Content-Type" content="charset=utf8">
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="./css/global.css" rel="stylesheet" > 


<script type="text/javascript" src="./jquery/jquery-2.1.3.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"charset="UTF-8"></script>
<script type="text/javascript" src="./js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
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
		if (targetAction == "ChangeMemberInfoAction.action") {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("mainDiv").innerHTML = xmlhttp.responseText;
					setBirthdayDateTimePicker();
				}
			};
		} else {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("mainDiv").innerHTML = xmlhttp.responseText;
				}
			}
		}
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("memberid=" + memberid);
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
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById(targetDiv).innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send(sendInfo);
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
	//响应分页组件为特定Div添加内容  实现分页
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
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("memberid=" + memberid + "&targetPage=" + targetPage);
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
	//提交修改memberInfo表单；
	function subbmitChangeMemberInfo() {
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
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", "DoChangeMemberInfo.action", true);
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
		var oldpassword = $('#oldpassword').val();
		var oldpaypassword = $('#oldpaypassword').val();
		xmlhttp.send("memberid=" + memberid + "&name=" + name + "&password="
				+ password + "&bankcard=" + bankcard + "&paypassword="
				+ paypassword + "&birthday=" + birthday + "&sex=" + sex
				+ "&city=" + city + "&area=" + area + "&street=" + street
				+ "&oldpassword=" + oldpassword + "&oldpaypassword="
				+ oldpaypassword);
	}
	//提交memberRecharge表单
	function subbmitMemberRecharge() {
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
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", "DoMemberRecharge.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var amount = $('#amount').val();
		var password = $('#password').val();
		var bankcard = $('#bankcard').val();
		var paypassword = $('#paypassword').val();
		xmlhttp.send("memberid=" + memberid + "&amount=" + amount
				+ "&password=" + password + "&bankcard=" + bankcard
				+ "&paypassword=" + paypassword);
	}

	//提交integralExchange表单
	function subbmitIntegralExchange() {
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
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", "DoIntegralExchange.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var amount = $('#integral').val();
		xmlhttp.send("memberid=" + memberid + "&amount=" + amount);
	}

	//提交取消会员资格表单
	function subbmitCancelMember() {
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
		var memberid = document.getElementById("memberid").innerHTML;
		xmlhttp.open("POST", "MemberCancelMember.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var password = $('#password').val();
		xmlhttp.send("memberid=" + memberid + "&password=" + password);
	}
	//用户选择票
	function addTicket(planid, seat,a) {
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
				document.getElementById("selectedTicketsDiv").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "GetMemberSelectedTickets.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var memberid = document.getElementById("memberid").innerHTML;
		var memberSeletedTicketCount = $('#memberSeletedTicketCount').val();
		var planids = $('#planids').val();
		var seats = $('#seats').val();
		xmlhttp.send("memberid=" + memberid + "&planid=" + planid + "&seat="
				+ seat + "&planids=" + planids + "&seats=" + seats
				+ "&memberSeletedTicketCount=" + memberSeletedTicketCount);
		$(a).parent().attr('class','disabled');
		$(a).attr('style','color:red');
	}

	//用户提交买票
	function memberSubbmitBuyTickets() {
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
		xmlhttp.open("POST", "MemberBuySelectedTickets.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		var memberid = document.getElementById("memberid").innerHTML;
		var memberSeletedTicketCount = $('#memberSeletedTicketCount').val();
		var planids = $('#planids').val();
		var seats = $('#seats').val();
		var bankcard = $('#bankcard').val();
		var paypassword = $('#paypassword').val();
		xmlhttp.send("memberid=" + memberid + "&planids=" + planids + "&seats="
				+ seats + "&memberSeletedTicketCount="
				+ memberSeletedTicketCount + "&bankcard=" + bankcard
				+ "&paypassword=" + paypassword);
	}
	//根据票id找到所有未回答的question
	function getQuestionByTicketid(ticketid, targetPage) {
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
				document.getElementById("QuestionDiv").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "GetQuestionByTicketid.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("ticketid=" + ticketid + "&targetPage=" + targetPage);
	}
	//提交活动的问题回答
	function subbmitQuestions(questionCount) {
		var answers = $('input[name="1"]:checked').val();
		for ( var i = 1; i < questionCount; i++) {
			var target = i + 1;
			target = "input[name=" + target + "]:checked";
			var per = $(target).val();
			answers = answers + "|" + per;
		}
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
				document.getElementById("QuestionDiv").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "MemberSubbmitAnswers.action", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("answers=" + answers);
	}
</script>

</head>

<body>
	<!-- container-fluid把整个屏幕12等分 -->
	<div class="container">
		<div class="row">
			<header id="header">
			<h1>用户主页</h1>
			<%
				String memberid = (String)session.getAttribute("userid");
			    MemberManager memberManager =(MemberManager) ApplicationContextUtil.getBean("memberManager");
				Member member = memberManager.getMemberByMemberid(memberid);
			%>
			<p class="text-right">
				Hello, <strong id='memberid'>${userid}</strong>
			</p>
			
			</header>
		</div>
		<div style="padding-top:5%">
			<div id="functionDiv" class="col-md-2 col-xs-6 menu_nav" role = "navigation">
				<!-- <li class="text-center" style="font-size:20px">功能列表</li> -->
				<ul >
					<li><a href="javascript:getCertainFunctionPage('MemberInfoAction.action')">会员基本信息</a></li>
					<li><a href="javascript:getCertainFunctionPage('ComsumptionHistoryAction.action')">查看消费记录</a></li>
					<li><a href="javascript:getCertainFunctionPage('ChangeMemberInfoAction.action')">修改账号信息</a></li>
					<li><a href="javascript:getCertainFunctionPage('MemberdoRechargeAction.action')">会员卡充值</a></li>
					<li><a href="javascript:getCertainFunctionPage('IntegralExchangeAction.action')">积分兑换</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetCancelMemberPage.action')">会员资格取消 </a></li>
					<li><a href="javascript:getCertainFunctionPage('GetMemberBuyTicketsPage.action');">购票</a></li>
					<li><a href="javascript:getCertainFunctionPage('GetAttendActivityPage.action');">参加活动</a></li>
				</ul>
			</div>

			<div class="col-md-10 col-xs-6 ">
				<div id="mainDiv" class="main-div">
					<div id="userInfo" >
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
				</div>
			</div>
		</div>
	</div>
</body>
</html>
