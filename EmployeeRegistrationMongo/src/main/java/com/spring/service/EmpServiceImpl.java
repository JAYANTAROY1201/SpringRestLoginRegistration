package com.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.model.EmployeeBean;
import com.spring.dao.GeneralMongoRepository;

/**
 * purpose: Class to apply logic of Sign up and login
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@Service
public class EmpServiceImpl {
	@Autowired
	private GeneralMongoRepository gm;

	/**
	 * This method is add functionality for sign up
	 * @param emp
	 * @return true if sign up successful else false
	 */
	public boolean signup(EmployeeBean emp) {

		System.out.println("entered");
		Optional<EmployeeBean> is = gm.findById(emp.getEmail());
		System.out.println("this 5464f6dgf46ds5gf74 ");
		System.out.println(is.isPresent());
		if (is.isPresent() == false) {
			System.out.println("true");
			gm.save(emp);
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}

	/**
	 * This method is add functionality for login
	 * @param email
	 * @param password
	 * @return
	 */
	public EmployeeBean login(String email, String password) {
		Optional<EmployeeBean> isEmail = gm.findById(email);
		EmployeeBean eb = new EmployeeBean();
		if (isEmail.isPresent()) {
			System.out.println("email is present");
			if (isEmail.get().getPassword().equals(password)) {
				System.out.println("email password true");
				eb = isEmail.get();
			} else {
				eb = null;

			}
		} else {
			eb = null;
		}
		return eb;

	}
}
