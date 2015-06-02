package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.dao.QuestionDao;
import com.yueguang.model.Film;
import com.yueguang.model.Member;
import com.yueguang.model.MemberReply;
import com.yueguang.model.Plan;
import com.yueguang.model.Question;
import com.yueguang.model.QuestiontoPlan;
import com.yueguang.model.Ticket;
import com.yueguang.service.ActivityManager;
import com.yueguang.service.FilmManager;
import com.yueguang.service.MemberManager;
import com.yueguang.service.PlanManager;
import com.yueguang.service.TicketManager;
import com.yueguang.util.ApplicationContextUtil;

public class ActivityAction extends ActionSupport {

	private static final long serialVersionUID = 6068197135887585839L;

	private String memberid;
	private int targetPage;
	private InputStream inputStream;
	private int ticketid;
	private String answers;
	private int filmid;
	private String content;
	private String options;
	private String bindPlans;
	private int questionid;
	private int bonus;
	private String selected;

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getBindPlans() {
		return bindPlans;
	}

	public void setBindPlans(String bindPlans) {
		this.bindPlans = bindPlans;
	}

	public int getFilmid() {
		return filmid;
	}

	public void setFilmid(int filmid) {
		this.filmid = filmid;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public int getTicketid() {
		return ticketid;
	}

	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
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

	// 返回能参加活动的票列表 （用票去链接到qtp）
	public String getAttendActivityPage() {
		MemberManager memberManager = (MemberManager) ApplicationContextUtil
				.getBean("memberManager");
		String status = memberManager.checkMemberState(memberid);
		if(status.equals("有效")){
			ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
					.getBean("activityManager");
			TicketManager ticketManager = (TicketManager) ApplicationContextUtil
					.getBean("ticketManager");
			List<Ticket> tickets = ticketManager.getTicketsByMemberid(memberid);
			ArrayList<Ticket> ticketsWithQuestions = new ArrayList<Ticket>(10);
			// 筛选掉所有回答过问题的票以及所有还没看的票
			Timestamp timestamp = new Timestamp(new Date().getTime());
			for (Ticket ticket : tickets) {
				if (timestamp.after(ticket.getPlan().getEndtime())) {
					if (activityManager.getQtPidsbyTicket(ticket).size() > 0) {
						ticketsWithQuestions.add(ticket);
					}
				}
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			ticketsWithQuestions.trimToSize();
			request.setAttribute("ticketsWithQuestions", ticketsWithQuestions);
			request.setAttribute("index", 1);
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

	// 返回能参加活动的票列表指定的index
	public String gotoTicketwithActivityTargetPage() {
		ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
				.getBean("activityManager");
		TicketManager ticketManager = (TicketManager) ApplicationContextUtil
				.getBean("ticketManager");
		List<Ticket> tickets = ticketManager.getTicketsByMemberid(memberid);
		ArrayList<Ticket> ticketsWithQuestions = new ArrayList<Ticket>(10);
		// 筛选掉所有回答过问题的票以及所有还没看的票
		Timestamp timestamp = new Timestamp(new Date().getTime());
		for (Ticket ticket : tickets) {
			if (timestamp.after(ticket.getPlan().getEndtime())) {
				if (activityManager.getQtPidsbyTicket(ticket).size() > 0) {
					ticketsWithQuestions.add(ticket);
				}
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		ticketsWithQuestions.trimToSize();
		request.setAttribute("ticketsWithQuestions", ticketsWithQuestions);
		request.setAttribute("index", targetPage);
		return "success";
	}

	// 根据ticketID 获得能参加的QTP （没有去答过题）
	public String getQuestionByTicketid() {
		TicketManager ticketManager = (TicketManager) ApplicationContextUtil
				.getBean("ticketManager");
		ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
				.getBean("activityManager");
		Ticket ticket = ticketManager.getTicketByTicketid(ticketid);
		List<QuestiontoPlan> questiontoPlans = activityManager
				.getQtPidsbyTicket(ticket);

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("ticket", ticket);
		request.setAttribute("questiontoPlans", questiontoPlans);
		request.setAttribute("targetPage", targetPage);
		return "success";
	}

	// 用户提交回答
	public String memberSubbmitAnswers() {
		try {
			ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
					.getBean("activityManager");
			String[] answerArray = answers.split("\\|");
			for (int i = 0; i < answerArray.length; i++) {
				String[] detail = answerArray[i].split("-");
				int ticketid = Integer.parseInt(detail[0]);
				int qtpid = Integer.parseInt(detail[1]);
				activityManager.submmitReply(ticketid, qtpid, detail[2]);
			}
			inputStream = new StringBufferInputStream("Success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error Occurred");
			return "fail";
		}
	}

	// 得到创建活动的页面
	public String getCreateActivityPage() {
		// 得到所有能做活动的电影
		FilmManager filmManager = (FilmManager) ApplicationContextUtil
				.getBean("filmManager");
		List<Film> films = filmManager.getAvailableFilmsFordoPlan();

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("films", films);
		request.setAttribute("filmIndex", 1);
		return "success";
	}

	// 得到为指定filmid 创建活动的列表
	public String getCreateActivityForTargetFilmPage() {
		try {
			FilmManager filmManager = (FilmManager) ApplicationContextUtil
					.getBean("filmManager");
			Film film = filmManager.getFilmByFilmid(filmid);
			if (film == null) {
				inputStream = new StringBufferInputStream(
						"Error occurred in finding film");
				return "fail";
			}
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			List<Integer> plans = planManager.getPlansByFilmid(filmid);
			// 如果film 没有审核通过的放映计划 那么就不让做活动问题
			if (plans == null) {
				inputStream = new StringBufferInputStream(
						"Error occurred in finding film's plan");
				return "fail";
			}
			if (plans.size() == 0) {
				inputStream = new StringBufferInputStream("film has no plan");
				return "fail";
			}

			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("film", film);
			request.setAttribute("plans", plans);
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 服务员提交活动
	public String staffCreateActivity() {
		try {
			FilmManager filmManager = (FilmManager) ApplicationContextUtil
					.getBean("filmManager");
			Film film = filmManager.getFilmByFilmid(filmid);
			if (film == null) {
				inputStream = new StringBufferInputStream(
						"Error occurred in finding film");
				return "fail";
			}
			if (bindPlans == null || bindPlans.equals("")) {
				inputStream = new StringBufferInputStream(
						"activity must bind  plan");
				return "success";
			}
			ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
					.getBean("activityManager");
			int questionid = activityManager.insertActivity(content, options);
			String[] planidArray = bindPlans.split("-");
			for (String planidString : planidArray) {
				activityManager.bindPlan(questionid,
						Integer.parseInt(planidString));
			}
			QuestiontoPlan questiontoPlan = new QuestiontoPlan();
			inputStream = new StringBufferInputStream("success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}

	}

	// 得到待处理活动的页面
	public String getHandleActivityPage() {
		ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
				.getBean("activityManager");
		FilmManager filmManager = (FilmManager) ApplicationContextUtil
				.getBean("filmManager");
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Film> films = filmManager.getOffshelvesFilms();
		if (films == null) {
			inputStream = new StringBufferInputStream("Error in find films");
			return "fail";
		}
		// 筛选掉那些不存在plan或者不存在未处理的活动的film
		Film film;
		List<Integer> planids;
		List<Integer> unsolvedquestionids;
		for (int i = 0; i < films.size(); i++) {
			film = films.get(i);
			planids = planManager.getPlansByFilmid(film.getFilmid());
			if (planids == null || planids.size() == 0) {
				films.remove(i);
				i--;
				continue;
			}
			unsolvedquestionids = activityManager
					.getUnhandledQuestionidsByplanids(planids);
			if (unsolvedquestionids == null || unsolvedquestionids.size() == 0) {
				films.remove(i);
				i--;
				continue;
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("films", films);
		return "success";
	}

	// 得到指定电影的待处理活动列表
	public String getQuestionsofAFilmPage() {
		FilmManager filmManager = (FilmManager) ApplicationContextUtil
				.getBean("filmManager");
		Film film = filmManager.getFilmByFilmid(filmid);
		if (film == null) {
			inputStream = new StringBufferInputStream("error in find film");
			return "fail";
		}
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Integer> planids = planManager.getPlansByFilmid(filmid);
		if (planids == null) {
			inputStream = new StringBufferInputStream("error in find plan");
			return "fail";
		}
		ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
				.getBean("activityManager");
		List<Integer> questionids = activityManager
				.getUnhandledQuestionidsByplanids(planids);
		if (questionids == null) {
			inputStream = new StringBufferInputStream("error in find question");
			return "fail";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("questionids", questionids);
		request.setAttribute("film", film);
		return "success";
	}

	// 得到指定电影的指定问题的统计信息
	public String getStatisticofAQuestiononAFilm() {
		QuestionDao questionDao = (QuestionDao) ApplicationContextUtil
				.getBean("questionDaoImpl");
		Question question = questionDao.getQuestionById(questionid);
		if (question == null) {
			inputStream = new StringBufferInputStream("error in find quesiton");
			return "fail";
		}
		ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
				.getBean("activityManager");
		// 验证是否问题已经被处理
		if (!activityManager.isUnsolvedQuestiononAFilm(questionid, filmid)) {
			inputStream = new StringBufferInputStream(
					"question has been handled");
			return "fail";
		}

		Map<String, Integer> questiontoStatistic = activityManager
				.getQuestionStatisticOnaFilm(questionid, filmid);

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("question", question);
		request.setAttribute("questiontoStatistic", questiontoStatistic);
		request.setAttribute("filmid", filmid);
		return "success";
	}

	// 处理活动
	public String handleActivity() {
		try {
			ActivityManager activityManager = (ActivityManager) ApplicationContextUtil
					.getBean("activityManager");
			List<Member> members = activityManager.handleSatistic(questionid,
					filmid, selected);
			MemberManager memberManager = (MemberManager) ApplicationContextUtil
					.getBean("memberManager");
			for (Member member : members) {
				member.setIntegral(member.getIntegral() + bonus);
				memberManager.updateMemberInfo(member);
			}
			inputStream = new StringBufferInputStream("success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("error");
			e.printStackTrace();
			return "fail";
		}
	}

}
