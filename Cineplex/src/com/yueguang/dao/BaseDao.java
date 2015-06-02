package com.yueguang.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

public interface BaseDao {

	public Session getSession();

	public Session getNewSession();

	public void flush();

	public void clear();

	public Object load(Class c, Serializable id);

	public List getAllList(Class c);

	public Long getTotalCount(Class c);

	public void save(Object bean);

	public void update(Object bean);

	public void delete(Object bean);

	public void delete(Class c, Serializable id);

	public void delete(Class c, String[] ids);

	//查找对应属性列的值  返回值为List<List>形式  如果是有选择的拿的话
	public List find(Class c, String[] attributes);
    //查找  满足attribute=value条件组的值 
	public List find(Class c,HashMap<String,String> condition);
    //删除 满足 attribute=value条件组的值
	public void delete(Class c, HashMap<String,String> condition);
}
