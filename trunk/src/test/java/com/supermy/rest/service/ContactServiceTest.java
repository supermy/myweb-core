package com.supermy.rest.service;

import static junit.framework.Assert.assertEquals;

import java.util.Date;
import java.util.List;

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
public class ContactServiceTest  {
	private final Logger logger = LoggerFactory
			.getLogger(ContactServiceTest.class);
 
	
	@Autowired
	private ContactService userService;

	private Page<Contact> page = new Page<Contact>(1,3);

	/** 
	 * 增删改查测试
	 */
	@Test
	public void crudContact() {
		long count = userService.count("select count(*) from Contact");
		logger.debug("count:{}", count);
		
		List<Contact> findAll = userService.findAll();
		
		Contact c = new Contact();
		c.setName("superadmin");
		c.setEmail("test@core.com");
		c.setBirthDate(new Date());
		c.setPhone("110119");
		userService.saveOrUpdate(c);
		long count2 = userService.count("select count(*) from Contact");
		assertEquals(count2, count+1);

		logger.debug("count:{}", count);
		userService.delete(c);
		long count3 = userService.count("select count(*) from Contact");
		logger.debug("count:{}", count);
		assertEquals(count3, count);
	}
}
