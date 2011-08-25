package com.supermy.rest.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.supermy.core.service.Page;

import com.supermy.rest.domain.Contact;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/servlet-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ContactMyBatisServiceTest  {
	private final Logger logger = LoggerFactory.getLogger(ContactMyBatisServiceTest.class);

	@Autowired
	private ContactMyBatisService cs;

	private Page<Contact> page = new Page<Contact>(1,3);

	/**
	 * 增删改查测试
	 */
	@Test
	public void crudContact() {
		
		Contact c = new Contact();
		c.setName("superadmin");
		c.setEmail("test@core.com");
		c.setBirthDate(new Date());
		c.setPhone("110119");
		c.setCreateBy("test");
		c.setUpdateBy("test");
		
		 cs.addContact(c);
		Long id=c.getPkId();
		Assert.assertNotNull(id);
		Contact contact=cs.getContact(id);
		
		Assert.assertNotNull(contact);
		
		contact.setName("update");
		cs.updateContact(contact);
		
		Contact contact2 = cs.getContact(id);
		Assert.assertEquals(contact2.getName(), "update");
		
		cs.delContact(contact2.getPkId());

		c.setPkId(null);
		cs.addContact(c);
		c.setPkId(null);
		cs.addContact(c);
		
		List<Contact> result = cs.findByName("super%");
		logger.debug("result:{}",result.size());
		logger.debug("result:{}",result.get(0));
		
		c.setPkId(null);
		cs.addContact(c);
		long[] ids={c.getPkId()};
//		java.util.List list = Arrays.asList(ids); 
//		logger.debug("result:{}",list.get(0));
		cs.delContacts(ids);
		
	}
}
