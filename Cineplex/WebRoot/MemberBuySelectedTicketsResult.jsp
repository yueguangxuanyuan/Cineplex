<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MemberBuySelectedTicketsResult.jsp' starting page</title>
    
	<meta http-equiv="Content-Type" content="charset=utf8">

  </head>
  
  <body>
     <div class="row">
       <div class="col-xs-3 col-xs-offset-4">
          <h2>支付成功</h2>
        </div>
     </div>
  </body>
</html>
