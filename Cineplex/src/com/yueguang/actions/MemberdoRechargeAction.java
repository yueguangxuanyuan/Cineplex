package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.dao.MemberRechargeDao;
import com.yueguang.model.Member;
import com.yueguang.model.MemberRecharge;
import com.yueguang.service.MemberManager;
import com.yueguang.service.PayManager;
import com.yueguang.util.ApplicationContextUtil;

public class MemberdoRechargeAction extends ActionSupport {
	private static final long serialVersionUID = -8866393653634472698L;
	private String memberid;
	private double amount;
	private String password;
	private String bankcard;
	private String paypassword;
	private InputStream inputStream;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	// 返回填写充值信息的界面
	public String execute() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = memberManager.checkMemberState(memberid);
		if (status.equals("有效")) {
			Member member = memberManager.getMemberByMemberid(memberid);
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("member", member);
			return "success";
		} else if (status.equals("暂停")) {
			inputStream = new StringBufferInputStream("account is locked");
			return "fail";
		} else if (status.equals("停止")) {
			inputStream = new StringBufferInputStream("account is forbidden");
			return "fail";
		} else {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}

	}

	// 对用户的充值进行判断
	public String doMemberRecharge() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		Member member = memberManager.getMemberByMemberid(memberid);
		PayManager payManager = (PayManager) ApplicationContextUtil
				.getBean("payManager");
		if (member.getPassword().equals(password)
				&& member.getPaypassword().equals(paypassword)) {
			if (payManager.rechargeInInternet(memberid, amount, bankcard)) {
				inputStream = new StringBufferInputStream(
						"<h2>Recharge success</h2>");
				return "success";
			} else {
				inputStream = new StringBufferInputStream(
						"<h2>Recharge fail</h2>");
			}
		}
		inputStream = new StringBufferInputStream(
				"<h2 style='color:red'>Wrong password</h2>");
		return "fail";
	}

	// 得到服务员给用户充值的界面
	public String getStaffRechargeMemberPage() {
		return "success";
	}

	// 进行服务员对member的充值
	public String staffRechargeMember() {
		try {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			Member member = memberManager.getMemberByMemberid(memberid);
			if (member == null) {
				inputStream = new StringBufferInputStream("member inexist");
				return "fail";
			}
			String status = memberManager.checkMemberState(memberid);
			if (status.equals("有效")||isNeedActivated(memberid)) {
				PayManager payManager = (PayManager) ApplicationContextUtil
						.getBean("payManager");
				payManager.rechargeInCash(memberid, amount);
				inputStream = new StringBufferInputStream("Recharge Success");
				return "success";
			} else if (status.equals("暂停")) {
				inputStream = new StringBufferInputStream("account is locked");
				return "fail";
			} else if (status.equals("停止")) {
				inputStream = new StringBufferInputStream(
						"account is forbidden");
				return "fail";
			} else {
				inputStream = new StringBufferInputStream("Error");
				return "fail";
			}

		} catch (Exception E) {
			inputStream = new StringBufferInputStream("Recharge Error");
			return "fail";
		}
	}

	// 判断是否激活账户
	private boolean isNeedActivated(String memberid) {
		MemberRechargeDao memberRechargeDao = (MemberRechargeDao) ApplicationContextUtil
				.getBean("memberRechargeDaoImpl");
		List<MemberRecharge> recharges = memberRechargeDao
				.getMemberRechargeByMemberid(memberid);
		for (MemberRecharge memberRecharge : recharges) {
			String[] detail = memberRecharge.getDetail().split("-");
			if (detail[0].equals("网上充值") || detail[0].equals("现金充值")
					|| detail[0].equals("积分兑换")) {
				double amount = Double.parseDouble(detail[1]);
				if (amount >= 200) {
					return false;
				}
			}
		}
		return true;

	}
}
