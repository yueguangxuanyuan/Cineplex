package com.yueguang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ListModel;

import com.yueguang.dao.MemberReplyDao;
import com.yueguang.dao.QuestionDao;
import com.yueguang.dao.QuestiontoPlanDao;
import com.yueguang.model.Member;
import com.yueguang.model.MemberReply;
import com.yueguang.model.Plan;
import com.yueguang.model.Question;
import com.yueguang.model.QuestiontoPlan;
import com.yueguang.model.Ticket;

public class ActivityManager {
	QuestionDao questionDao;
	QuestiontoPlanDao questiontoPlanDao;
	MemberReplyDao memberReplyDao;

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public void setQuestiontoPlanDao(QuestiontoPlanDao questiontoPlanDao) {
		this.questiontoPlanDao = questiontoPlanDao;
	}

	public void setMemberReplyDao(MemberReplyDao memberReplyDao) {
		this.memberReplyDao = memberReplyDao;
	}

	// 插入 新问题 并且返回问题id
	public int insertActivity(String content, String options) {
		try {
			Question question = new Question();
			question.setContent(content);
			question.setOptions(options);
			questionDao.insertQuestion(question);
			return question.getQuestionid();
		} catch (Exception e) {
			return -1;
		}
	}

	// 根据questionid 找到quesion
	public Question getQuestionByQuestionid(int questionid) {
		try {
			return questionDao.getQuestionById(questionid);
		} catch (Exception e) {
			return null;
		}
	}

