package com.yueguang.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;





import org.apache.commons.lang3.RandomStringUtils;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.MemberDao;
import com.yueguang.dao.MemberLevelDao;
import com.yueguang.dao.MemberRechargeDao;
import com.yueguang.dao.MemberDuesDao;
import com.yueguang.dao.TicketDao;
import com.yueguang.model.Member;
import com.yueguang.model.MemberDues;
import com.yueguang.model.MemberLevel;
import com.yueguang.model.MemberRecharge;
import com.yueguang.model.Ticket;
import com.yueguang.util.ApplicationContextUtil;

public class MemberManager {
	MemberDao memberDaoImpl;
	MemberRechargeDao memberRechargeDao;
	MemberLevelDao memberLevelDao;
	MemberDuesDao memberDuesDao;
	TicketDao ticketDao;

	public void setIntegralRate(float integralRate) {
		this.integralRate = integralRate;
	}

	float integralRate; // 积分兑换比例

	public float getIntegralRate() {
		return integralRate;
	}

	public void setMemberDaoImpl(MemberDao memberDaoImpl) {
		this.memberDaoImpl = memberDaoImpl;
	}

	public void setMemberRechargeDao(MemberRechargeDao memberRechargeDao) {
		this.memberRechargeDao = memberRechargeDao;
	}

	public void setMemberLevelDao(MemberLevelDao memberLevelDao) {
		this.memberLevelDao = memberLevelDao;
	}

	public void setMemberDuesDao(MemberDuesDao memberDuesDao) {
		this.memberDuesDao = memberDuesDao;
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	// 用户登陆
	public boolean Login(String memberid, String password) {
		Member member = memberDaoImpl.getMemberByMemberId(memberid);
		if (member != null && member.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	
	public Member getMemberByMemberid(String memberid){
    	return memberDaoImpl.getMemberByMemberId(memberid);
    }
	
	// 检查账户状态
	public String checkMemberState(String memberid) {
		Member member = memberDaoImpl.getMemberByMemberId(memberid);
		if(member == null){
			return "不存在";
		}
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Timestamp validity = member.getValidity();
		if (timestamp.compareTo(validity) <= 0) {
			return "有效";
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			timestamp = new Timestamp(calendar.getTimeInMillis());
			if (validity.after(timestamp)) {
				return "暂停";
			} else {
				return "停止";
			}
		}
	}

	// 更新用户信息
	public boolean updateMemberInfo(Member member) {
		try {
			memberDaoImpl.updateinfo(member);
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	
	//自动生成7位ID
	public String generatorId() {
		List<String> listids = memberDaoImpl.getMemberIds();
		Set<String> ids = new HashSet<String>(listids);
		while (true) {
			String rsu ="m"+RandomStringUtils.randomAlphanumeric(6);
			if (ids.add(rsu)) {
				return rsu;
			}

		}
	}
	// 实现用户的注册功能 这里 会自动生成一个memberid
	public String register(Member member) {
		String memberId = generatorId();
		member.setMemberid(memberId);
		memberDaoImpl.insertMember(member);
		return memberId;
	}

	// 使用memberid查找用户信息
	public Member findMember(String memberid) {
		return memberDaoImpl.getMemberByMemberId(memberid);
	}

	// 获取全部的用户信息
	public List<Member> findAllMembers() {
		return memberDaoImpl.getAllMembers();
	}

	// 获取缴费信息
	public List<MemberRecharge> getMemberRechargesByMemberid(String memberid) {
		return memberRechargeDao.getMemberRechargeByMemberid(memberid);
	}

	// 获取用户缴费信息
	public List<MemberDues> getMemberDuesByMemberid(String memberid) {
		return memberDuesDao.getMemberDuesByMemberid(memberid);
	}

	// 获取用户的购票信息
	public List<Ticket> getMemberTickets(String memberid) {
		return ticketDao.getTicketsByMemberid(memberid);
	}

	// 会员等级修改
	public boolean ModifyMemberLevel(String memberid, int level) {
		try {
			// 这里按理应该改成原子事务 支持回滚操作
			Member member = memberDaoImpl.getMemberByMemberId(memberid);
			MemberLevelDao memberLevelDao = (MemberLevelDao) ApplicationContextUtil
					.getBean("memberLevelDaoImpl");
			member.setMemberLevel(memberLevelDao.getBylevel(level));
			memberDaoImpl.updateinfo(member);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 会员资格恢复
	public boolean recoverymember(String memberid) {
		try {
			Member member = memberDaoImpl.getMemberByMemberId(memberid);
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, 1);
			member.setValidity(new Timestamp(calendar.getTimeInMillis()));
			memberDaoImpl.updateinfo(member);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	// 给用户赠送积分（活动的后续）
	public void presentIntegral(List<Member> members, int integral) {
		for (Member member : members) {
			member.setIntegral(member.getIntegral() + integral);
			memberDaoImpl.updateinfo(member);
		}
	}

	// 用户积分兑换
	public boolean exchangeIntegral(String memberid, int amount) {
		try {
			Member member = memberDaoImpl.getMemberByMemberId(memberid);
			double balance = member.getBalance();
			int integral = member.getIntegral();
			if(amount > integral){
				return false;
			}
			integral = integral - amount;
			balance = balance + amount * integralRate;
			member.setIntegral(integral);
			member.setBalance(balance);
			MemberRecharge memberRecharge = new MemberRecharge();
			memberRecharge.setMember(member);
			//记录充值
			memberRecharge.setTime(new Timestamp(new Date().getTime()));
			memberRecharge.setDetail("积分兑换-"+amount * integralRate);
			memberRechargeDao.insertMemberRecharge(memberRecharge);
			//正式充值
			memberDaoImpl.updateinfo(member);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 取消会员资格
	public boolean cancelMemberIdentity(String memberid) {
		try {
			Member member = memberDaoImpl.getMemberByMemberId(memberid);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -2);
            member.setValidity(new Timestamp(calendar.getTimeInMillis()));
            memberDaoImpl.updateinfo(member);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//缴纳会费操作
	public void payMemberDues(String memberid){
		String status = checkMemberState(memberid);
		if(status.equals("有效")){
			return;
		}else if(status.equals("暂停")){
		     PayManager payManager = (PayManager)ApplicationContextUtil.getBean("payManager");
		     if(payManager.payMemberDues(memberid)){
		    	 Member member =memberDaoImpl.getMemberByMemberId(memberid);
		    	 Calendar calendar=Calendar.getInstance();
		    	 calendar.add(Calendar.YEAR, 1);
		    	 member.setValidity(new Timestamp(calendar.getTimeInMillis()));
		    	 memberDaoImpl.updateinfo(member);
		     }
		     return;
		}else if(status.equals("停止")){
		    return;
		}else{
		}
	}
	//获取所有的用户等级
   public List<MemberLevel> getMemberLevels(){
	   return memberLevelDao.getAll();
   }
   
   //找回账号
   public String getMemberIdByBankandName(String bankcard,String name){
	   BaseDao baseDao= (BaseDao) ApplicationContextUtil.getBean("baseDao");
	   HashMap<String,String> condition = new HashMap();
	   condition.put("bankcard", bankcard);
	   condition.put("name", name);
	   List<Member> members = baseDao.find(Member.class, condition);
	   if(members != null && members.size() > 0){
		   return members.get(0).getMemberid();
	   }else{
		   return null;
	   }
   }
   
	// 找回密码
   public String getPasswordByAccountandName(String account,String name){
	   Member member = memberDaoImpl.getMemberByMemberId(account);
	   if(member!=null&&member.getName().equals(name)){
		   return member.getPassword();
	   }
	   return null;
   };
}
