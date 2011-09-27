package com.supermy.rest.service;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.supermy.rest.domain.Child;
import com.supermy.rest.domain.Parent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/business.xml" })
@TransactionConfiguration(transactionManager = "hibernateTransactionManager", defaultRollback = false)
public class ParentServiceTest  {
	private static final Logger logger = LoggerFactory
			.getLogger(ParentServiceTest.class);

	@Autowired
	private ParentService parentService;
	
	
	@Autowired
	private ChildService childService;
	
	private Parent p = new Parent();
	private Child c1 = new Child();
	private Child c2 = new Child();
	private Child c3 = new Child();
	
	@Before
	public void init(){
		logger.debug(">>>>>>>>>>>>>>before");
		
		p.setName("superadmin");

		c1 = new Child();
		c1.setName("child1");
		c1.setParent(p);
		c2 = new Child();
		c2.setName("child2");
		c2.setParent(p);
		c3 = new Child();
		c3.setName("child3");
		c3.setParent(p);

		Set<Child> childs = new HashSet<Child>();
		childs.add(c1);
		childs.add(c2);
		childs.add(c3);
		Assert.assertEquals(childs.size(), 3);
		p.setChilds(childs);

		parentService.saveOrUpdate(p);
		
	}
	
	@After
	public void destory(){
		logger.debug(">>>>>>>>>>>>>>after");
		parentService.delete(p);
	}
	

	/** 
	 * 增删改查测试
	 */
	@Test
	public void crudParent() {
		Parent c = new Parent();
		c.setName("superadmin");
		parentService.saveOrUpdate(c);
		c.setName("update");
		parentService.saveOrUpdate(c);
		Parent p =parentService.getById(c.getPkId());
		Assert.assertEquals(p.getName(), "update");
		logger.debug(">>>>>>>>>>>>>>parent id:{}",c.getPkId());
		parentService.deleteById(c.getPkId());
	}
	
	@Test
	public void findByIds() {
		Long[] ids={c1.getPkId(),c2.getPkId()};
		parentService.findById(ids);
		
	}
	
	@Test
	@ExpectedException(AuthenticationCredentialsNotFoundException.class)
	public void aclaopmeth() {
		parentService.deleteTest();
		
	}	
	
	@Test
	@Transactional
	public void parentAddChild() {
		logger.debug("开始增加子对象测试");
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>增加一个元素child pkid:{}");

		Child c4 = new Child();
		c4.setName("child4");
		c4.setParent(p);

		p.getChilds().add(c4);

		boolean remove = p.getChilds().remove(c1);
		Assert.assertEquals(remove, true);

		parentService.saveOrUpdate(p);
		Parent p1 = parentService.getById(p.getPkId());
		Assert.assertEquals(p1.getChilds().size(), 3);
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>删除");
	}
	
	@Test
	public void listmodify() {
		logger.debug("开始增加子对象测试");
		Set<String> objs=new HashSet<String>();
		objs.add("1");
		objs.add("2");
		objs.add("3");
		org.junit.Assert.assertEquals(objs.size(), 3);		
		objs.remove("1");
		org.junit.Assert.assertEquals(objs.size(), 2);
	}

	
}
