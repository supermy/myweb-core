package org.supermy.core.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * 
 * <pre>
1. 查询数据
 List result =getSqlMapClientTemplate().queryForList("TestSpace.qryTestByParam", objA);
  如果需要取4~40条数据： 
  List result = getSqlMapClientTemplate().queryForList("TestSpace.qryTestByParam", objA, 4, 40); 
  也可以返回
  Map Map result =  getSqlMapClientTemplate().queryForMap("TestSpace.qryTestByParam", objA, "MapKey"); 
   
2、使用SqlMapClientTemplate添加数据
 Java代码：
     ObjectA objA = new ObjectA();
     objA.setParam1("test1");
     objA.setParam2("test2");
     ......
     getSqlMapClientTemplate().insert("TestSpace.insertTest", objA);

 3、使用SqlMapClientTemplate更新数据
 Java代码：
     ObjectA objA = new ObjectA();
     objA.setParam1("test1");
     objA.setParam2("test2");
     ......
     getSqlMapClientTemplate().update("TestSpace.updateTest", objA);
     更新前20条记录：
     getSqlMapClientTemplate().update("TestSpace.updateTest", objA, 20);

 4、使用SqlMapClientTemplate删除数据
 Java代码：
     Long id = new Long("2");
     getSqlMapClientTemplate().delete("TestSpace.deleteTest", id); 
      
 * </pre>
 * @author james mo
 * 
 * @param <E>
 * @param <PK>
 */
public abstract class BaseIbatisDao<E, PK extends Serializable> {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public Object getById(PK primaryKey) {
		Object object = sqlMapClientTemplate.queryForObject(
				getFindByPrimaryKeyStatement(), primaryKey);
		return object;
	}

	public void deleteById(PK id) {
		int affectCount = sqlMapClientTemplate.delete(getDeleteStatement(), id);
	}

	public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		sqlMapClientTemplate.insert(getInsertStatement(), entity);
	}

	public void update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = sqlMapClientTemplate.update(getUpdateStatement(),
				entity);
	}

	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * 
	 * @param o
	 */
	protected void prepareObjectForSaveOrUpdate(E o) {
	}

	public String getFindByPrimaryKeyStatement() {
		return getIbatisSqlMapNamespace() + ".getById";
	}

	public String getInsertStatement() {
		return getIbatisSqlMapNamespace() + ".insert";
	}

	public String getUpdateStatement() {
		return getIbatisSqlMapNamespace() + ".update";
	}

	public String getDeleteStatement() {
		return getIbatisSqlMapNamespace() + ".delete";
	}

	public String getCountStatementForPaging(String statementName) {
		return statementName + ".count";
	}

	public String getIbatisSqlMapNamespace() {
		throw new RuntimeException("not yet implement");
	}

	@SuppressWarnings("unchecked")
	protected Page pageQuery(String statementName, Page page,Object... params) {
		return pageQuery(sqlMapClientTemplate, statementName,
				getCountStatementForPaging(statementName), page,params);
	}

	@SuppressWarnings("unchecked")
	public static Page pageQuery(SqlMapClientTemplate sqlMapClientTemplate,
			String statementName, String countStatementName, Page page,Object... params) {

		Number totalCount = (Number) sqlMapClientTemplate.queryForObject(
				countStatementName, page);
		if (totalCount == null || totalCount.longValue() <= 0) {
			return new Page(0);
		}
		
		page.setTotalCount(totalCount.intValue());

		// 其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用.
		// 与sqlMapClientTemplate.queryForList(statementName,
		// parameterObject)配合使用
		Map otherFilters = new HashMap();
		otherFilters.put("offset", page.getFirst());
		otherFilters.put("pageSize", page.getPageSize());
		otherFilters.put("lastRows", page.getFirst() + page.getPageSize());

		// 混合两个filters为一个filters,MapAndObject.get()方法将在两个对象取值,Map如果取值为null,则再在Bean中取值
		Map parameterObject = new MapAndObject(otherFilters, params);
		List list = sqlMapClientTemplate.queryForList(statementName,
				parameterObject, page.getFirst(), page.getPageSize());
		page.setResult(list);
		return page;
	}

	public List findAll() {
		throw new UnsupportedOperationException();
	}

	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}

	public void flush() {
		// ignore
	}
	
	public List find(String qryTestByParam,Map objA) {
		 List result =sqlMapClientTemplate.queryForList(qryTestByParam, objA);
		 return result;
	}
	
	public List find(String qryTestByParam,Map objA,int start,int limit) {
		 List result =sqlMapClientTemplate.queryForList(qryTestByParam, objA,start,limit);
		 return result;
	}
	
	public Map find4Map(String qryTestByParam,Map objA) {
		 Map result =sqlMapClientTemplate.queryForMap(qryTestByParam, objA,  "MapKey");
		 return result;
	}
	
}
