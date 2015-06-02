package com.yueguang.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.yueguang.model.Member;
import com.yueguang.dao.CinemaStatisticDao;
import com.yueguang.dao.MemberDao;
import com.yueguang.dao.MemberStatitsticDao;
import com.yueguang.dao.PlanDao;
import com.yueguang.dao.TicketDao;
import com.yueguang.model.CinemaStatistic;
import com.yueguang.model.MemberStatistic;
import com.yueguang.model.Plan;
import com.yueguang.model.Ticket;
import com.yueguang.util.ApplicationContextUtil;

public class StatisticManager {
	private CinemaStatisticDao cinemaStatisticDao;
	private MemberStatitsticDao memberStatitsticDao;

	public CinemaStatisticDao getCinemaStatisticDao() {
		return cinemaStatisticDao;
	}

	public void setCinemaStatisticDao(CinemaStatisticDao cinemaStatisticDao) {
		this.cinemaStatisticDao = cinemaStatisticDao;
	}

	public MemberStatitsticDao getMemberStatitsticDao() {
		return memberStatitsticDao;
	}

	public void setMemberStatitsticDao(MemberStatitsticDao memberStatitsticDao) {
		this.memberStatitsticDao = memberStatitsticDao;
	}

	// 得到上一个月memberstatistic
	public MemberStatistic getLastMonthMemberStatistic() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String lastmonthString = simpleDateFormat.format(calendar.getTime());
		Date lastmonthdate = new Date();
		try {
			lastmonthdate = simpleDateFormat.parse(lastmonthString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Timestamp lastMonthTimestamp = new Timestamp(lastmonthdate.getTime());
		MemberStatistic memberStatistic = memberStatitsticDao
				.findMemberStatisticByTime(lastMonthTimestamp);
		if (memberStatistic == null) {
			memberStatistic = generatorLastMonthMemberStatistic(lastMonthTimestamp);
		}
		return memberStatistic;
	}

	// 生成上一个月memberstatistic
	/**
	 * @param lastmonthTimestamp
	 * @return
	 */
	private MemberStatistic generatorLastMonthMemberStatistic(
			Timestamp lastmonthTimestamp) {
		MemberDao memberDao = (MemberDao) ApplicationContextUtil
				.getBean("memberDaoImpl");
		List<Member> members = memberDao.getAllMembers();
		TreeMap<String, Integer> ageMap = new TreeMap<String, Integer>();
		TreeMap<String, Integer> sexMap = new TreeMap<String, Integer>();
		TreeMap<String, Integer> cityMap = new TreeMap<String, Integer>();
		TreeMap<String, Integer> areaMap = new TreeMap<String, Integer>();
		TreeMap<String, Integer> validityMap = new TreeMap<String, Integer>();
		// 初始化统计量
		ageMap.put("20岁以下", 0);
		ageMap.put("20岁到40岁", 0);
		ageMap.put("40岁以上", 0);

		sexMap.put("男", 0);
		sexMap.put("女", 0);

		cityMap.put("南京", 0);
		String[] areas = { "玄武区", "鼓楼区", "建邺区", "白下区", "秦淮区", "下关区", "雨花台区",
				" 浦口区", "栖霞区", " 江宁区", "六合区" };
		for (String area : areas) {
			areaMap.put(area, 0);
		}

		validityMap.put("有效", 0);
		validityMap.put("暂停", 0);
		validityMap.put("停止", 0);

		// 初始化统计相关的量 这里统计的截止时间是 本月的 0时0分0秒
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastmonthTimestamp);
		calendar.add(Calendar.MONTH, 1);

		Timestamp now = new Timestamp(calendar.getTimeInMillis());
		calendar.add(Calendar.YEAR, -1);
		Timestamp oneyearsAgo = new Timestamp(calendar.getTimeInMillis());

		calendar.add(Calendar.YEAR, -19);
		Timestamp twentyyearsAgo = new Timestamp(calendar.getTimeInMillis());

		calendar.add(Calendar.YEAR, -20);
		Timestamp fortyyearsAgo = new Timestamp(calendar.getTimeInMillis());

		for (Member member : members) {
			// 生成年龄统计
			Timestamp birthday = new Timestamp(member.getBirthday().getTime());
			if (birthday.after(twentyyearsAgo)) {
				ageMap.put("20岁以下", ageMap.get("20岁以下") + 1);
			} else if (birthday.after(fortyyearsAgo)) {
				ageMap.put("20岁到40岁", ageMap.get("20岁到40岁") + 1);
			} else {
				ageMap.put("40岁以上", ageMap.get("40岁以上") + 1);
			}
			// 生成性别统计
			sexMap.put(member.getSex(), sexMap.get(member.getSex()) + 1);

			// 生成居住地统计
			String[] residence = member.getResidence().split("-");
			cityMap.put(residence[0], cityMap.get(residence[0]) + 1);
			areaMap.put(residence[1], areaMap.get(residence[1]) + 1);

			// 生成会员卡状态统计
			Timestamp vadility = member.getValidity();
			if (vadility.after(now)) {
				validityMap.put("有效", validityMap.get("有效") + 1);
			} else if (vadility.after(oneyearsAgo)) {
				validityMap.put("暂停", validityMap.get("暂停") + 1);
			} else {
				validityMap.put("停止", validityMap.get("停止") + 1);
			}
		}

		// 拼接年龄统计
		Set<String> agekeys = ageMap.keySet();
		String agestatistic = "";
		for (String agekey : agekeys) {
			if (!agestatistic.equals("")) {
				agestatistic += "-";
			}
			agestatistic += agekey + "-" + ageMap.get(agekey) + "人";
		}

		// 拼接性别统计
		Set<String> sexkeys = sexMap.keySet();
		String sexstatistic = "";
		for (String sexkey : sexkeys) {
			if (!sexstatistic.equals("")) {
				sexstatistic += "-";
			}
			sexstatistic += sexkey + "-" + sexMap.get(sexkey) + "人";
		}

		// 拼接居住地统计
		Set<String> citykeys = cityMap.keySet();
		String residence = "";
		for (String citykey : citykeys) {
			if (!residence.equals("")) {
				residence += "-";
			}
			residence += citykey + "-" + cityMap.get(citykey) + "人";
		}
		residence += "|";
		Set<String> areakeys = areaMap.keySet();
		for (String areakey : areakeys) {
			if (!residence.endsWith("|")) {
				residence += "-";
			}
			residence += areakey + "-" + areaMap.get(areakey) + "人";
		}

		// 拼接会员卡有效情况统计
		Set<String> validitykeys = validityMap.keySet();
		String validitystatistic = "";
		for (String validitykey : validitykeys) {
			if (!validitystatistic.equals("")) {
				validitystatistic += "-";
			}
			validitystatistic += validitykey + "-"
					+ validityMap.get(validitykey) + "人";
		}

		MemberStatistic memberStatistic = new MemberStatistic();
		memberStatistic.setTime(lastmonthTimestamp);
		memberStatistic.setAgestatistic(agestatistic);
		memberStatistic.setResidencestatistic(residence);
		memberStatistic.setSexstatistic(sexstatistic);
		memberStatistic.setValiditystatistic(validitystatistic);

		memberStatitsticDao.insertMemberStatistic(memberStatistic);
		return memberStatistic;
	}

