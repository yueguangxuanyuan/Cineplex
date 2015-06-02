package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.dao.FilmDao;
import com.yueguang.dao.VedioHallDao;
import com.yueguang.model.Film;
import com.yueguang.model.Plan;
import com.yueguang.model.Staff;
import com.yueguang.model.VedioHall;
import com.yueguang.service.FilmManager;
import com.yueguang.service.PlanManager;
import com.yueguang.util.ApplicationContextUtil;

public class PlanAction extends ActionSupport {

	private static final long serialVersionUID = 6001375118288350630L;
	private int filmid;
	private int planid;
	private String staffid;
	private String starttime;
	private String endtime;
	private float price;
	private String hallname;
	private int targetPage;
    private String verifyDecision;
    
	public String getVerifyDecision() {
		return verifyDecision;
	}

	public void setVerifyDecision(String verifyDecision) {
		this.verifyDecision = verifyDecision;
	}

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public int getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(int targetPage) {
		this.targetPage = targetPage;
	}

	InputStream inputStream;

	public String getHallname() {
		return hallname;
	}

	public void setHallname(String hallname) {
		this.hallname = hallname;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getFilmid() {
		return filmid;
	}

	public void setFilmid(int filmid) {
		this.filmid = filmid;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	// 得到制定计划的页面
	public String getCreatePlanPage() {
		FilmManager filmManager = (FilmManager) ApplicationContextUtil
				.getBean("filmManager");
		List<Film> films = filmManager.getAvailableFilmsFordoPlan();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("films", films);
		request.setAttribute("filmIndex", 1);
		return "success";
	}

	// 得到对影片进行计划的页面
	public String getDoFilmPlanPage() {
		FilmDao filmDao = (FilmDao) ApplicationContextUtil
				.getBean("filmDaoImpl");
		Film film = filmDao.getFilmById(filmid);
		if (film == null) {
			inputStream = new StringBufferInputStream("Film Inexist");
			return "fail";
		}
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if (timestamp.after(film.getOfftime())) {
			inputStream = new StringBufferInputStream("Film already offshelf");
			return "fail";
		}
		VedioHallDao vedioHallDao = (VedioHallDao) ApplicationContextUtil
				.getBean("vedioHallDaoImpl");
		List<VedioHall> vedioHalls = vedioHallDao.getVideoHalls();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("film", film);
		request.setAttribute("vedioHalls", vedioHalls);
		return "success";
	}

	// 进行计划提交
	public String subbmitPlan() {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			Date date1 = new Date();
			Date date2 = new Date();
			date1 = simpleDateFormat.parse(starttime);
			date2 = simpleDateFormat.parse(endtime);
			Timestamp starttimestamp = new Timestamp(date1.getTime());
			Timestamp endtimestamp = new Timestamp(date2.getTime());
			// 检查计划制定的时间是否与不在电影上架的时间段
			Date now = new Date();
			Timestamp nowtime = new Timestamp(now.getTime());
			if (now.after(starttimestamp)) {
				inputStream = new StringBufferInputStream(
						"cannot create plans before today");
				return "fail";
			}
			FilmDao filmDao = (FilmDao) ApplicationContextUtil
					.getBean("filmDaoImpl");
			Film film = filmDao.getFilmById(filmid);

			if (endtimestamp.after(film.getOfftime())) {
				inputStream = new StringBufferInputStream(
						"cannot create plans after film's offline");
				return "fail";
			}

			Staff staff = new Staff();
			staff.setStaffid(staffid);
			VedioHall vedioHall = new VedioHall();
			vedioHall.setHallname(hallname);

			Plan plan = new Plan();
			plan.setFilm(film);
			plan.setStaff(staff);
			plan.setPrice(price);
			plan.setStatus("审核中");
			plan.setStarttime(starttimestamp);
			plan.setEndtime(endtimestamp);
			plan.setVedioHall(vedioHall);

			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			planManager.summitPlan(plan);
			inputStream = new StringBufferInputStream("Success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 得到修改放映计划的页面
	public String getModifyPlanPage() {
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Plan> plans = planManager.getModifiablePlansByStaffid(staffid);
		if (plans == null) {
			inputStream = new StringBufferInputStream("No Available Plans");
			return "fail";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("planIndex", 1);
		return "success";
	}

	// 得到放映计划指定页实现分页
	public String getTargetModifiablePlanPage() {
		PlanManager planManager = (PlanManager) ApplicationContextUtil
				.getBean("planManager");
		List<Plan> plans = planManager.getModifiablePlansByStaffid(staffid);
		if (plans == null) {
			inputStream = new StringBufferInputStream("No Available Plans");
			return "fail";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("planIndex", targetPage);
		return "success";
	}
    
	//获取对指定planid 修改的界面
	public String getModifyTargetPlanPage(){
		try{
			PlanManager planManager = (PlanManager)ApplicationContextUtil.getBean("planManager");
			Plan plan = planManager.getModifiedPlanById(planid);
			if(plan==null){
				inputStream = new StringBufferInputStream("Error occurred in finding plan");
				return "fail";
			}
			Timestamp now = new Timestamp(new Date().getTime());
			if(now.after(plan.getFilm().getOfftime())){
				inputStream = new StringBufferInputStream("Film off shelf");
				return "fail";
			}
			VedioHallDao vedioHallDao = (VedioHallDao) ApplicationContextUtil.getBean("vedioHallDaoImpl");
			List<VedioHall> vedioHalls = vedioHallDao.getVideoHalls();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("plan", plan);
			request.setAttribute("vedioHalls", vedioHalls);
			return "success";
		}catch(Exception e){
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}
	
	// 进行放映计划的修改
	public String modifyPlan() {
		try {
			PlanManager planManager = (PlanManager) ApplicationContextUtil
			.getBean("planManager");
			Plan plan = planManager.getPlanById(planid);
			if(plan==null){
				inputStream = new StringBufferInputStream("Error occurred in finding plan");
				return "fail";
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			Date date1 = new Date();
			Date date2 = new Date();
			date1 = simpleDateFormat.parse(starttime);
			date2 = simpleDateFormat.parse(endtime);
			Timestamp starttimestamp = new Timestamp(date1.getTime());
			Timestamp endtimestamp = new Timestamp(date2.getTime());
			// 检查计划制定的时间是否与不在电影上架的时间段
			Date now = new Date();
			Timestamp nowtime = new Timestamp(now.getTime());
			if (nowtime.after(starttimestamp)) {
				inputStream = new StringBufferInputStream(
						"cannot create plans before today");
				return "fail";
			}
			if (endtimestamp.after(plan.getFilm().getOfftime())) {
				inputStream = new StringBufferInputStream(
						"cannot create plans after film's offline");
				return "fail";
			}

			VedioHall vedioHall = new VedioHall();
			vedioHall.setHallname(hallname);
			plan.setPrice(price);
			plan.setStatus("审核中");
			plan.setStarttime(starttimestamp);
			plan.setEndtime(endtimestamp);
			plan.setVedioHall(vedioHall);
			planManager.modifyPlan(plan);
			inputStream = new StringBufferInputStream("Success");
			return "success";
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}

	// 进行放映计划的删除
	public String deletePlan() {
		try {
			PlanManager planManager = (PlanManager) ApplicationContextUtil
					.getBean("planManager");
			if (planManager.deletePlan(planid)) {

				inputStream = new StringBufferInputStream("delete plan success");
				return "success";
			} else {
				inputStream = new StringBufferInputStream(
						"Error occurred in deleting plan");
				return "fail";
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("Error");
			return "fail";
		}
	}
	
	//得到审批放映计划的页面
	public String  getVerifyPlanPage(){
		PlanManager planManager = (PlanManager) ApplicationContextUtil.getBean("planManager");
		List<Plan> plans= planManager.getPlanforJudging();
		if(plans==null){
			inputStream = new StringBufferInputStream("Error in find availabl plan");
			return "fail";
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("planIndex", 1);
		return "success";
	}
	
	//得到指定的可审批的放映计划的页面   实现的方面比较广
	public  String getTargetVerifiablePlanPage(){
		PlanManager planManager = (PlanManager) ApplicationContextUtil.getBean("planManager");
		List<Plan> plans= planManager.getPlanforJudging();
		if(plans==null){
			inputStream = new StringBufferInputStream("Error in find availabl plan");
			return "fail";
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("plans", plans);
		request.setAttribute("planIndex", targetPage);
		return "success";
	}
	//提交审批结果
    public String subbmitVerifyPlanResult(){
    	try{
    		PlanManager planManager =(PlanManager) ApplicationContextUtil.getBean("planManager");
    		Plan plan = planManager.getPlanById(planid);
    		if(plan == null){
    			inputStream=new StringBufferInputStream("error occurred in find plan");
            	return "fail";
    		}
    		plan.setStatus(verifyDecision);
    		planManager.modifyPlan(plan);
    		inputStream=new StringBufferInputStream("success");
        	return "success";
    	}catch(Exception e){
    		inputStream=new StringBufferInputStream("error");
        	return "fail";
    	}
    	
    }
	
    
}
