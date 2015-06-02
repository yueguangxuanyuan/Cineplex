package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.Plan;
import com.yueguang.model.Ticket;

public interface PlanDao {
	public void insertPlan(Plan plan);

	public void deletePlanById(int planid);

	public void updatePlan(Plan plan);

	public List<Plan> getPlanByStaffId(String staffid);
	
	public Plan  getPlanByPlanId(int planid);
	
	//返回所有的电影计划
	public List<Plan> getAllPlans();
	
	//返回特定月已经通过的放映计划
	public List<Plan> getAvailablePlansofCertainMonth(int year,int month);
}
