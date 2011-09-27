package com.supermy.rest.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("hibernateTransactionManager")
public interface IParentService {

	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public abstract void deleteTest();

}