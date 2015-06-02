package com.yueguang.dao;

import java.sql.Timestamp;

import com.yueguang.model.CinemaStatistic;

public interface CinemaStatisticDao {
   //插入记录
	public void insertCinemaStatistic(CinemaStatistic cinemaStatistic);
	
	//查找记录
	public CinemaStatistic findCinemaStatisticByTime(Timestamp time);
}
