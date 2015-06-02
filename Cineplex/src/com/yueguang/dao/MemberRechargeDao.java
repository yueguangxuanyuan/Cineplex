package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.MemberRecharge;

public interface MemberRechargeDao {
	public List<MemberRecharge> getMemberRechargeByMemberid(String memberid);

	public void deleteMemberRechargeByMemberid(String memberid);
	
	public void insertMemberRecharge(MemberRecharge memberRecharge);
}
