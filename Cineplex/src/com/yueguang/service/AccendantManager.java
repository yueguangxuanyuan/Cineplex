package com.yueguang.service;

import com.yueguang.dao.BaseDao;
import com.yueguang.model.Accendant;
import com.yueguang.model.CEO;

public class AccendantManager {
	BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	//登陆
	public boolean Login(String account, String password) {
		Accendant accendant = (Accendant) baseDao
				.load(Accendant.class, account);
		if (accendant != null && accendant.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	//找回密码
	public String getPasswordByAccountandName(String account, String name) {
		Accendant accendant = (Accendant) baseDao
				.load(Accendant.class, account);
		if (accendant != null && accendant.getName().equals(name)) {
			return accendant.getPassword();
		}
		return null;
	};
}
