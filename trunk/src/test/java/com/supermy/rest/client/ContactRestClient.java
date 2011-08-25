package com.supermy.rest.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.supermy.rest.domain.Contact;

 /** 标准的rest方法列表
 * 
 * <pre>
 * /contact                => index()  
 * /contact/add            => add()  
 * /contact/{id}           => show()  
 * /contact/{id}/edit      => edit()  
 * /contact        POST    => create()  
 * /contact/{id}   PUT     => update()  
 * /contact/{id}   DELETE  => delete()
 * 
 * /contacts       POST    => batchCreate()  
 * /contacts       PUT     => batchUpdate()  
 * /contacts/del   PUT     => batchDelete()
 * </pre>
 * 
 * @author james mo rest 客户端
 */
@Component
public class ContactRestClient {

	@Autowired
	protected RestTemplate restTemplate;

	private static final String BASE_URL = "http://localhost:8080/myweb-rest-extjs/contact";
	private static final String BASE_URLS = "http://localhost:8080/myweb-rest-extjs/contacts";

	@SuppressWarnings("unchecked")
	public List<Contact> getContacts(int start, int limit) {
		return restTemplate.getForObject(BASE_URLS
				+ "/start/{start}/limit/{limit}", List.class, start, limit);
	}

	public Contact getContact(long id) {
		return restTemplate.getForObject(BASE_URL + "/{id}", Contact.class, id);
	}

	public Contact addContact() {
		return restTemplate.postForObject(BASE_URL + "/add", null,
				Contact.class);
	}

	public Contact createContact(Contact contact) {
		System.out.println(contact);
		return restTemplate.postForObject(BASE_URL, contact, Contact.class);
	}
	

	public Contact editContact(long id) {
		return restTemplate.getForObject(BASE_URL + "/{id}/edit",
				Contact.class, id);
	}

	public Long updateContact(long id, Contact contact) {
		restTemplate.put(BASE_URL + "/{id}", contact,id);
		return contact.getPkId();
	}

	@SuppressWarnings("unchecked")
	public void delContacts(long id) {
		restTemplate.delete(BASE_URL + "/{id}", id);
	}
	


	@SuppressWarnings("unchecked")
	public void batchDeleteContacts(long[] ids) {		
		restTemplate.put(BASE_URLS+"/del", ids);
	}

}
