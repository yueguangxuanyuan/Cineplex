package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Member;
import com.yueguang.service.MemberManager;
import com.yueguang.util.ApplicationContextUtil;

public class DoChangeMemberInfo extends ActionSupport {

	private static final long serialVersionUID = -5201782801477893640L;
	private String memberid;
	private String name;
	private String password;
	private String bankcard;
	private String paypassword;
	private String birthday;
	private int sex;
	private String city;
	private String area;
	private String street;
	private String oldpassword;
	private String oldpaypassword;
	private InputStream changeInfoMSG;

	public String getOldpaypassword() {
		return oldpaypassword;
	}

	public void setOldpaypassword(String oldpaypassword) {
		this.oldpaypassword = oldpaypassword;
	}

	public InputStream getChangeInfoMSG() {
		return changeInfoMSG;
	}

	public void setChangeInfoMSG(InputStream changeInfoMSG) {
		this.changeInfoMSG = changeInfoMSG;
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
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

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		MemberManager manager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		Member member = manager.getMemberByMemberid(memberid);
		if (member.getPassword().equals(oldpassword)
				&& member.getPaypassword().equals(oldpaypassword)) {
			member.setName(name);
			member.setBankcard(bankcard);
			member.setPassword(password);
			member.setPaypassword(paypassword);
			member.setResidence(city + "-" + area + "-" + street);
			member.setSex((sex == 1) ? "男" : "女");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date date = new Date();
			try {
				date = simpleDateFormat.parse(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			manager.updateMemberInfo(member);
			changeInfoMSG = new StringBufferInputStream(
					"<h1>changeMemberInfo Success</h1>");

			return "success";
		} else {
			changeInfoMSG = new StringBufferInputStream(
					"<h1 style=\"color:red\">wrong password</h1>");
			return "fail";
		}
	}
}
