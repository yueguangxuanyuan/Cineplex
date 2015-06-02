package com.yueguang.service;

import com.yueguang.dao.BaseDao;
import com.yueguang.model.CEO;
import com.yueguang.model.Manager;
import com.yueguang.model.Member;

public class CEOManager {
	BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	//登陆
	public boolean Login(String account, String password) {
		CEO ceo = (CEO) baseDao.load(CEO.class, account);
		if (ceo != null && ceo.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
    //找回密码
	public String getPasswordByAccountandName(String account, String name) {
		CEO ceo = (CEO) baseDao.load(CEO.class, account);
		if (ceo != null && ceo.getName().equals(name)) {
			return ceo.getPassword();
		}
		return null;
	};
}
