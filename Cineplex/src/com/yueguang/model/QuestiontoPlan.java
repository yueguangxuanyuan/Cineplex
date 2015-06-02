package com.yueguang.model;

public class QuestiontoPlan {
	int qtpid;
	Question question;
	Plan plan;
	String statistic;
	String status;

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getQtpid() {
		return qtpid;
	}

	public void setQtpid(int qtpid) {
		this.qtpid = qtpid;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}