	// 指定 活动与放映计划的关联
	public boolean bindPlan(int questionid, int planid) {
		try {
			QuestiontoPlan questiontoPlan = new QuestiontoPlan();
			Question question = new Question();
			question.setQuestionid(questionid);
			Plan plan = new Plan();
			plan.setPlanid(planid);

			questiontoPlan.setPlan(plan);
			questiontoPlan.setQuestion(question);
			// 默认状态为未统计 未处理状态
			questiontoPlanDao.inserQtP(questiontoPlan);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 根据questionid删除 所有相关的统计
	public boolean deleteActivityByQuestionid(int questionid) {
		try {
			List<QuestiontoPlan> questiontoPlans = questiontoPlanDao
					.getQtPbyQuestionid(questionid);
			for (QuestiontoPlan questiontoPlan : questiontoPlans) {
				memberReplyDao.deleteByQtPId(questiontoPlan.getQtpid());
				questiontoPlanDao.deleteQtP(questiontoPlan.getQtpid());
			}
			questionDao.deleteByQuestionid(questionid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 返回所有未处理问题的列表 根据传入的下架电影所有相关的planids (如果没做统计 那么就把统计也做了)
	public List<Integer> getUnhandledQuestionidsByplanids(List<Integer> planids) {
		Set<Integer> questiondidSet = new HashSet<Integer>();
		for (int planid : planids) {
			List<QuestiontoPlan> questiontoPlans = questiontoPlanDao
					.getQtPbyPlanid(planid);
			for (QuestiontoPlan questiontoPlan : questiontoPlans) {
				if (questiontoPlan.getStatus().equals("未处理")) {
					if (questiontoPlan.getStatistic().equals("未统计")) {
						doSatisticByQuestionid(questiontoPlan);
					}
					questiondidSet.add(questiontoPlan.getQuestion()
							.getQuestionid());
				}
			}
		}
		return new ArrayList<Integer>(questiondidSet);
	}

	// 针对一个 qtp做活动统计
	private void doSatisticByQuestionid(QuestiontoPlan questiontoPlan) {
		String[] choices = questiontoPlan.getQuestion().getOptions().split("-");
		Map<String, Integer> statistic = new TreeMap<String, Integer>();
		for (String s : choices) {
			statistic.put(s, 0);
		}

		List<MemberReply> memberReplies = memberReplyDao
				.getMemberReplysByQtPid(questiontoPlan.getQtpid());

		for (MemberReply memberReply : memberReplies) {
			int count = statistic.get(memberReply.getChoice());
			statistic.put(memberReply.getChoice(), count + 1);
		}
		StringBuilder bufferedstatistic = new StringBuilder();
		for (int i = 0; i < choices.length; i++) {
			if (i != 0) {
				bufferedstatistic.append("-");
			}
			bufferedstatistic.append(choices[i]);
			bufferedstatistic.append("-");
			bufferedstatistic.append(statistic.get(choices[i]));
		}

		questiontoPlan.setStatistic(bufferedstatistic.toString());
		questiontoPlanDao.updateQtP(questiontoPlan);
	}

	// 拿到关于一部电影特定问题的 QTP
	private List<QuestiontoPlan> getQuestiontoPlansOnCurrentFilmAndQuestion(
			int questionid, int filmid) {
		List<QuestiontoPlan> questiontoPlans = questiontoPlanDao
				.getQtPbyQuestionid(questionid);
		QuestiontoPlan tempQtP;
		// 筛选掉无关的QTP
		for (int i = 0; i < questiontoPlans.size(); i++) {
			tempQtP = questiontoPlans.get(i);
			if (tempQtP.getPlan().getFilm().getFilmid() != filmid) {
				questiontoPlans.remove(i);
				i--;
			}
		}
		return questiontoPlans;
	}

	// 验证关于一个film的问题活动是否已经被处理
	public boolean isUnsolvedQuestiononAFilm(int questionid, int filmid) {
		List<QuestiontoPlan> questiontoPlans = questiontoPlanDao
				.getQtPbyQuestionid(questionid);
		if (questiontoPlans == null || questiontoPlans.size() == 0) {
			return false;
		} else {
			for (QuestiontoPlan questiontoPlan : questiontoPlans) {
				if (questiontoPlan.getPlan().getFilm().getFilmid() == filmid
						&& questiontoPlan.getStatus().equals("未处理")) {
					return true;
				}
			}

		}
		return false;
	}

	// 拿到针对一个电影的 某个问题的统计
	public Map<String, Integer> getQuestionStatisticOnaFilm(int questionid,
			int filmid) {
		List<QuestiontoPlan> questiontoPlans = getQuestiontoPlansOnCurrentFilmAndQuestion(
				questionid, filmid);
		// 拿到键值
		QuestiontoPlan tempQtP = questiontoPlans.get(0);
		Map<String, Integer> statistic = new TreeMap<String, Integer>();
		String[] choices = tempQtP.getQuestion().getOptions().split("-");
		for (int i = 0; i < choices.length; i++) {
			statistic.put(choices[i], 0);
		}
		// 统计
		for (QuestiontoPlan questiontoPlan : questiontoPlans) {
			choices = questiontoPlan.getStatistic().split("-");
			for (int i = 0; i < choices.length; i = i + 2) {
				String selected = choices[i];
				int count = statistic.get(selected);
				count = count + Integer.parseInt(choices[i + 1]);
				statistic.put(selected, count);
			}
		}
		return statistic;
	}

	// 对统计做出处理 选出满足条件需要送积分的member 并且把qtp标注为已处理的状态
	public List<Member> handleSatistic(int questionid, int filmid, String option) {
		List<QuestiontoPlan> questiontoPlans = getQuestiontoPlansOnCurrentFilmAndQuestion(
				questionid, filmid);
		List<Member> members = new ArrayList<Member>(100);
		for (QuestiontoPlan questiontoPlan : questiontoPlans) {
			List<MemberReply> memberReplies = memberReplyDao
					.getMemberReplysByQtPid(questiontoPlan.getQtpid());
			for (MemberReply memberReply : memberReplies) {
				if (memberReply.getChoice().equals(option)) {
					members.add(memberReply.getTicket().getMember());
				}
			}
			questiontoPlan.setStatus("已处理");
			questiontoPlanDao.updateQtP(questiontoPlan);
		}
		return members;
	}

	// 根据ticket找到用户所有未回答参与的活动的qtp（要求买了票 但是没有参加回答 ）
	public List<QuestiontoPlan> getQtPidsbyTicket(Ticket ticket) {
		ArrayList<QuestiontoPlan> questiontoPlans = new ArrayList<QuestiontoPlan>(
				20);
		int planid = ticket.getPlan().getPlanid();
		List<QuestiontoPlan> qtpList = questiontoPlanDao.getQtPbyPlanid(planid);
		// 筛选掉所用回答过问题的QTP
		for (QuestiontoPlan questiontoPlan : qtpList) {
			List<MemberReply> memberReplies = memberReplyDao
					.getMemberReplysByQtPid(questiontoPlan.getQtpid());
			Boolean isReplied = false;
			for (MemberReply memberReply : memberReplies) {
				if (memberReply.getTicket().getTicketid() == ticket
						.getTicketid()) {
					isReplied = true;
					break;
				}
			}
			if (!isReplied) {
				questiontoPlans.add(questiontoPlan);
			}
		}
		questiontoPlans.trimToSize();
		return questiontoPlans;
	}

	// 用户参与活动
	public boolean submmitReply(int ticketid, int qtpid, String option) {
		try {
			MemberReply memberReply = new MemberReply();
			Ticket ticket = new Ticket();
			ticket.setTicketid(ticketid);
			QuestiontoPlan questiontoPlan = new QuestiontoPlan();
			questiontoPlan.setQtpid(qtpid);
			memberReply.setQuestiontoPlan(questiontoPlan);
			memberReply.setTicket(ticket);
			memberReply.setChoice(option);
			memberReplyDao.insertMemberReply(memberReply);
			return true;
		} catch (Exception e) {

			return false;
		}
	}
}
