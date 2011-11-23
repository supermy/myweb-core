package com.supermy.rest.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.supermy.core.util.SecurityUtils;

import com.supermy.rest.domain.Child;

import freemarker.template.utility.SecurityUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/business.xml" })
@TransactionConfiguration(transactionManager = "hibernateTransactionManager", defaultRollback = false)
public class ChildServiceTest  {
	private final Logger logger = LoggerFactory
			.getLogger(ChildServiceTest.class);
 
	

	@Autowired
	private ChildService childService;
	
	List<Child> result=new ArrayList<Child>();
	
	@Before
	public void init(){
		
		//TODO　Security 不在此项目中；
		//SecurityUtils.setSimpleAuthToken("test", "test");
		//SecurityUtils.getSimplePrincipal();
		
		Child c = new Child();
		c.setName("superadmin");
		childService.saveOrUpdate(c);
		c.setName("update");
		childService.saveOrUpdate(c);
		Child p =childService.getById(c.getPkId());
		Assert.assertEquals(p.getName(), "update");
		logger.debug(">>>>>>>>>>>>>>parent id:{}",c.getPkId());
//		childService.deleteById(c.getPkId());
		result.add(c);
//		childService.delete(c);
		
	}
	@After
	public void destory(){
		//TODO　Security 不在此项目中；
		//SecurityUtils.setSimpleAuthToken("test", "test");
		
		childService.deleteAll(result);
		
	}

	/** 
	 * 增删改查测试
	 */
	@Test
	public void crudParent() {
		
	}
	

	
	
}
