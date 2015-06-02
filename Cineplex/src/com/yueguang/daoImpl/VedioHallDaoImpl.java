package com.yueguang.daoImpl;

import java.util.List;
import com.yueguang.dao.BaseDao;
import com.yueguang.dao.VedioHallDao;
import com.yueguang.model.VedioHall;

public class VedioHallDaoImpl implements VedioHallDao{
    BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<VedioHall> getVideoHalls() {
		return baseDao.getAllList(VedioHall.class);
	}

	public VedioHall getVedioHallByName(String hallname) {
		return (VedioHall) baseDao.load(VedioHall.class, hallname);
	}

}
