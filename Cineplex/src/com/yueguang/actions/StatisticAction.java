package com.yueguang.actions;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.CinemaStatistic;
import com.yueguang.model.MemberStatistic;
import com.yueguang.service.StatisticManager;
import com.yueguang.util.ApplicationContextUtil;

//用来生成经理看的统计页
public class StatisticAction extends ActionSupport{

	private static final long serialVersionUID = -4342580564154073949L;

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getStatisticPage(){
		StatisticManager statisticManager =(StatisticManager)ApplicationContextUtil.getBean("statisticManager");
		MemberStatistic memberStatistic = statisticManager.getLastMonthMemberStatistic();
		if(memberStatistic == null){
			inputStream=new StringBufferInputStream("Error in find memberstatistic");
		}
		CinemaStatistic cinemaStatistic = statisticManager.getLastMonthCinameStatistic();
		if(cinemaStatistic == null){
			inputStream=new StringBufferInputStream("Error in find cinemastatistic");
		}
		HttpServletRequest request =ServletActionContext.getRequest();
		request.setAttribute("memberStatistic", memberStatistic);
		request.setAttribute("cinemaStatistic",cinemaStatistic);
		return "success";
	}
}
