package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.Member;

public interface MemberDao {
   public Member getMemberByMemberId(String  memberid);
   public void insertMember(Member member);
   public void updateinfo(Member  member);
   public List<String> getMemberIds();
   public List<Member> getAllMembers();
   public void deleteBymemberid(String  memberid);
}
