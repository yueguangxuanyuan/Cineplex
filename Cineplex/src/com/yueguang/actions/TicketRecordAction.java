package com.yueguang.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.yueguang.model.Ticket;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class TicketRecordAction extends ActionSupport{

	private static final long serialVersionUID = 5762046080011368325L;
    private String memberid;
    private int targetPage;
	
	public String getMemberid() {
		return memberid;
	}


	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}



	public int getTargetPage() {
		return targetPage;
	}



	public void setTargetPage(int targetPage) {
		this.targetPage = targetPage;
	}



	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		MemberManager memberManager = (MemberManager)ApplicationContextUtil.getBean("memberManager");
		List<Ticket> tickets=memberManager.getMemberTickets(memberid);
		request.setAttribute("tickets", tickets);
		request.setAttribute("ticketIndex", targetPage);
    	return "success";
    }
}
