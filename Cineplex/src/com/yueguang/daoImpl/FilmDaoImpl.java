package com.yueguang.daoImpl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.FilmDao;
import com.yueguang.model.Film;

public class FilmDaoImpl implements FilmDao {
	BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Film> getAllFilms() {
		return baseDao.getAllList(Film.class);
	}

	public List<Film> getFilmDuringCertainTime(Timestamp begintime,
			Timestamp endtime) {
		Session session = baseDao.getNewSession();
		String hql="from Film where ontime>'"+begintime+"' and offtime<'"+endtime+"'";
		List<Film>   films  = session.createQuery(hql).list();
		session.close();
		return films;
	}

	public void insertFilm(Film film) {
        baseDao.save(film);
	}

	public void deleteFilmById(int filmid) {
        baseDao.delete(Film.class, filmid);
	}

	public Film getFilmById(int filmid) {
		// TODO Auto-generated method stub
		return (Film) baseDao.load(Film.class, filmid);
	}

}
