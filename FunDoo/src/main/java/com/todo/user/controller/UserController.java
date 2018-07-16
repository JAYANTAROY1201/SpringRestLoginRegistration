package com.todo.user.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.user.exception.AccountActivationException;
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
	UserServiceImpl userService= new UserServiceImpl();
	
	/**
	 * Method to control signup service
	 * @param eb
	 * @return
	 * @throws SignupException
	 * @throws MessagingException 
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@RequestBody User eb) throws SignupException, MessagingException
	{
		userService.doSignUp(eb);
		logger.info("Employee registered with : {}", eb.getEmail());
		String message = "Sign Up Successful";
		JwtTokenBuilder jwt = new JwtTokenBuilder();
		String currentJwt = jwt.createJWT(eb);
		userService.sendActivationLink(currentJwt, eb);
		logger.info("Activation link sent to email");
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	

	
	/**
	 * @param emp
	 * @return
	 * @throws LoginException
	 */
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public ResponseEntity<String> logIn(@RequestParam String email,@RequestParam String password) throws LoginException {
	userService.doLogIn(email, password);
		
	return new ResponseEntity<String>("login successful:\n", HttpStatus.OK);
	}

	
	/**
	 * this method is written to make account activated after successful sign in
	 * @param hsr 
	 * @return response entity
	 * @throws AccountActivationException 
	 */
	@RequestMapping(value="/activateaccount",method = RequestMethod.GET)
	public ResponseEntity<String> activateAccount(HttpServletRequest hsr) throws AccountActivationException {
		String token = hsr.getQueryString();
	        userService.doActivateEmail(token) ;
			String message = "Account activated successfully";
			logger.info(message);
			return new ResponseEntity<String>(message, HttpStatus.OK);	
	}

	/**
	 * This method is written to get forgotten password
	 * @param eb
	 * @return response entity
	 * @throws LoginException 
	 * @throws MessagingException 
	 */
	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgetPassword(@RequestParam String email) throws LoginException, MessagingException {

		    userService.doSendNewPasswordLink(email);
			logger.info("New password reset link sent to email");
			return new ResponseEntity<String>("Password sent to email", HttpStatus.OK);	
	}
	
	/**
	 * This method is written to get forgotten password
	 * @param eb
	 * @return response entity
	 * @throws LoginException 
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<String> resetPassword(@RequestBody User user,HttpServletRequest hsr)
	
	{
		    String jwtToken=hsr.getQueryString();
		    userService.doResetPassword(jwtToken, user.getPassword());
			logger.info("New password set successfully");
			return new ResponseEntity<String>("New password set successfully", HttpStatus.OK);	
	}
	
}
