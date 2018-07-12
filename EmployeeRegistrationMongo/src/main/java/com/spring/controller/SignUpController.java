package com.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.model.EmployeeBean;
import com.spring.service.EmpServiceImpl;
import com.spring.token.JwtTokenBuilder;

/**
 * purpose:Controller class to access login and logout
 * 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@RestController
@RequestMapping("/EmployeeRegistrationController")
public class SignUpController {

	public static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	@Autowired
	EmpServiceImpl empService = new EmpServiceImpl();

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<String> signup(@RequestBody EmployeeBean eb) {
		logger.info("Register user : {}", eb);

		boolean isRegistered = empService.signup(eb);
		if (!isRegistered) {
			logger.error("User with email {} already present.", eb.getEmail());
			return new ResponseEntity<String>("Employee with email " + eb.getEmail() + " is already present",
					HttpStatus.CONFLICT);
		} else {
			logger.info("Employee registered with : {}", eb.getEmail());
			String message = "Sign Up Successful";
			JwtTokenBuilder jwt = new JwtTokenBuilder();
			String currentJwt = jwt.createJWT(eb);
			EmpServiceImpl.sendActivationLink(currentJwt, eb);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
	}

	/**
	 * This method is written to login in system
	 * @param eb
	 * @return response entity
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody EmployeeBean eb) {
		EmployeeBean emp = empService.login(eb.getEmail(), eb.getPassword());
		if (emp == null) {
			logger.error("Employee with email {} not found", eb.getEmail());

			return new ResponseEntity<String>("Employee with email " + eb.getEmail() + " notfound",
					HttpStatus.NOT_FOUND);
		} else {
			JwtTokenBuilder jwt = new JwtTokenBuilder();
			String currentJwt = jwt.createJWT(emp);
			jwt.parseJWT(currentJwt);
			String message = "Hello, " + emp.getUserName() + " Id:- " + emp.getId() + " Email:- " + emp.getEmail()
					+ " Phone Number:- " + emp.getMobile() + "YOUR JWT TOKEN IS:" + currentJwt;
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
	}

	/**
	 * this method is written to make account activated after successful sign in
	 * @param hsr 
	 * @return response entity
	 */
	@RequestMapping("/activateaccount")
	public ResponseEntity<String> activateaccount(HttpServletRequest hsr) {
		System.out.println(hsr.getQueryString());
		String token = hsr.getQueryString();

		if (empService.activate(token)) {
			String messege = "Account activated successfully";
			return new ResponseEntity<String>(messege, HttpStatus.OK);
		} else {
			String msg = "Account not activated";
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * This method is written to get forgotten password
	 * @param eb
	 * @return response entity
	 */
	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgetPassword(@RequestBody EmployeeBean eb) {

		if (empService.forgotPassword(eb.getEmail())) {
			logger.info("Password sent to email");

			return new ResponseEntity<String>("Password sent to email", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Password not sent", HttpStatus.FORBIDDEN);
		}
	}
}
