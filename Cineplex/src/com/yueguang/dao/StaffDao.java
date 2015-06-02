package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.Staff;

public interface StaffDao {
	//创建服务员
	public void save(Staff entity);
	//根据id查找服务员
	public Staff findbyStaffid(String id);
    //根据id删除服务员
	public void deleteById(String id);
    //获取所有服务员
	public List<Staff> getStaff();
    //跟新服务员信息
	public void updateByStaffid(Staff user);
	
}
