package com.todo.user.controller;

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

import com.todo.user.exception.LoginException;
import com.todo.user.exception.SignupException;
import com.todo.user.model.User;
import com.todo.user.services.UserServiceImpl;
import com.todo.user.utility.JwtTokenBuilder;

@RestController
@RequestMapping("/fundoo")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServiceImpl empService= new UserServiceImpl();
	
	/**
	 * @param eb
	 * @return
	 * @throws SignupException
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@RequestBody User eb) throws SignupException
	{
		empService.signUp(eb);
		logger.info("Employee registered with : {}", eb.getEmail());
		String message = "Sign Up Successful";
		JwtTokenBuilder jwt = new JwtTokenBuilder();
		String currentJwt = jwt.createJWT(eb);
		UserServiceImpl.sendActivationLink(currentJwt, eb);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	


	
	/**
	 * @param emp
	 * @return
	 * @throws LoginException
	 */
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public ResponseEntity<String> logIn(@RequestBody User emp) throws LoginException {
	empService.logIn(emp.getEmail(), emp.getPassword());
	JwtTokenBuilder jwt = new JwtTokenBuilder();
	String currentJwt = jwt.createJWT(emp);
	jwt.parseJWT(currentJwt);
	String message = "Hello, " + emp.getUserName() + " Id:- " + emp.get_id() + " Email:- " + emp.getEmail()
			+ " Phone Number:- " + emp.getMobile() + "YOUR JWT TOKEN IS:" + currentJwt;
	return new ResponseEntity<String>(message, HttpStatus.OK);
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
			String message = "Account activated successfully";
			return new ResponseEntity<String>(message, HttpStatus.OK);
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
	public ResponseEntity<String> forgetPassword(@RequestBody User eb) {

		if (empService.forgotPassword(eb.getEmail())) {
			logger.info("Password sent to email");

			return new ResponseEntity<String>("Password sent to email", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Password not sent", HttpStatus.FORBIDDEN);
		}
	}
}
