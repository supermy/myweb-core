package com.supermy.rest.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.supermy.core.service.Page;
import org.supermy.core.web.ExtData;
import org.supermy.core.web.ExtResponse;

import com.supermy.rest.domain.Contact;
import com.supermy.rest.service.ContactService;

/**
 * 联系人的增删改查；所有的数据都是以对象集的形式传递。
 * @author james mo
 * 
 */
@Controller
@RequestMapping("/extjscontacts")
public class ContactExtjsController {
	private final Logger logger = LoggerFactory
			.getLogger(ContactExtjsController.class);

	@Autowired
	private ContactService cs;

	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ExtData<Contact> getContacts( Integer start, Integer limit) {
		logger.debug("getContacts called start:{} litmit:{}",start,limit);

		ExtData<Contact> data = new ExtData<Contact>();
		
		Page<Contact> result = cs.findAllPage(new Page<Contact>(start, limit));
		data.add(result.getResult());
		data.setTotal(result.getTotalCount());//设置记录总数
		data.setSuccess(true);

		return data;
	}

	/**
	 * 增加
	 * 
	 * @param contacts
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ExtData<Contact> addContacts(@RequestBody Contact[] contacts) {
		logger.info("addContacts called");
		ExtData<Contact> data = new ExtData<Contact>();
		List<Contact> results =cs.saveOrUpdateAll(contacts);
		data.add(results);
		data.setSuccess(true);
		return data;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody
	ExtResponse deleteContacts(@RequestBody Long[] ids) {
		logger.info("deleteContacts called"+ids);
		cs.deleteAll(ids);
		return ExtResponse.SUCCESS;
	}

	/**
	 * 更新
	 * 
	 * @param contacts
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	ExtData<Contact> updateContacts(@RequestBody Contact[] contacts) {
		logger.info("updateContacts called");

		ExtData<Contact> data = new ExtData<Contact>();
		List<Contact> items = cs.saveOrUpdateAll(contacts);
		data.add(items);
		data.setSuccess(true);

		return data;
	}
	
	
	///////////////////////////////以下方法是resttemplate 测试使用
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "/start/{start}/limit/{limit}",method = RequestMethod.GET)
	public @ResponseBody
	ExtData<Contact>  getAll(@PathVariable Integer start,@PathVariable Integer limit) {
		logger.debug("getContacts called start:{} litmit:{}",start,limit);

		ExtData<Contact> data = new ExtData<Contact>();
		
		Page<Contact> result = cs.findAllPage(new Page<Contact>(start, limit));
		data.add(result.getResult());
		data.setTotal(result.getTotalCount());//设置记录总数
		data.setSuccess(true);

		return data;
	}
	
	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del",method = RequestMethod.PUT)
	public @ResponseBody
	ExtResponse batchdelete(@RequestBody Long[] ids) {
		logger.info("deleteContacts called"+ids);
		cs.deleteAll(ids);
		return ExtResponse.SUCCESS;
	}
	

}
