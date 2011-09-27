package com.supermy.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.supermy.core.web.ExtData;

import com.supermy.rest.domain.Contact;

 /** 标准的rest方法列表
 * 
 * <pre>
 * /extjscontacts       GET     => index()  
 * /extjscontacts       POST    => batchCreate()  
 * /extjscontacts       PUT     => batchUpdate()  
 * /extjscontacts/del   PUT     => batchDelete()
 * 
 * </pre>
 * 
 * @author james mo rest 客户端
 */
@Component
public class ContactExtjsRestClient {

	@Autowired
	protected RestTemplate restTemplate;

	private static final String BASE_URLS = "http://localhost:8080/myweb-rest-extjs/myrest/extjscontacts";

	@SuppressWarnings("unchecked")
	public ExtData<Contact> getContacts(Integer start, Integer limit) {
		return restTemplate.getForObject(BASE_URLS
				+ "/start/{start}/limit/{limit}",  ExtData.class, start, limit);
	}

	/**
	 * 批量创建
	 * @param contact
	 * @return
	 */
	public ExtData<Contact> createContacts(Contact[] contact) {
		return restTemplate.postForObject(BASE_URLS, contact, ExtData.class);
	}
	

	/**
	 * 批量更新
	 * @param contact
	 * @return
	 */
	public Contact[] updateContacts( Contact[] contact) {
		restTemplate.put(BASE_URLS , contact);
		return contact;
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	public void deleteContacts(Long[] ids) {		
		restTemplate.put(BASE_URLS+"/del", ids);
	}

}
