package com.supermy.rest.mapper;

import java.util.List;

import com.supermy.rest.domain.Contact;

/**
 * Mybatis映射接口文件
 * @author james mo
 *
 */
public interface ContactMapper {

	List<Contact> findByName(String username);
	Contact getContact(long id);
	Integer addContact(Contact obj);
	Integer updateContact(Contact obj);
	Integer delContact(long id);
	Integer delContacts(long[] id);

}
