package com.supermy.rest.service;

import org.springframework.stereotype.Repository;
import org.supermy.core.service.BaseHibernateDao;

import com.supermy.rest.domain.Child;

@Repository
//@Transactional("hibernateTransactionManager")
public class ChildService extends BaseHibernateDao<Child, Long> {
	@Override
	public Class getEntityClass() {
		return Child.class;
	}


}
