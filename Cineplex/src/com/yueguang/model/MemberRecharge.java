package com.yueguang.model;

import java.sql.Timestamp;

public class MemberRecharge {
    private int rechargeid;
    private Member member;
    private Timestamp time;
    private String detail;
    
	public int getRechargeid() {
		return rechargeid;
	}
	public void setRechargeid(int rechargeid) {
		this.rechargeid = rechargeid;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
    
}
