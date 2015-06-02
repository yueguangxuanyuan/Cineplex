package com.yueguang.daoImpl;

import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.MemberReplyDao;
import com.yueguang.model.MemberReply;

public class MemberReplyDaoImpl implements MemberReplyDao {
	BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertMemberReply(MemberReply memberreply) {
        baseDao.save(memberreply);
	}

	public List<MemberReply> getMemberReplysByQtPid(int qtpid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("qtpid", String.valueOf(qtpid));
		return baseDao.find(MemberReply.class, condition);
	}

	public void deleteByQtPId(int qtpid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("qtpid", String.valueOf(qtpid));
	    baseDao.delete(MemberReply.class, condition);
	}

}
