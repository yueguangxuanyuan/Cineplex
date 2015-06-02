package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.MemberReply;

public interface MemberReplyDao {
    public void insertMemberReply(MemberReply memberreply);
    
    public List<MemberReply>  getMemberReplysByQtPid(int qtpid);
    
    public void deleteByQtPId(int qtpid);
}
