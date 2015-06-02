package com.yueguang.daoImpl;

import java.util.List;



import com.yueguang.dao.BaseDao;
import com.yueguang.dao.StaffDao;
import com.yueguang.model.Staff;

public class StaffDaoImpl implements StaffDao  {
    private  BaseDao baseDao;
    
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void save(Staff entity) {
		baseDao.save(entity);
	}

	public void deleteById(String id) {
		baseDao.delete(Staff.class, id);
	}

	public List<Staff> getStaff() {
		// TODO Auto-generated method stub
		return baseDao.getAllList(Staff.class);
	}


	public void updateByStaffid(Staff user) {
		// TODO Auto-generated method stub
		baseDao.update(user);
	}

	public Staff findbyStaffid(String id) {
		// TODO Auto-generated method stub
		return (Staff)baseDao.load(Staff.class, id);
	}

}
