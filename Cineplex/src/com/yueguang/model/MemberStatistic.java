package com.yueguang.model;

import java.sql.Timestamp;

public class MemberStatistic {
	private Timestamp time;
	private String agestatistic;
	private String sexstatistic;
	private String residencestatistic;
	private String validitystatistic;

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getAgestatistic() {
		return agestatistic;
	}

	public void setAgestatistic(String agestatistic) {
		this.agestatistic = agestatistic;
	}

	public String getSexstatistic() {
		return sexstatistic;
	}

	public void setSexstatistic(String sexstatistic) {
		this.sexstatistic = sexstatistic;
	}

	public String getResidencestatistic() {
		return residencestatistic;
	}

	public void setResidencestatistic(String residencestatistic) {
		this.residencestatistic = residencestatistic;
	}

	public String getValiditystatistic() {
		return validitystatistic;
	}

	public void setValiditystatistic(String validitystatistic) {
		this.validitystatistic = validitystatistic;
	}

}
