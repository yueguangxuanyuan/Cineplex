package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.MemberDues;

public interface MemberDuesDao {
	public List<MemberDues> getMemberDuesByMemberid(String memberid);

	public void deleteMemberDuesByMemberid(String memberid);
	
	public void insertMemberDues(MemberDues memberDues);
}
