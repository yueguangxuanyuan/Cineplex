package com.yueguang.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yueguang.dao.MemberDao;
import com.yueguang.dao.MemberDuesDao;
import com.yueguang.dao.MemberRechargeDao;
import com.yueguang.model.Member;
import com.yueguang.model.MemberDues;
import com.yueguang.model.MemberRecharge;
import com.yueguang.util.ApplicationContextUtil;

public class PayManager {
	MemberDao memberDao;
	MemberRechargeDao memberRechargeDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setMemberRechargeDao(MemberRechargeDao memberRechargeDao) {
		this.memberRechargeDao = memberRechargeDao;
	}

	// 现金支付处理
	public boolean payinCash(String memberid, double amount) {
		Member member = memberDao.getMemberByMemberId(memberid);
		if (member != null) {
			MemberRecharge memberRecharge = new MemberRecharge();
			memberRecharge.setDetail("现金支付-" + amount);
			memberRecharge.setMember(member);
			memberRecharge.setTime(new Timestamp(new Date().getTime()));
			memberRechargeDao.insertMemberRecharge(memberRecharge);
			return true;
		} else {
			return false;
		}
	}

	// 卡支付处理
	public boolean payinCard(String memberid, double amount, String paypassword) {
		Member member = memberDao.getMemberByMemberId(memberid);
		if (member != null && member.getPaypassword().equals(paypassword)) {
			double balance = member.getBalance();
			if (balance >= amount) {
				member.setBalance(balance - amount);
				memberDao.updateinfo(member);
				MemberRecharge memberRecharge = new MemberRecharge();
				memberRecharge.setDetail("会员卡支付-" + amount);
				memberRecharge.setMember(member);
				memberRecharge.setTime(new Timestamp(new Date().getTime()));
				memberRechargeDao.insertMemberRecharge(memberRecharge);
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	// 第三方網上处理
	public boolean payinInternet(String memberid, double amount, String bankcard) {
		// do other things
		Member member = memberDao.getMemberByMemberId(memberid);
		MemberRecharge memberRecharge = new MemberRecharge();
		memberRecharge.setDetail("银行卡支付-" + amount);
		memberRecharge.setMember(member);
		memberRecharge.setTime(new Timestamp(new Date().getTime()));
		memberRechargeDao.insertMemberRecharge(memberRecharge);
		return true;
	}

	// 充值处理
	public boolean rechargeInInternet(String memberid, double amount,
			String bankcard) {
		Boolean needActivated = isNeedActivated(memberid);
		// 记录充值
		MemberRecharge memberRecharge = new MemberRecharge();
		memberRecharge.setTime(new Timestamp(new Date().getTime()));
		Member member = new Member();
		member.setMemberid(memberid);
		memberRecharge.setMember(member);
		memberRecharge.setDetail("网上充值-" + amount);
		memberRechargeDao.insertMemberRecharge(memberRecharge);
		// 完成充值
		member = memberDao.getMemberByMemberId(memberid);
		double balance = member.getBalance();
		balance += amount;
		member.setBalance(balance);
		if (needActivated) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			member.setValidity(new Timestamp(calendar.getTimeInMillis()));
		}
		memberDao.updateinfo(member);
		return true;
	}

	public boolean rechargeInCash(String memberid, double amount) {
		Boolean needActivated = isNeedActivated(memberid);
		// 记录充值
		MemberRecharge memberRecharge = new MemberRecharge();
		memberRecharge.setTime(new Timestamp(new Date().getTime()));
		Member member = new Member();
		member.setMemberid(memberid);
		memberRecharge.setMember(member);
		memberRecharge.setDetail("现金充值-" + amount);
		memberRechargeDao.insertMemberRecharge(memberRecharge);
		// 完成充值
		member = memberDao.getMemberByMemberId(memberid);
		double balance = member.getBalance();
		balance += amount;
		member.setBalance(balance);
		if (needActivated) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			member.setValidity(new Timestamp(calendar.getTimeInMillis()));
		}
		memberDao.updateinfo(member);
		return true;
	}

	// 判断是否激活账户
	private boolean isNeedActivated(String memberid) {
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

	// 缴纳会费
	public boolean payMemberDues(String memberid) {
		try {
			MemberDuesDao memberDuesDao = (MemberDuesDao) ApplicationContextUtil
					.getBean("memberDuesDaoImpl");
			MemberDues memberDues = new MemberDues();
			Member member = memberDao.getMemberByMemberId(memberid);
			memberDues.setMember(member);
			memberDues.setTime(new Timestamp(new Date().getTime()));
			memberDues.setDetail("缴纳会员等级-" + member.getMemberLevel().getLevel()
					+ "-会费: " + member.getMemberLevel().getCharge());
			memberDuesDao.insertMemberDues(memberDues);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
