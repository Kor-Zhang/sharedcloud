package com.sharedcloud.pc.dao.basedao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


/**
 * 大众化dao操作接口
 * @author Kor_Zhang
 *
 * @param <T>
 */
public interface BaseDaoI<T>{
	
	/**
	 * 通过class和对象id获取数据库数据
	 * @param clazz
	 * @param key
	 * @return
	 */
	public T get(Class<?> clazz, String key);
	/**
	 * 通过携带参数的hql语句获取数据库的一条记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public T getByHql(String hql,Map<String,Object> parameters);
	/**
	 * 通过携带参数的sql语句获取数据库的一条记录
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public T getBySql(String sql,Map<String,Object> parameters);
	/**
	 * 将一个对象持久化
	 * @param model
	 * @return
	 */
	public Serializable save(T model);
	/**
	 * 将一个持久化对象更新
	 * @param model
	 * @return
	 */
	public void saveOrUpdate(T model);
	/**
	 * 删除一个持久化的对象（pojo中需要有该对象的key）
	 * @param model
	 */
	public void delete(T model);
	/**
	 * 携带参数的hql多行分页查询
	 * @param queryHql
	 * @param parameters
	 * @param page：当前页
	 * @param rows：每页行数
	 * @return
	 */
	public List<T> queryByHql(String hql,Map<String,Object> parameters,Integer page,Integer rows);
	/**
	 * 携带参数的sql多行分页查询
	 * @param sqlQuery
	 * @param parameters
	 * @param page：当前页
	 * @param rows：每页行数
	 * @return
	 */
	public List<T> queryBySql(String sql,Map<String,Object> parameters,Integer page,Integer rows);
	/**
	 * 执行携带参数的sql语句 
	 * @param sql
	 * @param parameters
	 */
	public void executeSql(String sql,Map<String,Object> parameters);
	/**
	 * 执行携带参数的hql语句 
	 * @param queryHql
	 * @param parameters
	 */
	public void executeHql(String queryHql,Map<String,Object> parameters);
}
