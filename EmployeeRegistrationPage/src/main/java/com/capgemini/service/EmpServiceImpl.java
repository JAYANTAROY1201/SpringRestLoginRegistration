package com.capgemini.service;

import com.capgemini.dto.GeneralJdbc;
import com.capgemini.dto.JdbcImplementation;
import com.capgemini.model.EmployeeBean;

/**
 * @author Jayanta
 *
 */
public class EmpServiceImpl implements GeneralEmpService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.GeneralEmpService#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public EmployeeBean login(String email, String password) {
		GeneralJdbc jdbc = new JdbcImplementation();
		EmployeeBean emp = jdbc.getEmployee(email);
		if (emp != null) {
			if (emp.getPassword().equals(password)) {
				return emp;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.GeneralEmpService#signup(com.capgemini.model.
	 * EmployeeBean)
	 */
	@Override
	public boolean signup(EmployeeBean emp) {
		GeneralJdbc jdbc = new JdbcImplementation();
		EmployeeBean isEmployee = jdbc.getEmployee(emp.getEmail());
		if (isEmployee == null) {
			jdbc.setEmployee(emp);
			return true;
		} else {
			return false;
		}
	}
}
