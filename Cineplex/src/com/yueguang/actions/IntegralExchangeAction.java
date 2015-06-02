package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Member;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class IntegralExchangeAction extends ActionSupport {

	private static final long serialVersionUID = -5580335596298983544L;

	private String memberid;
	private int amount;
    private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	// 返回积分兑换界面
	public String execute() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = memberManager.checkMemberState(memberid);
		if(status.equals("有效")){
			Member member = memberManager.getMemberByMemberid(memberid);
			float integralRate = memberManager.getIntegralRate();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("member", member);
			request.setAttribute("integralRate", integralRate);
			return "success";
		}else if(status.equals("暂停")){
			inputStream = new StringBufferInputStream("account is locked");
			return "fail";
		}else if(status.equals("停止")){
			inputStream = new StringBufferInputStream("account is forbidden");
			return "fail";
		}else{
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
		
	}

	// 进行积分兑换业务
	public String doIntegralExchange() {
		try {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			if(memberManager.exchangeIntegral(memberid, amount)){
				inputStream = new StringBufferInputStream("<h2>IntegralExchange Success</h2>");
				return "success";
			}else{
				inputStream = new StringBufferInputStream("<h2 style='color:red'>IntegralExchange fail</h2>");
				return "fail";
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("<h2 style='color:red'>System Error</h2>");
			return "fail";
		}
	}
}
