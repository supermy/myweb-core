package com.supermy.rest.service;

import org.springframework.stereotype.Repository;
import org.supermy.core.service.BaseHibernateDao;

import com.supermy.rest.domain.Contact;

/**
 * Contact Service
 * 
 * @author james mo
 * @version 创建时间：2011-9-11 上午09:48:50
 *  
 */
@Repository
public class ContactService extends BaseHibernateDao<Contact, Long> {
	@Override
	public Class getEntityClass() {
		return Contact.class;
	}

 

}
