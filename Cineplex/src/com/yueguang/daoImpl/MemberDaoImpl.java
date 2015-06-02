package com.yueguang.daoImpl;

import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.MemberDao;
import com.yueguang.model.Member;

public class MemberDaoImpl implements MemberDao {
	BaseDaoImpl baseDao;

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}

	public Member getMemberByMemberId(String memberid) {
		return (Member)baseDao.load(Member.class, memberid);
	}

	public void insertMember(Member member) {
        baseDao.save(member);
	}

	public void updateinfo(Member member) {
       baseDao.update(member);
	}

	public List<String> getMemberIds() {
		String[]  attributes = {"memberid"};
 		return baseDao.find(Member.class,attributes) ;
	}

	public List<Member> getAllMembers() {
		return (List<Member>)baseDao.getAllList(Member.class);
	}

	public void deleteBymemberid(String memberid) {
		baseDao.delete(Member.class, memberid);
	}
}
