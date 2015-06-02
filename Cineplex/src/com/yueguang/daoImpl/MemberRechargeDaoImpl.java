package com.yueguang.daoImpl;

import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.MemberRechargeDao;
import com.yueguang.model.MemberRecharge;

public class MemberRechargeDaoImpl implements MemberRechargeDao {
	BaseDaoImpl  baseDao;

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}

	public List<MemberRecharge> getMemberRechargeByMemberid(String memberid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("memberid", memberid);
		return (List<MemberRecharge>) baseDao.find(MemberRecharge.class, condition);
	}

	public void deleteMemberRechargeByMemberid(String memberid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("memberid", memberid);
		
        baseDao.delete(MemberRecharge.class, condition);
	}

	public void insertMemberRecharge(MemberRecharge memberRecharge) {
        baseDao.save(memberRecharge);		
	}

}
