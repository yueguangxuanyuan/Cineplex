package com.yueguang.actions;

import java.util.HashMap;

import com.yueguang.model.Buyfilmplan;
import com.yueguang.service.BuyfilmplanManager;
import com.yueguang.util.ApplicationContextUtil;

public class Manager extends BaseAction {
	private String filmname;
	private String introduction;
	private String reason;

	private HashMap<String, String> toJsonMap = new HashMap();

	public String getFilmname() {
		return filmname;
	}

	public void setFilmname(String filmname) {
		this.filmname = filmname;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public HashMap<String, String> getToJsonMap() {
		return toJsonMap;
	}

	public void setToJsonMap(HashMap<String, String> toJsonMap) {
		this.toJsonMap = toJsonMap;
	}

	// 返回经理个人信息页面
	public String info() {
		return "info";
	}

	// 返回发布电影收购计划页面
	public String buyfilmplans() {
		return "buyfilmplans";
	}

	// 发布电影收购计划,结束后返回发布电影计划界面
	public String publishBuyfilmplan() {
		Buyfilmplan buyfilmplan = new Buyfilmplan();
		buyfilmplan.setFilmname(filmname);
		buyfilmplan.setIntroduction(introduction);
		buyfilmplan.setReason(reason);
        buyfilmplan.setStatus("未处理");
		BuyfilmplanManager buyfilmplanManager = (BuyfilmplanManager) ApplicationContextUtil
				.getBean("buyfilmplanManager");

		buyfilmplanManager.publishBuyfilmplan(buyfilmplan);
		return "buyfilmplans";
	}
}
