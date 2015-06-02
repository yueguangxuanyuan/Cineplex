package com.yueguang.service;

import com.yueguang.daoImpl.StaffDaoImpl;
import com.yueguang.model.Staff;

public class StaffManager {
	private StaffDaoImpl staffDaoImpl;

	public void setStaffDaoImpl(StaffDaoImpl staffDaoImpl) {
		this.staffDaoImpl = staffDaoImpl;
	}

	// 服务员用户登陆
	public boolean Login(String staffid, String password) {
		Staff staff = staffDaoImpl.findbyStaffid(staffid);
		if (staff == null) {
			return false;
		}
		return password.equals(staff.getPassword());
	}

	// 根据 服务员id得到服务员信息
	public Staff getStaffByStaffid(String staffid) {
		return staffDaoImpl.findbyStaffid(staffid);
	}

	//找回密码
	public String getPasswordByAccountandName(String account, String name) {
		Staff staff = staffDaoImpl.findbyStaffid(account);
		if (staff != null && staff.getName().equals(name)) {
			return staff.getPassword();
		}
		return null;
	}

}
