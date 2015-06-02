package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.yueguang.model.Member;
import com.yueguang.model.Ticket;
import com.yueguang.model.Plan;
import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.service.MemberManager;
import com.yueguang.service.PayManager;
import com.yueguang.service.PlanManager;
import com.yueguang.service.TicketManager;
import com.yueguang.util.ApplicationContextUtil;

public class BuyTicketAction extends ActionSupport {
	private static final long serialVersionUID = -778439314967187804L;

	private String memberid;
	private int targetPage;
	private InputStream inputStream;
	private int planid;
	private int seat;
	private String planids;
	private String seats;
	private int memberSeletedTicketCount;
	private String bankcard;
	private String paypassword;
	private String payType;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getMemberSeletedTicketCount() {
		return memberSeletedTicketCount;
	}

	public void setMemberSeletedTicketCount(int memberSeletedTicketCount) {
		this.memberSeletedTicketCount = memberSeletedTicketCount;
	}

	public String getPlanids() {
		return planids;
	}

	public void setPlanids(String planids) {
		this.planids = planids;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public int getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(int targetPage) {
		this.targetPage = targetPage;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	// 返回用户 买电影票的页面
	public String GetMemberBuyTicketsPage() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = memberManager.checkMemberState(memberid);
		if (status.equals("有效")) {
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			List<Plan> plansList = planManager.getAvailableFilmsForBuyTickets();
			Plan[] plans = (Plan[]) plansList.toArray(new Plan[0]);
			Arrays.sort(plans);
			int defaultSize = 5;
			// 确定要查找的planindex的上界
			int size = plans.length > defaultSize ? defaultSize : plans.length;
			Map<Integer, Integer> soldTicketsCount = new HashMap<Integer, Integer>();
			for (int i = 0; i < size; i++) {
				int tickets = ticketManager.getSoldSeatsByPlanid(
						plans[i].getPlanid()).size();
				soldTicketsCount.put(plans[i].getPlanid(), tickets);
			}
			Member member = memberManager.getMemberByMemberid(memberid);
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("plans", plans);
			request.setAttribute("soldTicketsCount", soldTicketsCount);
			request.setAttribute("planIndex", 1);
			request.setAttribute("member", member);
			return "success";
		} else if (status.equals("暂停")) {
			inputStream = new StringBufferInputStream("account is locked");
			return "fail";
		} else if (status.equals("停止")) {
			inputStream = new StringBufferInputStream("account is forbidden");
			return "fail";
		} else {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}

	}

	// 返回可以 买票的电影列表
	public String GetAvailableFilms() {
		TicketManager ticketManager = (TicketManager) ApplicationContextUtil
				.getBean("ticketManager");
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Plan> plansList = planManager.getAvailableFilmsForBuyTickets();
		Plan[] plans = (Plan[]) plansList.toArray(new Plan[0]);
		Arrays.sort(plans);
		int defaultSize = 5;
		int planPages = (plans.length + defaultSize - 1) / defaultSize;
		if (targetPage > planPages || targetPage < 1) {
			inputStream = new StringBufferInputStream(
					"<h2>PageIndex out of border</h2>");
			return "fail";
		}
		// 确定要查找的planindex的上界
		int size = plans.length > defaultSize * targetPage ? defaultSize
				* targetPage : plans.length;
		Map<Integer, Integer> soldTicketsCount = new HashMap<Integer, Integer>();
		for (int i = 0; i < size; i++) {
			int tickets = ticketManager.getSoldSeatsByPlanid(
					plans[i].getPlanid()).size();
			soldTicketsCount.put(plans[i].getPlanid(), tickets);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("soldTicketsCount", soldTicketsCount);
		request.setAttribute("planIndex", targetPage);
		return "success";
	}

	// 返回电影信息以及座位情况
	public String getPlanInfo() {
		try {
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			Plan plan = planManager.getPlanById(planid);
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			List<Integer> seats = ticketManager.getSoldSeatsByPlanid(planid);
			HttpServletRequest request = ServletActionContext.getRequest();

			request.setAttribute("plan", plan);
			request.setAttribute("seats", seats);
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("System Error");
			return "fail";
		}
	}

	// 返回用户的选票的表格
	public String getMemberSelectedTickets() {
		try {
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			String warning = ""; // 存放异常信息
			// 存放通过检查的票
			ArrayList<Ticket> tickets = new ArrayList<Ticket>(10);
			// 存放需要支付的金额
			double price = 0;
			// 检查新购的票是否有问题
			List<Integer> soldseats = ticketManager
					.getSoldSeatsByPlanid(planid);
			if (soldseats.contains(seat)) {
				warning += "放映编号 " + planid + "-座位号为 " + seat
						+ " 票已经被购买，请选择其他的票据</br>";
			} else {
				Ticket ticket = new Ticket();
				Plan plan = planManager.getPlanById(planid);
				ticket.setPlan(plan);
				ticket.setSeat(seat);
				tickets.add(ticket);
				price += plan.getPrice();
			}

			// 检查原先的票是否存在问题
			if (memberSeletedTicketCount > 0) {
				String[] planidArray = planids.split("-");
				String[] seatArray = seats.split("-");
				for (int i = 0; i < memberSeletedTicketCount; i++) {
					// 这里可以改进 用Map缓存一下 可以提高大数据的速度
					soldseats = ticketManager.getSoldSeatsByPlanid(Integer
							.parseInt(planidArray[i]));
					if (soldseats.contains(Integer.parseInt(seatArray[i]))) {
						warning += "放映编号 " + planidArray[i] + "-座位号为 "
								+ seatArray[i] + " 的票已经被购买，请选择其他的票据</br>";
					} else {
						Ticket ticket = new Ticket();
						Plan plan = planManager.getPlanById(Integer
								.parseInt(planidArray[i]));
						ticket.setPlan(plan);
						ticket.setSeat(Integer.parseInt(seatArray[i]));
						// 去除重复选票
						boolean isRepeated = false;
						for (int j = 0; j < tickets.size(); j++) {
							if (tickets.get(j).compareTo(ticket) == 0) {
								isRepeated = true;
								warning += "放映编号 " + planidArray[i] + "-座位号为 "
										+ seatArray[i] + " 的票已经被你选择</br>";
								break;
							}
						}
						if (!isRepeated) {
							tickets.add(ticket);
							price += plan.getPrice();
						}
					}
				}
			}
			// 对得到的票进行排序
			tickets.trimToSize();
			Ticket[] ticketArray = tickets.toArray(new Ticket[0]);
			Arrays.sort(ticketArray);
			// 根据用户折扣进行计算
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			Member member = memberManager.getMemberByMemberid(memberid);
			price = price * member.getMemberLevel().getDiscount();

			// 四舍五入保留2位小数
			BigDecimal b = new BigDecimal(price);
			price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("price", price);
			request.setAttribute("ticketArray", ticketArray);
			request.setAttribute("warning", warning);
			request.setAttribute("member", member);
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("ERROR");
			e.printStackTrace();
			return "fail";
		}
	}

	// 会员网上购票 提交之后支付以及保存过程
	public String memberBuySelectedTickets() {
		try {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PayManager payManager = (PayManager) ApplicationContextUtil
					.getBean("payManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");

			Member member = memberManager.getMemberByMemberid(memberid);
			String[] planidArray = planids.split("-");
			String[] seatArray = seats.split("-");
			List<Ticket> buiedTickets = new ArrayList<Ticket>();
			double price = 0;
			for (int i = 0; i < memberSeletedTicketCount; i++) {
				int planid = Integer.parseInt(planidArray[i]);
				int seat = Integer.parseInt(seatArray[i]);
				Ticket ticket = new Ticket();
				ticket.setMember(member);
				ticket.setBuytime(new Timestamp(new Date().getTime()));
				if (bankcard == null || bankcard.equals("")) {
					ticket.setPayType("会员卡支付");
				} else {
					ticket.setPayType("银行卡支付");
				}
				Plan plan = planManager.getPlanById(planid);
				ticket.setPlan(plan);
				price += plan.getPrice();
				ticket.setSeat(seat);
				buiedTickets.add(ticket);
			}
			price = price * member.getMemberLevel().getDiscount();
			// 四舍五入保留2位小数
			BigDecimal b = new BigDecimal(price);
			price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (bankcard == null || bankcard.equals("")) {
				if (!payManager.payinCard(memberid, price, paypassword)) {
					inputStream = new StringBufferInputStream(
							"failed in paying");
					return "fail";
				}
			} else {
				if (!payManager.payinInternet(memberid, price, bankcard)) {
					inputStream = new StringBufferInputStream(
							"failed in paying");
					return "fail";

				}
			}

			for (Ticket t : buiedTickets) {
				ticketManager.buyTicket(memberid, t.getPlan().getPlanid(),
						t.getSeat(), t.getPayType());
			}
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 返回服务员买票的页面
	public String getStaffBuyTicketPage() {
		TicketManager ticketManager = (TicketManager) ApplicationContextUtil
				.getBean("ticketManager");
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Plan> plansList = planManager.getAvailableFilmsForBuyTickets();

		Plan[] plans = (Plan[]) plansList.toArray(new Plan[0]);
		Arrays.sort(plans);
		int defaultSize = 5;
		// 确定要查找的planindex的上界
		int size = plans.length > defaultSize ? defaultSize : plans.length;
		Map<Integer, Integer> soldTicketsCount = new HashMap<Integer, Integer>();
		for (int i = 0; i < size; i++) {
			int tickets = ticketManager.getSoldSeatsByPlanid(
					plans[i].getPlanid()).size();
			soldTicketsCount.put(plans[i].getPlanid(), tickets);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("soldTicketsCount", soldTicketsCount);
		request.setAttribute("planIndex", 1);
		return "success";
	}

	// 处理 非会员支付
	public String payByNotMember() {
		try {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PayManager payManager = (PayManager) ApplicationContextUtil
					.getBean("payManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			memberid = "1234567";
			Member member = memberManager.getMemberByMemberid(memberid);
			String[] planidArray = planids.split("-");
			String[] seatArray = seats.split("-");
			List<Ticket> buiedTickets = new ArrayList<Ticket>();
			double price = 0;
			for (int i = 0; i < memberSeletedTicketCount; i++) {
				int planid = Integer.parseInt(planidArray[i]);
				int seat = Integer.parseInt(seatArray[i]);
				Ticket ticket = new Ticket();
				ticket.setMember(member);
				ticket.setBuytime(new Timestamp(new Date().getTime()));
				ticket.setPayType("现金支付");
				Plan plan = planManager.getPlanById(planid);
				ticket.setPlan(plan);
				price += plan.getPrice();
				ticket.setSeat(seat);
				buiedTickets.add(ticket);
			}
			price = price * member.getMemberLevel().getDiscount();
			// 四舍五入保留2位小数
			BigDecimal b = new BigDecimal(price);
			price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (!payManager.payinCash(memberid, price)) {
				inputStream = new StringBufferInputStream(
						"Error occurred in pay");
				return "fail";
			}
			for (Ticket t : buiedTickets) {
				ticketManager.buyTicket(memberid, t.getPlan().getPlanid(),
						t.getSeat(), t.getPayType());
			}
			inputStream = new StringBufferInputStream("Success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 处理会员支付
	public String payByMember() {
		try {
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PayManager payManager = (PayManager) ApplicationContextUtil
					.getBean("payManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");

			String status = memberManager.checkMemberState(memberid);
			if (status.equals("有效")) {
				Member member = memberManager.getMemberByMemberid(memberid);
				if (member == null) {
					inputStream = new StringBufferInputStream(
							"Error in find member");
					return "fail";
				}
				String[] planidArray = planids.split("-");
				String[] seatArray = seats.split("-");
				List<Ticket> buiedTickets = new ArrayList<Ticket>();
				double price = 0;
				for (int i = 0; i < memberSeletedTicketCount; i++) {
					int planid = Integer.parseInt(planidArray[i]);
					int seat = Integer.parseInt(seatArray[i]);
					Ticket ticket = new Ticket();
					ticket.setMember(member);
					ticket.setBuytime(new Timestamp(new Date().getTime()));
					if (payType.equals("现金支付")) {
						ticket.setPayType("现金支付");
					} else {
						ticket.setPayType("会员卡支付");
					}
					Plan plan = planManager.getPlanById(planid);
					ticket.setPlan(plan);
					price += plan.getPrice();
					ticket.setSeat(seat);
					buiedTickets.add(ticket);
				}
				price = price * member.getMemberLevel().getDiscount();
				// 四舍五入保留2位小数
				BigDecimal b = new BigDecimal(price);
				price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (payType.equals("现金支付")) {
					if (!payManager.payinCash(memberid, price)) {
						inputStream = new StringBufferInputStream(
								"failed in paying by cash");
						return "fail";
					}
				} else {
					if (!payManager.payinCard(memberid, price, paypassword)) {
						inputStream = new StringBufferInputStream(
								"failed in paying by card");
						return "fail";
					}
				}

				for (Ticket t : buiedTickets) {
					ticketManager.buyTicket(memberid, t.getPlan().getPlanid(),
							t.getSeat(), t.getPayType());
				}
				inputStream = new StringBufferInputStream("Success	");
				return "success";
			} else if (status.equals("暂停")) {
				inputStream = new StringBufferInputStream("account is locked");
				return "fail";
			} else if (status.equals("停止")) {
				inputStream = new StringBufferInputStream(
						"account is forbidden");
				return "fail";
			} else {
				inputStream = new StringBufferInputStream("Error");
				return "fail";
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 返回服务员选票的列表
	public String getStaffSelectedTickets() {
		try {
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			String warning = ""; // 存放异常信息
			// 存放通过检查的票
			ArrayList<Ticket> tickets = new ArrayList<Ticket>(10);
			// 存放需要支付的金额
			double price = 0;
			// 检查新购的票是否有问题
			List<Integer> soldseats = ticketManager
					.getSoldSeatsByPlanid(planid);
			if (soldseats.contains(seat)) {
				warning += "放映编号 " + planid + "-座位号为 " + seat
						+ " 票已经被购买，请选择其他的票据</br>";
			} else {
				Ticket ticket = new Ticket();
				Plan plan = planManager.getPlanById(planid);
				ticket.setPlan(plan);
				ticket.setSeat(seat);
				tickets.add(ticket);
				price += plan.getPrice();
			}

			// 检查原先的票是否存在问题
			if (memberSeletedTicketCount > 0) {
				String[] planidArray = planids.split("-");
				String[] seatArray = seats.split("-");
				for (int i = 0; i < memberSeletedTicketCount; i++) {
					// 这里可以改进 用Map缓存一下 可以提高大数据的速度
					soldseats = ticketManager.getSoldSeatsByPlanid(Integer
							.parseInt(planidArray[i]));
					if (soldseats.contains(Integer.parseInt(seatArray[i]))) {
						warning += "放映编号 " + planidArray[i] + "-座位号为 "
								+ seatArray[i] + " 的票已经被购买，请选择其他的票据</br>";
					} else {
						Ticket ticket = new Ticket();
						Plan plan = planManager.getPlanById(Integer
								.parseInt(planidArray[i]));
						ticket.setPlan(plan);
						ticket.setSeat(Integer.parseInt(seatArray[i]));
						// 去除重复选票
						boolean isRepeated = false;
						for (int j = 0; j < tickets.size(); j++) {
							if (tickets.get(j).compareTo(ticket) == 0) {
								isRepeated = true;
								warning += "放映编号 " + planidArray[i] + "-座位号为 "
										+ seatArray[i] + " 的票已经被你选择</br>";
								break;
							}
						}
						if (!isRepeated) {
							tickets.add(ticket);
							price += plan.getPrice();
						}
					}
				}
			}
			// 对得到的票进行排序
			tickets.trimToSize();
			Ticket[] ticketArray = tickets.toArray(new Ticket[0]);
			Arrays.sort(ticketArray);

			// 四舍五入保留2位小数
			BigDecimal b = new BigDecimal(price);
			price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("price", price);
			request.setAttribute("ticketArray", ticketArray);
			request.setAttribute("warning", warning);
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("ERROR");
			return "fail";
		}
	}
}
