package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;


import com.mysql.fabric.xmlrpc.base.Data;
import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Member;
import com.yueguang.model.MemberLevel;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class MemberAction extends ActionSupport {

	private static final long serialVersionUID = -6035586419108860791L;
	private String memberid;
	private InputStream inputStream;
	private String name;
	private String password;
	private String bankcard;
	private String paypassword;
	private String birthday;
	private String sex;
	private String city;
	private String area;
	private String street;
	private int memberLevel;
	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		Member member = memberManager.getMemberByMemberid(memberid);
		request.setAttribute("member", member);
		return "success";
	}

	// 得到注册会员界面
	public String getRegisterMemberPage() {

		return "success";
	}

	// 注册用户
	public String registerMember() {
		try {
			Member member = new Member();
			member.setBalance(0);
			member.setBankcard(bankcard);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date date = new Date();
			date = simpleDateFormat.parse(birthday);
			member.setBirthday(new java.sql.Date(date.getTime()));
			member.setIntegral(0);
			MemberLevel memberLevel = new MemberLevel();
			memberLevel.setLevel(1);
			member.setMemberLevel(memberLevel);
			member.setName(name);
			member.setPassword(password);
			member.setPaypassword(paypassword);
			member.setSex(sex);
			member.setResidence(city + "-" + area + "-" + street);
			// 这个操作是为了实现 用户充值200再激活
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			member.setValidity(new Timestamp(calendar.getTimeInMillis()));
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			String memberid = memberManager.register(member);
			inputStream = new StringBufferInputStream(
					"Register Success<br/> Memberid : " + memberid);
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 返回搜索用户界面
	public String getSearchMemberInfoPage() {
		return "success";
	}

	// 返回改变用户等级界面
	public String getChangeMemberLevelPage() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		List<MemberLevel> memberLevels = memberManager.getMemberLevels();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("memberLevels", memberLevels);
		return "success";
	}

	// 修改用户等级
	public String changeMemberLevel(){
		try{
			MemberManager memberManager=(MemberManager)ApplicationContextUtil.getBean("memberManager");
			Member member = memberManager.getMemberByMemberid(memberid);
			if(member == null){
				inputStream=new StringBufferInputStream("Member inexist");
				return "fail";
			}
			MemberLevel targetmemberLevel= new MemberLevel();
			targetmemberLevel.setLevel(memberLevel);
			member.setMemberLevel(targetmemberLevel);
			memberManager.updateMemberInfo(member);
			inputStream=new StringBufferInputStream("ChangeMemberLevel Success");
			return "success";
		}catch(Exception e){
			inputStream=new StringBufferInputStream("Error");
			return "fail";
		}
	}
	
	//获取恢复会员资格的页面
	public String getRecoverMemberPage(){
		return "success";
	}
	
	//恢复会员资格
	public String recoverMember(){
		try{
			MemberManager memberManager=(MemberManager)ApplicationContextUtil.getBean("memberManager");
			Member member = memberManager.getMemberByMemberid(memberid);
			if(member == null){
				inputStream=new StringBufferInputStream("Member inexist");
				return "fail";
			}
			Timestamp validity = member.getValidity();
			Timestamp now = new Timestamp(new Date().getTime());
			if(validity.after(now)){
				inputStream=new StringBufferInputStream("no need to recover");
				return "fail";
			}
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			Timestamp oneYearAgo=new Timestamp(calendar.getTimeInMillis());
			if(oneYearAgo.after(validity)){
				inputStream=new StringBufferInputStream("cannot recover");
				return "fail";
			}
			calendar=Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			Timestamp oneYearLater = new Timestamp(calendar.getTimeInMillis());
			member.setValidity(oneYearLater);
			memberManager.updateMemberInfo(member);
			inputStream=new StringBufferInputStream("Success");
			return "success";
		}catch(Exception e){
			inputStream=new StringBufferInputStream("Error");
			return "fail";
		}
	}
}
