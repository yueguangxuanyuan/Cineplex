package com.yueguang.daoImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.yueguang.dao.BaseDao;

public class BaseDaoImpl implements BaseDao {
	/** * Autowired 自动装配 相当于get() set() */
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/** * gerCurrentSession 会自动关闭session，使用的是当前的session事务 * * @return */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** * openSession 需要手动关闭session 意思是打开一个新的session * * @return */
	public Session getNewSession() {
		return sessionFactory.openSession();
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/** * 根据 id 查询信息 * * @param id * @return */
	@SuppressWarnings("rawtypes")
	public Object load(Class c, Serializable id) {
		Session session = getNewSession();
		Object reval = session.get(c, id);
		session.close();
		return reval;
	}

	/** * 获取所有信息 * * @param c * * @return */

	public List getAllList(Class c) {
		String hql = "from " + c.getName();
		Session session = getNewSession();
		List reval = session.createQuery(hql).list();
		session.close();
		return reval;

	}

	/** * 获取总数量 * * @param c * @return */

	public Long getTotalCount(Class c) {
		Session session = getNewSession();
		String hql = "select count(*) from " + c.getName();
		Long count = (Long) session.createQuery(hql).uniqueResult();
		session.close();
		return count != null ? count.longValue() : 0;
	}

	/** * 保存 * * @param bean * */
	public void save(Object bean) {
		try {
			Session session = getNewSession();
			session.save(bean);
			session.flush();
			session.clear();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** * 更新 * * @param bean * */
	public void update(Object bean) {
		Session session = getNewSession();
		session.update(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/** * 删除 * * @param bean * */
	public void delete(Object bean) {

		Session session = getNewSession();
		session.delete(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/** * 根据ID删除 * * @param c 类 * * @param id ID * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, Serializable id) {
		Session session = getNewSession();
		Object obj = session.get(c, id);
		session.delete(obj);
		session.flush();
		session.clear();
		session.close();
	}

	/** * 批量删除 * * @param c 类 * * @param ids ID 集合 * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String[] ids) {
		for (String id : ids) {
			Object obj = getSession().get(c, id);
			if (obj != null) {
				getSession().delete(obj);
			}
		}
	}

	public List find(Class c, String[] attributes) {
		Session session = getNewSession();
		StringBuilder hql = new StringBuilder("select ");

		hql.append(attributes[0]);
		for (int i = 1; i < attributes.length; i++) {
			hql.append("," + attributes[i]);
		}
		hql.append(" from " + c.getName());
		List reval = session.createQuery(hql.toString()).list();
		session.close();
		return reval;
	}

	public List find(Class c, HashMap<String, String> condition) {
		Session session = getNewSession();
		StringBuilder hql = new StringBuilder("from " + c.getName() + " where ");

		boolean isfirst = true;
		for (String attribute : condition.keySet()) {
			if (isfirst) {
				isfirst = false;
			} else {
				hql.append(" and ");
			}

			hql.append(attribute + "='" + condition.get(attribute) + "'");
		}

		List reval = session.createQuery(hql.toString()).list();
		session.close();
		return reval;
	}

	public void delete(Class c, HashMap<String, String> condition) {
		// TODO Auto-generated method stub
		Session session = getNewSession();
		StringBuilder hql = new StringBuilder("delete  from " + c.getName()
				+ " where ");

		boolean isfirst = true;
		for (String attribute : condition.keySet()) {
			if (isfirst) {
				isfirst = false;
			} else {
				hql.append(" and ");
			}
			hql.append(attribute + "='" + condition.get(attribute) + "'");
		}
		
		session.createQuery(hql.toString()).executeUpdate();
		session.close();
	}
}
