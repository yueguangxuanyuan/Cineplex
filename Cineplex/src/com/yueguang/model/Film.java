package com.yueguang.model;

import java.sql.Timestamp;

public class Film {
	private int filmid;
	private String name;
	private String introduction;
	private String picture;
	private Timestamp ontime;
	private Timestamp offtime;
	private float price;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getFilmid() {
		return filmid;
	}

	public void setFilmid(int filmid) {
		this.filmid = filmid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

}