	// 得到上一个月的cinemastatistic
	public CinemaStatistic getLastMonthCinameStatistic() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String lastmonthString = simpleDateFormat.format(calendar.getTime());
		Date lastmonthdate = new Date();
		try {
			lastmonthdate = simpleDateFormat.parse(lastmonthString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Timestamp lastMonthTimestamp = new Timestamp(lastmonthdate.getTime());
		CinemaStatistic cinemaStatistic = cinemaStatisticDao
				.findCinemaStatisticByTime(lastMonthTimestamp);

		if (cinemaStatistic == null) {
			cinemaStatistic = generatorLastMonthCinemaStatistic(lastMonthTimestamp);
		}
		return cinemaStatistic;
	}

	// 生成上一个月的cinemastatistic
	private CinemaStatistic generatorLastMonthCinemaStatistic(
			Timestamp lastmonthTimestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastmonthTimestamp);
		int maxday = calendar.getMaximum(Calendar.DATE);
		TicketDao ticketDao = (TicketDao) ApplicationContextUtil
				.getBean("ticketDaoImpl");
		PlanDao planDao = (PlanDao) ApplicationContextUtil
				.getBean("planDaoImpl");

		List<Ticket> tickets = ticketDao.getTicketsofCertainMonth(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
		List<Plan> plans = planDao.getAvailablePlansofCertainMonth(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
		// 定义统计类型
		TreeMap<Integer, Integer> daypeopleCountMap = new TreeMap<Integer, Integer>();
		TreeMap<String, Integer> payTypeMap = new TreeMap<String, Integer>();
		// 用来统计上座率
		TreeMap<Integer, Integer> planPeopleCountMap = new TreeMap<Integer, Integer>();
		TreeMap<Integer, Integer> filmPeopleCountMap = new TreeMap<Integer, Integer>();
		TreeMap<Integer, String> planHallseatsCountMap = new TreeMap<Integer, String>();
		TreeMap<Integer, Integer> filmSeatsCountMap = new TreeMap<Integer, Integer>();
		// 初始化map
		// 每日人数统计
		for (int i = 1; i <= maxday; i++) {
			daypeopleCountMap.put(i, 0);
		}

		// 支付方式的统计
		payTypeMap.put("现金支付", 0);
		payTypeMap.put("会员卡支付", 0);
		payTypeMap.put("银行卡支付", 0);

		// 电影上座率的统计
		// 由于放映计划和放映厅的对应是唯一的 所以直接把放映厅的上座率转化为放映计划人数的统计 之后考虑放映厅的人数
		for (Plan plan : plans) {
			planPeopleCountMap.put(plan.getPlanid(), 0);
			filmPeopleCountMap.put(plan.getFilm().getFilmid(), 0);
			planHallseatsCountMap.put(plan.getPlanid(), plan.getVedioHall()
					.getHallname() + "-" + plan.getVedioHall().getSeatcount());
			if (filmSeatsCountMap.get(plan.getFilm().getFilmid()) == null) {
				filmSeatsCountMap.put(plan.getFilm().getFilmid(), plan
						.getVedioHall().getSeatcount());
			} else {
				filmSeatsCountMap.put(plan.getFilm().getFilmid(),
						filmSeatsCountMap.get(plan.getFilm().getFilmid())
								+ plan.getVedioHall().getSeatcount());
			}

		}

		// 生成统计信息
		Calendar buytime = calendar.getInstance();
		for (Ticket ticket : tickets) {
			buytime.setTime(ticket.getBuytime());
			daypeopleCountMap.put(buytime.get(Calendar.DATE),
					daypeopleCountMap.get(buytime.get(Calendar.DATE)) + 1);
			payTypeMap.put(ticket.getPayType(),
					payTypeMap.get(ticket.getPayType()) + 1);
			if (planPeopleCountMap.get(ticket.getPlan().getPlanid()) != null) {
				planPeopleCountMap
						.put(ticket.getPlan().getPlanid(), planPeopleCountMap
								.get(ticket.getPlan().getPlanid()) + 1);
				filmPeopleCountMap.put(
						ticket.getPlan().getFilm().getFilmid(),
						filmPeopleCountMap.get(ticket.getPlan().getFilm()
								.getFilmid()) + 1);
			}
		}

		// 拼接统计信息
		int mounthpeoplecount = 0;
		String peoplecount = "";
		Set<Integer> daycountkeys = daypeopleCountMap.keySet();
		for (int daycountkey : daycountkeys) {
			if (!peoplecount.equals("")) {
				peoplecount += "-";
			}
			peoplecount += daycountkey + "号-"
					+ daypeopleCountMap.get(daycountkey) + "人";
			mounthpeoplecount += daypeopleCountMap.get(daycountkey);
		}
		peoplecount += "|月总人数-" + mounthpeoplecount + "人";
		peoplecount += "|上座率统计|关于放映厅的统计";
		Set<Integer> planidKeys = planPeopleCountMap.keySet();
		for (int planid : planidKeys) {
			peoplecount += "<" + planHallseatsCountMap.get(planid) + "-"
					+ planPeopleCountMap.get(planid) + ">";
		}
		peoplecount += "|关于电影的上座率统计";
		Set<Integer> filmidkeys = filmPeopleCountMap.keySet();
		for (int filmid : filmidkeys) {
			peoplecount += "<" + filmSeatsCountMap.get(filmid) + "-"
					+ filmPeopleCountMap.get(filmid) + ">";
		}

		Set<String> payTypekeys = payTypeMap.keySet();
		String payTypeStatistic = "";
		for (String payTypekey : payTypekeys) {
			if (!payTypeStatistic.equals("")) {
				payTypeStatistic += "-";
			}
			payTypeStatistic += payTypekey + "-" + payTypeMap.get(payTypekey)
					+ "人";
		}

		CinemaStatistic cinemaStatistic = new CinemaStatistic();
		cinemaStatistic.setTime(lastmonthTimestamp);
		cinemaStatistic.setBuyticketstatistic(payTypeStatistic);
		cinemaStatistic.setPeoplecount(peoplecount);
		cinemaStatisticDao.insertCinemaStatistic(cinemaStatistic);
		return cinemaStatistic;
	}
}
