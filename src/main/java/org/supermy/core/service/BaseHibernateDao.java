package org.supermy.core.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * hibernate 常用查询封装
 * 
 * @author james mo
 * 
 * @param <T>
 * @param <PK>
 */
@Transactional(value="hibernateTransactionManager")
public abstract class BaseHibernateDao<T, PK extends Serializable>{ //implements IBaseHibernateDao<T, PK> {
	@Deprecated
	protected Log log = LogFactory.getLog(getClass());
	protected  Logger logger = LoggerFactory
	.getLogger(getClass());
	
	private HibernateTemplate ht;
	
	public HibernateTemplate getHt(){
		return ht;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		logger.debug("hibernate init session factory ");
		ht = new HibernateTemplate(sessionFactory);
		ht.setCacheQueries(true);
//		ht.
	}

	public abstract Class getEntityClass();

	/**
	 * 统计数
	 * 
	 * @param queryString
	 * @return
	 */
	public long count(final String queryString) {
		return count(queryString, new Object[] {});
	}

	/**
	 * 统计数
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public long count(final String queryString, Object... values) {
		return DataAccessUtils.longResult(ht.find(queryString, values));
	}

	/**
	 * 根据主键ID查询
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public T getById(PK id) {
		return (T) ht.get(getEntityClass(), id);
	}

	public List<T> findById(PK[] ids) {
		StringBuffer hql=new StringBuffer();
		hql.append("from "+getEntityClass().getSimpleName()+" obj");
		hql.append(" where obj.pkId in  (");
		for (PK pk : ids) {
			hql.append( pk );
			hql.append( "," );
		}
		hql.delete(hql.length()-1, hql.length());
		hql.append("   )");
		logger.debug(hql.toString());
		//String hql1="from "+getEntityClass().getSimpleName()+" obj where obj.pkId in :ids" ;
		//ht.find(hql1, ids);
		
		return  ht.find(hql.toString());
	}
	/**
	 * 根据实体类查询，建议少量数据使用
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<T> findAll() {
		return ht.loadAll(getEntityClass());
	}

	/**
	 * 保存实体类
	 * 
	 * @param entity
	 */
	public T save(T entity) {
		ht.save(entity);
		return entity;
	}
	
	/**
	 * 将传入的detached(托管)状态的对象的属性复制到持久化对象中，并返回该持久化对象。 如果该session中没有关联的持久化对象，加载一个。
	 * 如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 * 
	 * @param entity
	 * @return
	 */
	public T merge(T entity) {
		return ht.merge(entity);
	}

//	/**
//	 * 保存实体类列表
//	 * 
//	 * @param domains
//	 * @return 
//	 */
//	public List<T> saveAll(Collection<T> domains) {
//		List<T> results = new ArrayList<T>();
//		//ht.saveOrUpdateAll(entities)
//		for (T t : domains) {
//			results.add(save(t));
//		}
//		return results;
//	}
//	
//	public List<T> saveAll(T[] domains) {
//		List<T> results = new ArrayList<T>();
//		for (T t : domains) {
//			results.add(save(t));
//		}
//		return results;
//	}

//	public List<T> updateAll(Collection<T> domains) {
//		List<T> results = new ArrayList<T>();
//		//ht.saveOrUpdateAll(entities)
//		for (T t : domains) {
//			results.add(update(t));
//		}
//		return results;
//	}
//	
//	public List<T> updateAll(T[] domains) {
//		List<T> results = new ArrayList<T>();
//		for (T t : domains) {
//			results.add(update(t));
//		}
//		return results;
//	}


	public List<T> saveOrUpdateAll(Collection<T> domains) {
		List<T> results = new ArrayList<T>();
		//ht.saveOrUpdateAll(entities)
		for (T t : domains) {
			results.add(saveOrUpdate(t));
		}
		return results;
	}
	
	public List<T> saveOrUpdateAll(T[] domains) {
		List<T> results = new ArrayList<T>();
		for (T t : domains) {
			results.add(saveOrUpdate(t));
		}
		return results;
	}

	/**
	 * 保存或者更新实体类
	 * 
	 * @param entity
	 * @return 
	 */
	public T saveOrUpdate(T entity) {
		ht.saveOrUpdate(entity);
		//ht.merge(entity)
		return entity;
	}

	/**
	 * 更新实体类
	 * 
	 * @param entity
	 * @return 
	 */
	public T update(T entity) {
		ht.update(entity);
		return entity;
	}

	/**
	 * 按ID删除实体类
	 * 
	 * @param id
	 */
	public void deleteById(PK id) {
		Object entity = getById(id);
		if (entity == null) {
			throw new ObjectRetrievalFailureException(getEntityClass(), id);
		}
		ht.delete(entity);
	}

	/**
	 * 删除实体类
	 * 
	 * @param entity
	 */
	public void delete(Object entity) {
		ht.delete(entity);
	}

	/**
	 * 删除实体类
	 * 
	 * @param entity
	 */
	public void delete(Serializable entity) {
		ht.delete(entity);
	}

