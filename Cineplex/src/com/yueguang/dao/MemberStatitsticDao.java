package com.yueguang.dao;

import java.sql.Timestamp;

import com.yueguang.model.MemberStatistic;

public interface MemberStatitsticDao {
	// 插入记录
	public void insertMemberStatistic(MemberStatistic memberStatistic);
	
	//查找记录
	public MemberStatistic findMemberStatisticByTime(Timestamp time);
}
