package com.yueguang.daoImpl;

import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.MemberDuesDao;
import com.yueguang.model.MemberDues;

public class MemberDuesDaoImpl implements MemberDuesDao {
	BaseDaoImpl  baseDao;

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}

	public List<MemberDues> getMemberDuesByMemberid(String memberid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("memberid", memberid);
		return (List<MemberDues>) baseDao.find(MemberDues.class, condition);
	}

	public void deleteMemberDuesByMemberid(String memberid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("memberid", memberid);
        baseDao.delete(MemberDues.class, condition);
		
	}

	public void insertMemberDues(MemberDues memberDues) {
		baseDao.save(memberDues);	
		
	}

}
