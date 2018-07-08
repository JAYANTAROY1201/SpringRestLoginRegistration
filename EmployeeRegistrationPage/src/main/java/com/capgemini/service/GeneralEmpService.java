package com.capgemini.service;

import com.capgemini.model.EmployeeBean;

/**
 * Purpose:General service of employee
 * @author Jayanta
 * 
 */
public interface GeneralEmpService {
	/**
	 * @param email
	 * @param password
	 * @return
	 */
	public EmployeeBean login(String email, String password);

	/**
	 * @param emp
	 * @return
	 */
	public boolean signup(EmployeeBean emp);

}
