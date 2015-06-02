package com.yueguang.daoImpl;

import java.util.List;

import com.yueguang.dao.MemberLevelDao;
import com.yueguang.model.MemberLevel;

public class MemberLevelDaoImpl implements MemberLevelDao {
    BaseDaoImpl baseDaoImpl;
    
	public void setBaseDaoImpl(BaseDaoImpl baseDaoImpl) {
		this.baseDaoImpl = baseDaoImpl;
	}

	public List<MemberLevel> getAll() {
		return baseDaoImpl.getAllList(MemberLevel.class);
	}

	public float getDisCountByLevel(int level) {
		MemberLevel memberLevel =(MemberLevel)baseDaoImpl.load(MemberLevel.class, level);
		if(memberLevel == null){
			return 1;
		}else{
			return memberLevel.getDiscount();
		}
	}

	public MemberLevel getBylevel(int level) {
		// TODO Auto-generated method stub
		return (MemberLevel)baseDaoImpl.load(MemberLevel.class, level);
	}

}
