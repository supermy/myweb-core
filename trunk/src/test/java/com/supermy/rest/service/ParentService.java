package com.supermy.rest.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.service.BaseHibernateDao;

import com.supermy.rest.domain.Parent;

@Repository
@Transactional("hibernateTransactionManager")
public class ParentService extends BaseHibernateDao<Parent, Long>{
	@Override
	public Class getEntityClass() {
		return Parent.class;
	}
	
	/* (non-Javadoc)
	 * @see com.supermy.rest.service.IParentService#deleteTest()
	 */
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public void deleteTest(){
		throw new RuntimeException("方法没有拦截，出错！");
		
	}


}
