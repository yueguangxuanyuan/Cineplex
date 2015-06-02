package com.yueguang.daoImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.PlanDao;
import com.yueguang.model.Plan;
import com.yueguang.model.Ticket;

public class PlanDaoImpl implements PlanDao {
	BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertPlan(Plan plan) {
		baseDao.save(plan);
	}

	public void deletePlanById(int planid) {
		baseDao.delete(Plan.class, planid);
	}

	public void updatePlan(Plan plan) {
		baseDao.update(plan);
	}

	public List<Plan> getPlanByStaffId(String staffid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("staff.staffid", staffid);
		return baseDao.find(Plan.class, condition);
	}

	public Plan getPlanByPlanId(int planid) {
		return (Plan) baseDao.load(Plan.class, planid);
	}

	public List<Plan> getAllPlans() {
		return baseDao.getAllList(Plan.class);
	}

	public List<Plan> getAvailablePlansofCertainMonth(int year, int month) {
		List<Plan> plans = baseDao.getAllList(Plan.class);
		
		//筛选掉不是本月的放映计划
		Calendar calendar = Calendar.getInstance();
		for(int i = 0 ; i < plans.size();i++){
			Plan plan = plans.get(i);
			calendar.setTime(plan.getStarttime());
			if(calendar.get(Calendar.YEAR)!=year || calendar.get(Calendar.MONTH)!=month||!plan.getStatus().equals("已通过")){
				plans.remove(i);
				i--;
				continue;
			}
		}
		return plans;
		
	}

}
