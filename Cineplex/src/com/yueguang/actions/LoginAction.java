package com.yueguang.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Manager;
import com.yueguang.model.Member;
import com.yueguang.model.Staff;
import com.yueguang.service.ManagerManager;
import com.yueguang.service.MemberManager;
import com.yueguang.service.StaffManager;
import com.yueguang.util.ApplicationContextUtil;

public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = -4224983766729528319L;
	private String account;
	private String password; 
	private String logintype;
	
	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//假设
	//之前已经检查了所有值的合法性
	public String execute() {
        if(logintype.equals("member")){
        	MemberManager memberManager = (MemberManager)ApplicationContextUtil.getBean("memberManager");
        	if(memberManager.Login(account, password)){
        		HttpServletRequest request = ServletActionContext.getRequest();
        		Member member = memberManager.getMemberByMemberid(account);
                //进行检查缴纳操作
        		memberManager.payMemberDues(account);
        		request.setAttribute("member", member);
        		return "member";
        	}else{
        		this.addActionError("会员登陆失败");
                return "fail";                        		
        	}
        }else if(logintype.equals("staff")){
        	StaffManager staffManager =(StaffManager)ApplicationContextUtil.getBean("staffManager");
        	if(staffManager.Login(account, password)){
        		HttpServletRequest request = ServletActionContext.getRequest();
        		Staff staff = staffManager.getStaffByStaffid(account);
        		request.setAttribute("staff", staff);
        		return "staff";
        	}
        	this.addActionError("服务员登陆失败");
        	return "fail";
        }else if(logintype.equals("manager")){
        	ManagerManager managerManager=(ManagerManager)ApplicationContextUtil.getBean("managerManager");
        	if(managerManager.Login(account, password)){
        		HttpServletRequest request=ServletActionContext.getRequest();
        		Manager manager = managerManager.getManagerByManagerid(account);
        		request.setAttribute("manager", manager);
        		return "manager";
        	}else{
        		this.addActionError("经理登陆失败");
                return "fail";                        		
        	}
        }else{
        	this.addActionError("用户类型错误");
        	return "fail";
        }
		
	}
}
