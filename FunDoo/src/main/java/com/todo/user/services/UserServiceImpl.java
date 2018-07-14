package com.todo.user.services;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.user.dao.GeneralMongoRepository;
import com.todo.user.exception.LoginException;
import com.todo.user.exception.SignupException;
import com.todo.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * purpose: 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
/**
 * purpose: 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@Service
public class UserServiceImpl {
	@Autowired
	private GeneralMongoRepository gm;


	/**
	 * This method is add functionality for sign up
	 * 
	 * @param emp
	 * @return true if sign up successful else false
	 * @throws SignupException 
	 */
   public void signUp(User emp) throws SignupException
  { 
	   if(emp.getEmail().equals(""))
	   {
		   throw new SignupException("Email is null");
	   }
	   else {
	   Optional<User> user=gm.findByEmail(emp.getEmail());
	   if(user.isPresent())
	   {
		throw new SignupException("Email already exist");
	   }
	   else
	   {
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
	 */
   public void logIn(String email,String password) throws LoginException
   {
	   if(email.equals(""))
	   {
		   throw new LoginException("Email can't be null");
	   }
	   if(password.equals(""))
	   {
		   throw new LoginException("Password cannot be blank");   
	   }
	   if(gm.findByEmail(email).isPresent()==false)
	   {
		   throw new LoginException("Email not found");
	   }
	   if(!(gm.findByEmail(email).get().getPassword().equals(password)))
	   {
		 throw new LoginException("Password not correct"); 
	   }
	   else
	   {
		   User user=new User();
		   user=gm.findByEmail(email).get();
	   }
   
   }
	

	/**
	 * This method is written to task send an activation link to registered email
	 * @param jwToken
	 * @param emp
	 */
	public static void sendActivationLink(String jwToken, User emp) {
		String from = "roy.jayanta1201"; // GMail user name (just the part before "@gmail.com")
		String pass = "30964973209"; // GMail password
		String to = emp.getEmail();
		String subject = "Email Activation mail";
		String body = "Click here to activate your account:\n\n"
				+ "http://192.168.0.36:8080/fundoo/activateaccount/?" + jwToken;
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception ae) {
			ae.printStackTrace();
		}
	}

	/**
	 * This method is written to activate the account
	 * @param jwt
	 * @return true if account got successfully activated
	 */
	public boolean activate(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("JAYANTA")).parseClaimsJws(jwt)
				.getBody();
		Optional<User> user = gm.findById(claims.getId());
		user.get().setActivation("true");
		gm.save(user.get());
		return true;
	}

	/**
	 * This method is written to send password to the registered email to if user forget password
	 * @param email
	 * @return true if mail sent successfully else false
	 */
	public boolean forgotPassword(String email) {
		String from = "roy.jayanta1201"; // GMail user name (just the part before "@gmail.com")
		String pass = "30964973209"; // GMail password
		String to = email;
		String subject = "Password recovery mail";
		String body = "Your current password is: " + gm.findByEmail(email).get().getPassword();
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception ae) {
			ae.printStackTrace();
			return false;
		}
		return true;
	}
}
