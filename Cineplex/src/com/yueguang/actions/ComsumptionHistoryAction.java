package com.yueguang.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.yueguang.model.*;
import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class ComsumptionHistoryAction extends ActionSupport{

	private static final long serialVersionUID = -559422597453964116L;

	private String  memberid;

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	public String execute(){
		HttpServletRequest request =  ServletActionContext.getRequest();
		MemberManager memberManager = (MemberManager) ApplicationContextUtil.getBean("memberManager");
		List<Ticket> tickets=memberManager.getMemberTickets(memberid);
		List<MemberDues> memberDueses = memberManager.getMemberDuesByMemberid(memberid);
		List<MemberRecharge> recharges = memberManager.getMemberRechargesByMemberid(memberid);
		
		request.setAttribute("tickets", tickets);
		request.setAttribute("memberDueses", memberDueses);
		request.setAttribute("recharges",recharges );
		
		request.setAttribute("rechargeIndex", 1);
		request.setAttribute("duesIndex", 1);
		request.setAttribute("ticketIndex", 1);
		return "success";
	}
	
	
}
