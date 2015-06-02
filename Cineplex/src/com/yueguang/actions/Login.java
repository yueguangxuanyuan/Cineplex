package com.yueguang.actions;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.yueguang.dao.MemberDao;
import com.yueguang.model.Member;
import com.yueguang.model.MemberLevel;
import com.yueguang.service.AccendantManager;
import com.yueguang.service.CEOManager;
import com.yueguang.service.ManagerManager;
import com.yueguang.service.MemberManager;
import com.yueguang.service.StaffManager;
import com.yueguang.util.ApplicationContextUtil;

public class Login extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3181520989553022728L;
	private String account;
	// private String memberid;
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
	HashMap<String, String> toJsonMap = new HashMap();

	public HashMap<String, String> getToJsonMap() {
		return toJsonMap;
	}

	public void setToJsonMap(HashMap<String, String> toJsonMap) {
		this.toJsonMap = toJsonMap;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// public String getMemberid() {
	// return memberid;
	// }
	//
	// public void setMemberid(String memberid) {
	// this.memberid = memberid;
	// }

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

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

	// 假设
	// 之前已经检查了所有值的合法性
	// 使用重定向来指向相应的页面
	public String login() {
		String errorMessage = "";
		String reval = "";
		session.clear();
		if (account.startsWith("m")) {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			if (memberManager.Login(account, password)) {
				// 进行检查缴纳操作
				memberManager.payMemberDues(account);
				reval = "member";
			} else {
				errorMessage = "会员登陆失败";
				reval = "fail";
			}
		} else if (account.startsWith("s")) {
			StaffManager staffManager = (StaffManager) ApplicationContextUtil
					.getBean("staffManager");
			if (staffManager.Login(account, password)) {
				reval = "staff";
			} else {
				errorMessage = "服务员登陆失败";
				reval = "fail";
			}
		} else if (account.startsWith("M")) {
			ManagerManager managerManager = (ManagerManager) ApplicationContextUtil
					.getBean("managerManager");
			if (managerManager.Login(account, password)) {
				reval = "manager";
			} else {
				errorMessage = "经理登陆失败";
				reval = "fail";
			}
		} else if (account.startsWith("c")) {
			CEOManager ceoManager = (CEOManager) ApplicationContextUtil
					.getBean("ceoManager");

			if (ceoManager.Login(account, password)) {
				reval = "ceo";
			} else {
				errorMessage = "CEO登陆失败";
				reval = "fail";
			}
		} else if (account.startsWith("a")) {
			AccendantManager accendantManager = (AccendantManager) ApplicationContextUtil
					.getBean("accendantManager");
			if (accendantManager.Login(account, password)) {
				reval = "accendant";
			} else {
				errorMessage = "accendant登陆失败";
				reval = "fail";
			}
		} else {

			errorMessage = "用户登陆失败";
			reval = "fail";
		}

		if (!reval.equals("fail")) {
			session.put("userid", account);
		} else {
			session.put("errorMessage", errorMessage);
		}
		return reval;
	}

	// 使用JSON 形式返回注册信息
	public String register() {
		try {
			toJsonMap.clear();
			Member member = new Member();
			member.setBalance(0);
			member.setBankcard(bankcard);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date date = new Date();
			member.setRegistertime(new java.sql.Date(date.getTime()));
			date = simpleDateFormat.parse(birthday);
			member.setBirthday(new java.sql.Date(date.getTime()));
			member.setIntegral(0);
			MemberLevel memberLevel = new MemberLevel();
			memberLevel.setLevel(0);
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
			toJsonMap.put("memberid", memberid);
		} catch (Exception e) {
			toJsonMap.put("errorMessage", "注册出现问题");
		}
		return "map";
	}

	// 使用JSON形式返回找回账号状态
	public String findAccount() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String account = memberManager.getMemberIdByBankandName(bankcard, name);

		toJsonMap.clear();
		if (account == null) {
			toJsonMap.put("errorMessage", "，输入信息有错,找回密码失败");
		} else {
			toJsonMap.put("account", account);
		}
		return "map";
	}

	// 使用JSON形式返回找回密码状态
	public String findPassword() {
		String password = "";
		if (account.startsWith("m")) {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			password = memberManager.getPasswordByAccountandName(account, name);
		} else if (account.startsWith("s")) {
			StaffManager staffManager = (StaffManager) ApplicationContextUtil
					.getBean("staffManager");
			password = staffManager.getPasswordByAccountandName(account, name);
		} else if (account.startsWith("M")) {
			ManagerManager managerManager = (ManagerManager) ApplicationContextUtil
					.getBean("managerManager");
			password = managerManager
					.getPasswordByAccountandName(account, name);

		} else if (account.startsWith("c")) {
			CEOManager ceoManager = (CEOManager) ApplicationContextUtil
					.getBean("ceoManager");
			password = ceoManager.getPasswordByAccountandName(account, name);
		} else if (account.startsWith("a")) {
			AccendantManager accendantManager = (AccendantManager) ApplicationContextUtil
					.getBean("accendantManager");
			password = accendantManager.getPasswordByAccountandName(account,
					name);
		}
		toJsonMap.clear();
		if (password == null || password.equals("")) {
			toJsonMap.put("errorMessage", "找回密码失败");
		} else {
			toJsonMap.put("password", password);
		}
		return "map";
	}
}
