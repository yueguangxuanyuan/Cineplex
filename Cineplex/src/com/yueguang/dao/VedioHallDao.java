package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.VedioHall;

public interface VedioHallDao {
	public List<VedioHall> getVideoHalls();

	public VedioHall getVedioHallByName(String hallname);
}
