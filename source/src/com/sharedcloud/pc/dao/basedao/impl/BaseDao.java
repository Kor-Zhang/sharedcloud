package com.sharedcloud.pc.dao.basedao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sharedcloud.pc.dao.basedao.BaseDaoI;

/**
 * 实现大众化的dao操作
 * 
 * @author Kor_Zhang
 * 
 * @param <T>
 */
public class BaseDao<T> implements BaseDaoI<T> {
	/**
	 * spring-hibernate.xml配置了sessionFactory的bean，自动装配方式为默认（byName），
	 * 必须为装配过程提供setter
	 */
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取当前session
	 */
	private Session getCurrtSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 通过class和对象id获取数据库数据
	 */
	public T get(Class<?> clazz, String key) {

		return (T) getCurrtSession().get(clazz, key);

	}

	/**
	 * 将一个对象持久化
	 * 
	 * @param model
	 * @return
	 */
	public Serializable save(T model) {

		return getCurrtSession().save(model);

	}

	/**
	 * 删除一个持久化的对象（pojo中需要有该对象的key）
	 * 
	 * @param model
	 * @return
	 */
	public void delete(T model) {

		getCurrtSession().delete(model);

	}

	/**
	 * 携带参数的hql多行分页查询
	 * 
	 * @throws Exception
	 */
	public List<T> queryByHql(String hql, Map<String, Object> parameters,
			Integer page, Integer rows) {
		Query query = getCurrtSession().createQuery(hql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		//<=0代表查询全部
		if(page>=1){
			// 计算分页
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();

	}

	/**
	 * 携带参数的sql多行分页查询
	 * 
	 * @param sqlQuery
	 * @param parameters
	 * @return
	 */
	public List<T> queryBySql(String sql, Map<String, Object> parameters,
			Integer page, Integer rows) {

		Query query = getCurrtSession().createSQLQuery(sql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		//<=0代表查询全部
		if(page>=1){
			// 计算分页
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();

	}

	/**
	 * 执行携带参数的sql语句
	 */
	public void executeSql(String sql, Map<String, Object> parameters) {

		SQLQuery query = getCurrtSession().createSQLQuery(sql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.executeUpdate();

	}

	/**
	 * 执行携带参数的hql语句
	 */
	public void executeHql(String queryHql, Map<String, Object> parameters) {

		Query query = getCurrtSession().createQuery(queryHql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.executeUpdate();

	}

	/**
	 * 通过携带参数的hql语句获取数据库的一条记录
	 */
	public T getByHql(String hql, Map<String, Object> parameters) {

		Query query = getCurrtSession().createQuery(hql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return (T) query.setMaxResults(1).uniqueResult();

	}

	/**
	 * 通过携带参数的sql语句获取数据库的一条记录
	 */
	public T getBySql(String sql, Map<String, Object> parameters) {

		Query query = getCurrtSession().createQuery(sql);
		// 设置参数
		if (null != parameters && parameters.size() >= 1) {
			Set<Entry<String, Object>> entrys = parameters.entrySet();
			for (Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return (T) query.setMaxResults(1).uniqueResult();

	}

	/**
	 * 将一个持久化对象更新
	 * @param model
	 * @return
	 */
	public void saveOrUpdate(T model) {
		getCurrtSession().saveOrUpdate(model);
	}
}
