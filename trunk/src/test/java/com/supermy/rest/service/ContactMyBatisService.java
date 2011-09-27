package com.supermy.rest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.supermy.rest.domain.Contact;

/**
 * 
 * @author james mo
 * @version 创建时间：2011-9-13 下午10:21:31
 *  
 */
@Repository
@Transactional(value="myBatisTransactionManager")
public class ContactMyBatisService{
	private final Logger logger = LoggerFactory.getLogger(ContactMyBatisService.class);
 
	
	@Autowired
	private com.supermy.rest.mapper.ContactMapper contactMapper;

	public List<Contact> findByName(String userName) {
	    return contactMapper.findByName(userName);
	}

	public Contact getContact(long id) {
	    return contactMapper.getContact(id);
	}

	public Contact addContact(Contact contact) {
		
	     Integer addContact = contactMapper.addContact(contact);
		    logger.debug("add Contact amount:{}",addContact);
		    logger.debug("add Contact id:{}",contact.getPkId());
	    
		return contact;
	}

	public Contact updateContact(Contact contact) {
	    Integer updateContact = contactMapper.updateContact(contact);
	    logger.debug("update Contact amount:{}",updateContact);
	    logger.debug("update Contact id:{}",contact.getPkId());
	    
		return contact;
	}

	public void delContact(long id) {
	    Integer delContact = contactMapper.delContact(id);
	    logger.debug("delete Contact amount:{}",delContact);
	}

	public void delContacts(long[] ids) {
	    Integer delContacts = contactMapper.delContacts(ids);		
	    logger.debug("delete Contacts amount:{}",delContacts);
	}

}
