package com.emp.service;

import com.emp.dao.GeneralMongo;
import com.emp.dao.MongoDBImplementation;
import com.emp.model.EmployeeBean;

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
		GeneralMongo mng = new MongoDBImplementation();
		EmployeeBean emp = mng.getEmployee(email);
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
		GeneralMongo mongo = new MongoDBImplementation();
		EmployeeBean isEmployee = mongo.getEmployee(emp.getEmail());
		if (isEmployee == null) {
			mongo.setEmployee(emp);
			return true;
		} else {
			return false;
		}
	}
}
