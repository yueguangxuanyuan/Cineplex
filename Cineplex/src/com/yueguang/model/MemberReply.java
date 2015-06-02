package com.yueguang.model;

public class MemberReply {
	private int replyid;
	private QuestiontoPlan questiontoPlan;
	private Ticket ticket;
	private String choice;

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public QuestiontoPlan getQuestiontoPlan() {
		return questiontoPlan;
	}

	public void setQuestiontoPlan(QuestiontoPlan questiontoPlan) {
		this.questiontoPlan = questiontoPlan;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

}
