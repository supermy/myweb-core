package com.supermy.rest.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.supermy.core.service.Page; 

import com.supermy.rest.domain.Contact;
import com.supermy.rest.service.ContactService;

/**
 * 联系人的增删改查；所有的数据都是以对象集的形式传递。
 * 
 * @author james mo
 * 
 */
@Controller
public class ContactController {
	private final Logger logger = LoggerFactory
			.getLogger(ContactController.class);

	@Autowired
	private ContactService cs;

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/contacts/start/{start}/limit/{limit}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public @ResponseBody
	List<Contact> getContacts(@PathVariable int start,
			@PathVariable int limit) {
		logger.info("get Contacts called");

		Page<Contact> result = cs.findAllPage(new Page<Contact>(start, limit));

		return result.getResult();
	}

	/**
	 * 显示
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contact/{id}")
	@Transactional(readOnly = true)
	public @ResponseBody
	Contact getContact(@PathVariable("id") Long id) {
		return cs.getById(id);
	}

	/**
	 * 新增
	 * 
	 * @param contact
	 * @return
	 */
	@RequestMapping(value = "/contact/add")
	@Transactional(readOnly = false)
	public @ResponseBody
	Contact addContact() {
		Contact contact = new Contact();
		return contact;
	}

	/**
	 * 创建
	 * 
	 * @param contact
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public @ResponseBody
	Contact createContact(@RequestBody Contact contact) {
		System.out.println("create Contact called"+contact);
		logger.info("create Contact called",contact);
		cs.saveOrUpdate(contact);
		return contact;
	}

	/**
	 * 编辑
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contact/{id}/edit")
	@Transactional(readOnly = true)
	public @ResponseBody
	Contact edit(@PathVariable("id") Long id) {
		return cs.getById(id);
	}

	/**
	 * 更新
	 * 
	 * @param contacts
	 * @return
	 */
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Contact updateContacts( @RequestBody Contact contact,@PathVariable Long id) {
		logger.info("updateContacts called");
		cs.saveOrUpdate(contact);
		return contact;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	boolean deleteContacts(@PathVariable Long id) {
		logger.info("delete Contact called" + id);
		cs.deleteById(id);
		return true;
	}

	/** 批量删除 */
	@RequestMapping(value = "/contacts/del",method = RequestMethod.PUT)
	public @ResponseBody
	boolean batchDelete(@RequestBody long[] items) {
		for (int i = 0; i < items.length; i++) {
			cs.deleteById(items[i]);
		}
		return true;
	}
	

}
