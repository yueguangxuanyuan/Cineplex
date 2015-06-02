package com.yueguang.service;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.yueguang.dao.BaseDao;
import com.yueguang.model.Buyfilmplan;
import com.yueguang.model.Film;

public class BuyfilmplanManager {
	BaseDao baseDao;

	// 查找未处理的电影收购计划
	public List<Buyfilmplan> getUncheckedBuyfilmplans() {
		HashMap<String, String> condition = new HashMap<String, String>();
		condition.put("status", "未处理");
		return baseDao.find(Buyfilmplan.class, condition);
	}

	// 发布电影收购计划
	public boolean publishBuyfilmplan(Buyfilmplan buyfilmplan) {
		try {
			baseDao.save(buyfilmplan);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//完成电影收购计划   插入新电影  并修改收购计划状态
	public boolean finishBuyfilmplan(int id,Film film){
		Session session = baseDao.getNewSession();
		Transaction tx=session.beginTransaction();
		try{
			tx.begin();
			baseDao.save(film);
			Buyfilmplan buyfilmplan = (Buyfilmplan) baseDao.load(Buyfilmplan.class, id);
			buyfilmplan.setStatus("已处理");
			baseDao.update(buyfilmplan);
            tx.commit();
			return true;
		}catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			return false;
		}finally{
			session.close();
		}
	}
}
