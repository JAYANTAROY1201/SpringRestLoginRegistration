package com.capgemini.dto;

import com.capgemini.model.EmployeeBean;

public interface GeneralJdbc {
public EmployeeBean getEmployee(String email);
public boolean setEmployee(EmployeeBean emp);
}
