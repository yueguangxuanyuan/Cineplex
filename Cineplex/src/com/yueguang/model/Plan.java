package com.yueguang.model;

import java.sql.Timestamp;

//电影放映计划     确定某一个电影的放映场次
public class Plan implements Comparable<Plan>{
	private int planid;
	private String status;
	private Staff staff;
	private Film film;
	private VedioHall vedioHall;
	private Timestamp starttime;
	private Timestamp endtime;
	private float price;

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public VedioHall getVedioHall() {
		return vedioHall;
	}

	public void setVedioHall(VedioHall vedioHall) {
		this.vedioHall = vedioHall;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int compareTo(Plan o) {
		if(this.getStarttime().after(o.getStarttime())){
			return 1;
		}else if(this.getStarttime().equals(o.getStarttime())){
			return 0;
		}else{
			return -1;
		}
	}
	
	

}
