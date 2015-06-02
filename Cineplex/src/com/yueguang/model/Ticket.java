package com.yueguang.model;

import java.sql.Timestamp;

public class Ticket implements Comparable<Ticket>{
    private  int  ticketid;
    private Member member;
    private Plan plan;
    private int  seat;
    private String  payType;
    private Timestamp buytime; 
	public int getTicketid() {
		return ticketid;
	}
	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Timestamp getBuytime() {
		return buytime;
	}
	public void setBuytime(Timestamp buytime) {
		this.buytime = buytime;
	}
	public int compareTo(Ticket o) {
		int cachethis = getPlan().getPlanid()*1000+seat;
		int cachethat = o.getPlan().getPlanid()*1000+o.getSeat();
		if(cachethis > cachethat){
			return 1;
		}else if(cachethis == cachethat){
			return 0;
		}else{
			return -1;
		}
	}
    
}
