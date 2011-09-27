package org.supermy.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public interface IBaseHibernateDao<T, PK extends Serializable> {

	public abstract HibernateTemplate getHt();

	@Autowired
	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract Class getEntityClass();

	/**
	 * 统计数
	 * 
	 * @param queryString
	 * @return
	 */
	public abstract long count(final String queryString);

	/**
	 * 统计数
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public abstract long count(final String queryString, Object... values);

	/**
	 * 根据主键ID查询
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public abstract T getById(PK id);

	public abstract List<T> findById(PK[] ids);

	/**
	 * 根据实体类查询，建议少量数据使用
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public abstract List<T> findAll();

	/**
	 * 保存实体类
	 * 
	 * @param entity
	 */
	public abstract T save(T entity);

	/**
	 * 将传入的detached(托管)状态的对象的属性复制到持久化对象中，并返回该持久化对象。 如果该session中没有关联的持久化对象，加载一个。
	 * 如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 * 
	 * @param entity
	 * @return
	 */
	public abstract T merge(T entity);

	public abstract List<T> saveOrUpdateAll(Collection<T> domains);

	public abstract List<T> saveOrUpdateAll(T[] domains);

	/**
	 * 保存或者更新实体类
	 * 
	 * @param entity
	 * @return 
	 */
	public abstract T saveOrUpdate(T entity);

	/**
	 * 更新实体类
	 * 
	 * @param entity
	 * @return 
	 */
	public abstract T update(T entity);

	/**
	 * 按ID删除实体类
	 * 
	 * @param id
	 */
	public abstract void deleteById(PK id);

	/**
	 * 删除实体类
	 * 
	 * @param entity
	 */
	public abstract void delete(Object entity);

	/**
	 * 删除实体类
	 * 
	 * @param entity
	 */
	public abstract void delete(Serializable entity);

	/**
	 * 删除实体类列表
	 * 
	 * @param entities
	 */
	public abstract void deleteAll(Collection<T> entities);

	/**
	 * @param entities
	 */
	public abstract void deleteAll(PK[] ids);

	/**
	 * 刷新实体类
	 * 
	 * @param entity
	 */
	public abstract void refresh(Object entity);

	/**
	 * 刷新当前实体类
	 */
	public abstract void flush();

	public abstract void evict(Object entity);

	public abstract void lock(Object entity, LockMode lockMode);

	/**
	 * 根据属性和值查找返回一个实体类
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public abstract T findByProperty(final String propertyName,
			final Object value);

	/**
	 * 根据属性和值查找返回所有实体类
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public abstract List<T> findAllByProperty(final String propertyName,
			final Object value);

	/**
	 * 分页查询返回所有实体
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true)
	public abstract Page<T> findAllPage(final Page param);

	/**
	 * 根据hql和page参数和条件值params，查询分页返回实体
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true)
	public abstract Page<T> find(final String hql, final Page param,
			final Object... params);

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public abstract List<T> find(final String hql);

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public abstract List<String> find4Attr(final String hql);

	@Transactional(readOnly = true)
	public abstract List<Object> findByValueBean(final String hql,
			Object valueBean);

	/**
	 * cache 查询命中率
	 * 
	 * @param event
	 */
	public abstract void queryByCacheStats();

}