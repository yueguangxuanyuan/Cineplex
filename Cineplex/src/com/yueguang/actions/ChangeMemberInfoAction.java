package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Member;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class ChangeMemberInfoAction extends ActionSupport {

	private static final long serialVersionUID = -7581089220083829590L;

	private String memberid;
    private InputStream inputStream;
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String execute() {
		MemberManager manager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = manager.checkMemberState(memberid);
		if(status.equals("有效")){
			Member member = manager.getMemberByMemberid(memberid);
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("member", member);
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

}
