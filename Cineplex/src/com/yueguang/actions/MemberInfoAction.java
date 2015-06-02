package com.yueguang.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.dao.MemberDao;
import com.yueguang.model.Member;
import com.yueguang.util.ApplicationContextUtil;

public class MemberInfoAction extends ActionSupport{

	private static final long serialVersionUID = 2554117211913544179L;

	private String memberid;

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	
	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		MemberDao memberDao = (MemberDao) ApplicationContextUtil.getBean("memberDaoImpl");
		Member member = memberDao.getMemberByMemberId(memberid);
		request.setAttribute("member", member);
		return "success";
	}
	
}
