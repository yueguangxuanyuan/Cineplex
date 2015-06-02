package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Member;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class MemberLevelAction extends ActionSupport {

	private static final long serialVersionUID = -5630497576300411714L;

	String memberid;
	String password;
	InputStream inputStream;

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
    
	//返回用户手动取消会员资格的界面
	public String  getCancelMemberPage(){
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = memberManager.checkMemberState(memberid);
		if(status.equals("有效")){
			return "success";
		}else if(status.equals("暂停")){
			inputStream = new StringBufferInputStream("account is locked");
			return "fail";
		}else if(status.equals("停止")){
			inputStream = new StringBufferInputStream("account is forbidden");
			return "fail";
		}else{
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}
	
	// 会员手动取消会员资格
	public String memberCancelMember() {
		MemberManager manager =(MemberManager) ApplicationContextUtil.getBean("memberManager");
		Member member=manager.getMemberByMemberid(memberid);
		if(member.getPassword().equals(password)){
			manager.cancelMemberIdentity(memberid);
			inputStream = new StringBufferInputStream("<h2>CancalMember Successs</h2>");
			return "success";
		}else{
			inputStream = new StringBufferInputStream("<h2 style='color:red'>CancalMember Failed</h2>");
			return "fail";
		}
	}
}
