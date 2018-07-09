package com.emp.dao;

import com.emp.model.EmployeeBean;

public interface GeneralMongo {
public EmployeeBean getEmployee(String email);
public boolean setEmployee(EmployeeBean emp);
}
