package com.yueguang.daoImpl;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.ManagerDao;
import com.yueguang.model.Manager;

public class ManagerDaoImpl implements ManagerDao {
    BaseDao  baseDao;
    
    
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	public Manager getManagerByMangerid(String managerid) {
		Manager  manager  = (Manager) baseDao.load(Manager.class, managerid);
		return manager;
	}

}
