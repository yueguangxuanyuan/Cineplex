package com.yueguang.actions;

import java.sql.Timestamp;

public class Accendant extends BaseAction{
	private String buyfilmplanid;
	private String filmname;
	private String introduction;
	private Timestamp ontime;
	private Timestamp offtime;
	private float price;
	
	
	public String getBuyfilmplanid() {
		return buyfilmplanid;
	}

	public void setBuyfilmplanid(String buyfilmplanid) {
		this.buyfilmplanid = buyfilmplanid;
	}

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

	public Timestamp getOntime() {
		return ontime;
	}

	public void setOntime(Timestamp ontime) {
		this.ontime = ontime;
	}

	public Timestamp getOfftime() {
		return offtime;
	}

	public void setOfftime(Timestamp offtime) {
		this.offtime = offtime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	//返回运维人员的个人信息主页
	public String info(){
		return "info";
	}
	
	//返回带处理的电影收购计划
	public String buyfilmplans(){
		return "buyfilmplans";
	}
	
	//发布电影
//	public 
	
}
