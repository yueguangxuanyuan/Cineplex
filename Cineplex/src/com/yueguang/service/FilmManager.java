package com.yueguang.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.yueguang.dao.FilmDao;
import com.yueguang.model.Film;

public class FilmManager {
	private FilmDao filmDao;

	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	// 返回可以做放映计划的电影 (电影未下架)
	public List<Film> getAvailableFilmsFordoPlan() {
		try {
			List<Film> films = filmDao.getAllFilms();
			if (films != null) {
				Timestamp now = new Timestamp(new Date().getTime());
				Film tempFilm;
				for (int i = 0; i < films.size(); i++) {
					tempFilm = films.get(i);
					if (tempFilm.getOfftime().compareTo(now) < 0) {
						films.remove(i);
						i--;
					}
				}

				return films;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	// 返回需要做统计的电影 (电影已下架)
	public List<Film> getOffshelvesFilms() {
		try {
			List<Film> films = filmDao.getAllFilms();
			if (films != null) {
				Timestamp now = new Timestamp(new Date().getTime());
				Film tempFilm;
				for (int i = 0; i < films.size(); i++) {
					tempFilm = films.get(i);
					if (tempFilm.getOfftime().compareTo(now) > 0) {
						films.remove(i);
						i--;
					}
				}

				return films;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}
	
	//根据filmid得到film
    public Film getFilmByFilmid(int filmid){
    	return filmDao.getFilmById(filmid);
    }
}
