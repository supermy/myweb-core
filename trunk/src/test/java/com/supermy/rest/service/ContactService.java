package com.supermy.rest.service;

import org.springframework.stereotype.Repository;
import org.supermy.core.service.BaseHibernateDao;

import com.supermy.rest.domain.Contact;

@Repository
public class ContactService extends BaseHibernateDao<Contact, Long> {
	@Override
	public Class getEntityClass() {
		return Contact.class;
	}

 

}
