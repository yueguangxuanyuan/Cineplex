package com.yueguang.dao;

import java.sql.Timestamp;
import java.util.List;

import com.yueguang.model.Film;

public interface FilmDao {
    public List<Film>  getAllFilms();
    
    public List<Film>  getFilmDuringCertainTime(Timestamp begintime,Timestamp endtime);
    
    public void insertFilm(Film  film);
    
    public void deleteFilmById(int filmid);
    
    public Film  getFilmById(int  filmid);
}
