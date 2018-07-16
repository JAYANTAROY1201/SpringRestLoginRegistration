package com.todo.user.services;

import java.util.Optional;
import javax.mail.MessagingException;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.user.dao.GeneralMongoRepository;
import com.todo.user.dao.MailService;
import com.todo.user.exception.AccountActivationException;
import com.todo.user.exception.LoginException;
import com.todo.user.exception.SignupException;
import com.todo.user.model.Mail;
import com.todo.user.model.User;
import com.todo.user.utility.JwtTokenBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * purpose: Implementation of user service
 * 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@Service
public class UserServiceImpl {
	@Autowired
	private GeneralMongoRepository gm;
	@Autowired
	PasswordEncoder passwordencoder;
	@Autowired
	Mail mail;
	@Autowired
	MailService mailService;

	/**
	 * This method is add functionality for sign up
	 * 
	 * @param emp
	 * @return true if sign up successful else false
	 * @throws SignupException
	 */
	public void doSignUp(User emp) throws SignupException {
		if (emp.getEmail().equals("")) {
			throw new SignupException("Email is null");
		} else {
			Optional<User> user = gm.findByEmail(emp.getEmail());
			if (user.isPresent()) {
				throw new SignupException("Email already exist");
			} else {

				emp.setPassword(passwordencoder.encode(emp.getPassword()));

				gm.save(emp);
			}
		}
	}

	/**
	 * This method is add functionality for login
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws LoginException
	 */
	public void doLogIn(String email, String password) throws LoginException {
		if (email.equals("")) {
			throw new LoginException("Email can't be null");
		}
		if (password.equals("")) {
			throw new LoginException("Password cannot be blank");
		}
		if (gm.findByEmail(email).isPresent() == false) {
			throw new LoginException("Email not found");
		}
		if (gm.findByEmail(email).get().getActivation().equals("false")) {
			throw new LoginException("Account not activated");
		}
		if (!passwordencoder.matches(password, gm.findByEmail(email).get().getPassword())) {
			throw new LoginException("Password not correct");
		} else {
			User user = new User();
			user = gm.findByEmail(email).get();
			user.toString();

		}

	}

	/**
	 * This method is written to task send an activation link to registered email
	 * 
	 * @param jwToken
	 * @param emp
	 * @throws MessagingException 
	 */
	public void sendActivationLink(String jwToken, User emp) throws MessagingException {
		
		mail.setTo(emp.getEmail());
		mail.setSubject("Email Activation mail");
		mail.setBody("Click here to activate your account:\n\n" + "http://192.168.0.36:8080/fundoo/activateaccount/?"
				+ jwToken);
		mailService.sendMail(mail);
	}

	/**
	 * This method is written to activate the account
	 * @param jwt
	 * @throws AccountActivationException
	 */
	public void doActivateEmail(String jwt) throws AccountActivationException {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("JAYANTA")).parseClaimsJws(jwt)
				.getBody();

		if (gm.findById(claims.getId()).isPresent() == false) {
			throw new AccountActivationException("Account not get activated");
		} else {
			Optional<User> user = gm.findById(claims.getId());
			user.get().setActivation("true");
			gm.save(user.get());
		}
	}

	/**
	 * This method is written to send password to the registered email to if user
	 * forget password
	 * 
	 * @param email
	 * @return true if mail sent successfully else false
	 * @throws LoginException
	 * @throws MessagingException 
	 */
	public void doSendNewPasswordLink(String email) throws LoginException, MessagingException {
		JwtTokenBuilder jb = new JwtTokenBuilder();
		if (gm.findByEmail(email).isPresent() == false) {
			throw new LoginException("Email not exist");
		}	
	    mail.setTo(email);
		mail.setSubject("Password reset mail");
		mail.setBody("Copy the below link to postman and reset your password:\n\n"
				+ "192.168.0.36:8080/fundoo/resetpassword/?" + jb.createJWT(gm.findByEmail(email).get()));
		mailService.sendMail(mail);
	}

	/**
	 * Method to reset password
	 * @param jwtToken
	 * @param newPassword
	 */
	public void doResetPassword(String jwtToken, String newPassword) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("JAYANTA"))
				.parseClaimsJws(jwtToken).getBody();
		Optional<User> user = gm.findById(claims.getId());
		user.get().setPassword(passwordencoder.encode(newPassword));
		gm.save(user.get());
	}
}
