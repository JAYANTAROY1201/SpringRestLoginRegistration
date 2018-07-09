package com.emp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emp.model.EmployeeBean;
import com.emp.service.EmpServiceImpl;
import com.emp.service.GeneralEmpService;

@RestController
@RequestMapping("/EmployeeRegistrationController")
public class EmployeeRegistrationController {
	@RequestMapping(value="/success")
	public String success()
	{
		return "Successful";
	}
	public static final Logger logger = LoggerFactory.getLogger(EmployeeRegistrationController.class);

	GeneralEmpService empService = new EmpServiceImpl();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody EmployeeBean eb) {
		logger.info("login as: {}", eb);
		EmployeeBean emp = empService.login(eb.getEmail(), eb.getPassword());
		if (emp == null) {
			logger.error("Employee with email {} not found", eb.getEmail());
			
			return new ResponseEntity<String>("Employee with email " + eb.getEmail() + " notfound",
					HttpStatus.NOT_FOUND);
		}
		String message = "Hello, " + emp.getUserName() + " Id:- " + emp.getId() + " Email:- " + emp.getEmail()
				+ " Phone Number:- " + emp.getMobile();
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
		
	
	//______________________________________________________________
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<String> signup(@RequestBody EmployeeBean eb){
		logger.info("Register user : {}", eb);
		
		boolean isRegistered = empService.signup(eb);
		if(!isRegistered) {
			logger.error("User with email {} already present.", eb.getEmail());
			return new ResponseEntity<String>("Employee with email " + eb.getEmail() + " is already present",
					HttpStatus.CONFLICT);
		}
		else
		{
		logger.info("Employee registered with : {}", eb.getEmail());
		String message = "Sign Up Successful";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
}
