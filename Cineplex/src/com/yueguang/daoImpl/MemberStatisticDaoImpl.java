package com.yueguang.daoImpl;

import java.sql.Timestamp;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.MemberStatitsticDao;
import com.yueguang.model.MemberStatistic;

public class MemberStatisticDaoImpl implements MemberStatitsticDao {
	BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertMemberStatistic(MemberStatistic memberStatistic) {
       baseDao.save(memberStatistic);
	}

	public MemberStatistic findMemberStatisticByTime(Timestamp time) {
		return (MemberStatistic) baseDao.load(MemberStatistic.class, time);
	}

}
