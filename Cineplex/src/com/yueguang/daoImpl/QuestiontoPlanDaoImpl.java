package com.yueguang.daoImpl;

import java.util.HashMap;
import java.util.List;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.QuestiontoPlanDao;
import com.yueguang.model.QuestiontoPlan;

public class QuestiontoPlanDaoImpl implements QuestiontoPlanDao {
	BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void inserQtP(QuestiontoPlan questiontoPlan) {
		questiontoPlan.setStatistic("未统计");
		questiontoPlan.setStatus("未处理");
        baseDao.save(questiontoPlan);
	}

	public void deleteQtP(int qtpid) {
        baseDao.delete(QuestiontoPlan.class, qtpid);
	}

	public List<QuestiontoPlan> getQtPbyQuestionid(int questionid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("questionid", String.valueOf(questionid));
		return baseDao.find(QuestiontoPlan.class, condition);
	}

	public List<QuestiontoPlan> getQtPbyPlanid(int planid) {
		HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("planid", String.valueOf(planid));
		return baseDao.find(QuestiontoPlan.class, condition);
	}

	public void updateQtP(QuestiontoPlan questiontoPlan) {
		baseDao.update(questiontoPlan);
	}

}
