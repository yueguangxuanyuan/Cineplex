package com.yueguang.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yueguang.dao.FilmDao;
import com.yueguang.dao.PlanDao;
import com.yueguang.model.Film;
import com.yueguang.model.Plan;

public class PlanManager {
	private PlanDao planDao;

	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}

	// 根据id 查询plan 但只有 处于可修改状态的可以返回
	public Plan getModifiedPlanById(int planid) {
		Plan plan = planDao.getPlanByPlanId(planid);
		if (plan != null && plan.getStatus().equals("修改中")) {
			return plan;
		} else {
			return null;
		}
	}

	// 根据staffid 返回所有的可以修改的plan（主要的 状态是修改中 并且电影还没下架）
	public List<Plan> getModifiablePlansByStaffid(String staffid) {
		List<Plan> plans = planDao.getPlanByStaffId(staffid);
		if (plans != null) {
			for (int i = 0; i < plans.size(); i++) {
				Plan plan = plans.get(i);
				// 筛选出允许修改的
				if (!plan.getStatus().equals("修改中")) {
					plans.remove(i);
					i--;
					continue;
				} else {
					// 筛选掉电影下架的
					Timestamp timestamp = new Timestamp(new Date().getTime());
                    if(timestamp.after(plan.getFilm().getOfftime())){
                    	plans.remove(i);
    					i--;
    					continue;
                    }
				}
			}
		}
		
		return plans;
	}

	// 提交plan
	public boolean summitPlan(Plan plan) {
		if (plan != null) {
			planDao.insertPlan(plan);
			return true;
		} else {
			return false;
		}
	}
	
	//修改plan
	public boolean modifyPlan(Plan plan) {
		if (plan != null) {
			planDao.updatePlan(plan);
			return true;
		} else {
			return false;
		}
	}

	// 根据id查找Plan
	public Plan getPlanById(int planid) {
		return planDao.getPlanByPlanId(planid);
	}

	// 删除放映计划
	public boolean deletePlan(int planid) {
		try {
			planDao.deletePlanById(planid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 返回可以买票的放映计划 (电影未开始 并且放映计划通过审核)
	public List<Plan> getAvailableFilmsForBuyTickets() {
		try {
			List<Plan> plans = planDao.getAllPlans();
			if (plans != null) {
				Timestamp now = new Timestamp(new Date().getTime());
				Plan tempPlan;
				for (int i = 0; i < plans.size(); i++) {
					tempPlan = plans.get(i);
					if ((!tempPlan.getStatus().equals("已通过"))
							|| tempPlan.getStarttime().compareTo(now) < 0) {
						plans.remove(i);
						i--;
					}
				}
				return plans;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	// 获取待审核的所有放映计划
	public List<Plan> getPlanforJudging() {
		try {
			List<Plan> plans = planDao.getAllPlans();
			if (plans != null) {
				Plan tempPlan;
				for (int i = 0; i < plans.size(); i++) {
					tempPlan = plans.get(i);
					if (!tempPlan.getStatus().endsWith("审核中")) {
						plans.remove(i);
						i--;
					}
				}
				return plans;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// 批准放映计划
	public boolean approvePlan(int planid) {
		Plan plan = planDao.getPlanByPlanId(planid);
		if (plan != null && plan.getStatus().equals("审核中")) {
			plan.setStatus("已通过");
			planDao.updatePlan(plan);
			return true;
		} else {
			return false;
		}
	}

	// 打回放映计划
	public boolean refusePlan(int planid) {
		Plan plan = planDao.getPlanByPlanId(planid);
		if (plan != null && plan.getStatus().equals("审核中")) {
			plan.setStatus("修改中");
			planDao.updatePlan(plan);
			return true;
		} else {
			return false;
		}
	}

	// 根据filmid 返回所有的已通过放映计划id
	public List<Integer> getPlansByFilmid(int filmid) {
		try {
			List<Plan> plans = planDao.getAllPlans();
			if (plans != null) {
				Plan tempPlan;
				for (int i = 0; i < plans.size(); i++) {
					tempPlan = plans.get(i);
					if (tempPlan.getFilm().getFilmid() != filmid
							|| !tempPlan.getStatus().endsWith("已通过")) {
						plans.remove(i);
						i--;
					}
				}

				ArrayList<Integer> reval = new ArrayList<Integer>(50);
				for (Plan plan : plans) {
					reval.add(plan.getPlanid());
				}
				reval.trimToSize();
				return reval;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
