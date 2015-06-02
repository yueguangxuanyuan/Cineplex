package com.yueguang.daoImpl;

import java.sql.Timestamp;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.CinemaStatisticDao;
import com.yueguang.model.CinemaStatistic;

public class CinemaStatisticDaoImpl implements CinemaStatisticDao {
	BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertCinemaStatistic(CinemaStatistic cinemaStatistic) {
		baseDao.save(cinemaStatistic);
	}

	public CinemaStatistic findCinemaStatisticByTime(Timestamp time) {
		// TODO Auto-generated method stub
		return (CinemaStatistic) baseDao.load(CinemaStatistic.class,time);
	}

}
