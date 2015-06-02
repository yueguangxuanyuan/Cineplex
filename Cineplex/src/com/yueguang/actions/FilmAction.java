package com.yueguang.actions;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yueguang.model.Film;
import com.yueguang.service.FilmManager;
import com.yueguang.util.ApplicationContextUtil;

public class FilmAction extends ActionSupport{

	private static final long serialVersionUID = 3778529484465948886L;
    private int targetPage;
	InputStream inputStream;
	
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

	//得到指定的候选电影页面
	public String getTargetOnShelfFilmPage(){
		FilmManager filmManager = (FilmManager) ApplicationContextUtil.getBean("filmManager");
		List<Film> films = filmManager.getAvailableFilmsFordoPlan();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("films", films);
		request.setAttribute("filmIndex", targetPage);
		return "success";
	}
	
	//得到能做活动的电影候选页面
	public String getTargetOnShelfFilmPageForActivity(){
		FilmManager filmManager = (FilmManager) ApplicationContextUtil.getBean("filmManager");
		List<Film> films = filmManager.getAvailableFilmsFordoPlan();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("films", films);
		request.setAttribute("filmIndex", targetPage);
		return "success";
	}
}
