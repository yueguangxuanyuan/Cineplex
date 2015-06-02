package com.yueguang.service;

import com.yueguang.dao.ManagerDao;
import com.yueguang.model.Manager;

public class ManagerManager {
	ManagerDao managerDao;

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	// 登陆
	public boolean Login(String account, String password) {
		Manager manager = managerDao.getManagerByMangerid(account);
		if (manager != null && manager.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	// 待删除
	public Manager getManagerByManagerid(String managerid) {
		return managerDao.getManagerByMangerid(managerid);
	}

	// 找回密码
	public String getPasswordByAccountandName(String account, String name) {
		Manager manager = managerDao.getManagerByMangerid(account);
		if (manager != null && manager.getName().equals(name)) {
			return manager.getPassword();
		}
		return null;
	}
	
}
