<%@page import="com.yueguang.util.ApplicationContextUtil"%>
<%@page import="com.yueguang.model.*"%>
<%@page import="com.yueguang.dao.*"%>
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

<title>经理主页</title>

<meta http-equiv="Content-Type" content="charset=utf8">
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript" src="./jquery/jquery-1.8.3.min.js"
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
		var managerid = document.getElementById("managerid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("managerid=" + managerid);
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
		var managerid = document.getElementById("managerid").innerHTML;
		xmlhttp.open("POST", targetAction, true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		xmlhttp.send("managerid=" + managerid + "&targetPage=" + targetPage
				+ "&memberid=" + $('#memberid').val());
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

	function submitHandleActivity(questionid, filmid) {
		var selected = $('input[name="questionoption"]:checked').val();
		var bonus = $("#bonus").val();
		var sendInfo = "questionid=" + questionid + "&filmid=" + filmid
				+ "&selected=" + selected + "&bonus=" + bonus;
		getCertainActionforDiv('HandleActivity.action',
				'statisticofQuestiononAFilmDiv', sendInfo)
	}
</script>
<style>
.ReSizeRow>div {
	margin: 5px 0;
}
</style>
</head>

<body>
	<!-- container-fluid把整个屏幕12等分 -->
	<div class="container">
		<div class="row">
			<header id="header"
				style="background-color:  #dedef8;box-shadow: 
         inset 1px -1px 1px #444, inset -1px 1px 1px #444;height:120px">
			<h1>经理主页</h1>
			<%
				String managerid = (String)session.getAttribute("userid");
		//ManagerDao managerDao = (ManagerDao) ApplicationContextUtil.getBean("managerDaoImpl");
			    
			%>
			<p class="text-right">
				Hello, <strong id='managerid'><%=managerid%></strong>
			</p>
			</header>
		</div>
		<div class="row">
			<div id="functionDiv" class="col-md-2">
				<ul class="navbar navbar-default" role="navigation">
					<li class="text-center" style="font-size:20px">功能列表</li>
					<li><button type="button" class="btn btn-default btn-block"
							onclick="getCertainFunctionPage('GetVerifyPlanPage.action')">审批放映计划</button>
					</li>
					<li><button type="button" class="btn btn-default btn-block"
							onclick="getCertainFunctionPage('GetHandleActivityPage.action')">处理活动统计</button>
					</li>
					<li><button type="button" class="btn btn-default btn-block"
							onclick="getCertainFunctionPage('GetStatisticPage.action')">查看统计信息</button>
					</li>
				</ul>
			</div>

			<div id="mainDiv" class="col-xs-10"
				style="background-color: #dedef8;box-shadow: 
         inset 1px -1px 1px #444, inset -1px 1px 1px #444;height:600px;overflow:auto">
				<div id="userInfo">
					<table class="table">
						<caption>管理员个人信息</caption>
						<tbody>
							<tr>
								<td>id ：<%=managerid%></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
