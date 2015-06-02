package com.yueguang.model;

import java.sql.Timestamp;

public class CinemaStatistic {
	private Timestamp time;
	private String peoplecount;
	private String buyticketstatistic;

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getPeoplecount() {
		return peoplecount;
	}

	public void setPeoplecount(String peoplecount) {
		this.peoplecount = peoplecount;
	}

	public String getBuyticketstatistic() {
		return buyticketstatistic;
	}

	public void setBuyticketstatistic(String buyticketstatistic) {
		this.buyticketstatistic = buyticketstatistic;
	}

}