	/**
	 * 删除实体类列表
	 * 
	 * @param entities
	 */
	//TODO　@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public void deleteAll(Collection<T> entities) {
//		logger.debug(">>>>>>>>>>delete all begin");
//		for (T t : entities) {
//			ht.delete(t);
//		}
		ht.deleteAll(entities);
//		logger.debug(">>>>>>>>>>delete all end");
	}

	
	/**
	 * @param entities
	 */
	//TODO　@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public void deleteAll(PK[] ids) {
		StringBuffer hql=new StringBuffer();
		hql.append("delete from "+getEntityClass().getSimpleName()+" obj");
		hql.append(" where obj.pkId in  (");
		for (PK pk : ids) {
			hql.append( pk );
			hql.append( "," );
		}
		hql.delete(hql.length()-1, hql.length());
		hql.append("   )");
		logger.debug(hql.toString());
		
		ht.bulkUpdate(hql.toString());
	}

	
	/**
	 * 刷新实体类
	 * 
	 * @param entity
	 */
	public void refresh(Object entity) {
		ht.refresh(entity);
	}

	/**
	 * 刷新当前实体类
	 */
	public void flush() {
		ht.flush();
	}

	public void evict(Object entity) {
		ht.evict(entity);
	}
	
	public void lock(Object entity,LockMode lockMode) {
		ht.lock(entity, lockMode);
	}

	/**
	 * 根据属性和值查找返回一个实体类
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public T findByProperty(final String propertyName, final Object value) {

		return (T) ht.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(getEntityClass())
						.add(Expression.eq(propertyName, value)).uniqueResult();
			}
		});
	}

	/**
	 * 根据属性和值查找返回所有实体类
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findAllByProperty(final String propertyName,
			final Object value) {
		return ht.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(getEntityClass())
						.add(Expression.eq(propertyName, value)).list();
			}
		});
	}

	/**
	 * 分页查询返回所有实体
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<T> findAllPage(final Page param) {
		return (Page<T>) ht.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				StringBuilder hqlSb = new StringBuilder(" FROM ")
						.append(getEntityClass().getSimpleName()).append(" o ORDER BY o.createDate desc");
				String countHql = "SELECT count(*) " + hqlSb.toString();
				Query query = session.createQuery(hqlSb.toString());
				Query countQuery = session.createQuery(countHql);// FIXME
				Page<T> executeQuery = executeQuery(query, countQuery, param.getPageNo(),
						param.getPageSize());
				return executeQuery;
			}
		});

	}

	/**
	 * 根据hql和page参数和条件值params，查询分页返回实体
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<T> find(final String hql, final Page param,
			final Object... params) {

		final String countHql = "SELECT count(*) "
				+ HqlUtils.removeSelect(HqlUtils.removeFetchKeyword((hql)));
		return pageQuery(hql, countHql, param, params);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> find(final String hql) {
		return ht.find(hql);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<String> find4Attr(final String hql) {
		return ht.find(hql);
	}
	@Transactional(readOnly=true)
	public List<Object> findByValueBean(final String hql,Object valueBean) {
		return ht.findByValueBean(hql, valueBean);
	}
	/**
	 * 
	 * @param hql
	 * @param countHql
	 * @param pageParam
	 * @param params
	 * @return
	 */
	private Page<T> pageQuery(final String hql, final String countHql,
			final Page pageParam, final Object... params) {
		return (Page<T>) ht.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(hql);
				if (params.length > 0) {
					setQueryParameters(query, params);
				}
				Query countQuery = session.createQuery(countHql);
				if (params.length > 0) {
					setQueryParameters(countQuery, params);
				}
				return executeQuery(query, countQuery, pageParam.getPageNo(),
						pageParam.getPageSize());
			}
		});
	}

	private Page<T> executeQuery(Query query, Query countQuery, int pageNo,
			int pageSize) {
		log.debug("execute query");
		Assert.isTrue(pageSize > 0, "pageSize > 0 must be true");
		Number totalCount = (Number) countQuery.uniqueResult();
		log.debug("execute query total count:"+totalCount);
		Page<T> page = new Page<T>(pageNo, pageSize, totalCount.intValue());

		if (totalCount != null && totalCount.intValue() > 0) {
			query.setFirstResult(page.getFirst());
			query.setMaxResults(page.getPageSize());
			log.debug("start rec:"+page.getFirst());
			log.debug("count rec:"+page.getPageSize());
			page.setResult(query.list());
		}
		return page;

	}

	/**
	 * 为hibernate 的Query设置Object参数
	 * 
	 * @param q
	 * @param params
	 * @return
	 */
	protected void setQueryParameters(Query q, Object params) {
		q.setProperties(params);
	}

	/**
	 * 为hibernate 的Query设置Map参数
	 * 
	 * @param q
	 * @param params
	 * @return
	 */
	protected void setQueryParameters(Query q, Map params) {
		q.setProperties(params);
	}

	/**
	 * cache 查询命中率
	 * 
	 * @param event
	 */
	public void queryByCacheStats() {
		Statistics stats = ht.getSessionFactory().getStatistics();
		long l2HitCount = stats.getSecondLevelCacheHitCount();
		long l2MissCount = stats.getSecondLevelCacheMissCount();
		long queryHitCount = stats.getQueryCacheHitCount();
		long queryMissCount = stats.getQueryCacheMissCount();
		log.debug("L2_Cache_Hit :{}" + l2HitCount);
		log.debug("L2_Cache_Miss :{}" + l2MissCount);
		double l2CacheHitRatio = l2HitCount
				/ (l2HitCount + l2MissCount + 0.000001);
		log.debug("L2_Cache_Hit_Ratio :{}" + l2CacheHitRatio);
		log.debug("");
		log.debug("Query_Cache_Hit :{}" + queryHitCount);
		log.debug("Query_Cache_Miss :{}" + queryMissCount);
	}

}
