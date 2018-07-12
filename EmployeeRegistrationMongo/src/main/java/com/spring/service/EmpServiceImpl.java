package com.spring.service;

import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.model.EmployeeBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.spring.dao.GeneralMongoRepository;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

/**
 * purpose: Class to apply logic of Sign up and login
 * 
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
	 * 
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
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public EmployeeBean login(String email, String password) {
		Optional<EmployeeBean> isEmail = gm.findById(email);
		EmployeeBean eb = new EmployeeBean();
		if (isEmail.isPresent()) {
			System.out.println("email is present");
			if (isEmail.get().getPassword().equals(password) && isEmail.get().getActivate().equals("true")) {
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

	/**
	 * This method is written to task send an activation link to registered email
	 * @param jwToken
	 * @param emp
	 */
	public static void sendActivationLink(String jwToken, EmployeeBean emp) {
		String from = "roy.jayanta1201"; // GMail user name (just the part before "@gmail.com")
		String pass = "30964973209"; // GMail password
		String to = emp.getEmail();
		String subject = "EmailActivation mail";
		String body = "Click here to activate your account:\n\n"
				+ "http://192.168.0.36:8080/EmployeeRegistrationController/activateaccount/?" + jwToken;
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
		Optional<EmployeeBean> user = gm.findById(claims.getSubject());
		user.get().setActivate("true");
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
		String body = "Your current password is: " + gm.findById(email).get().getPassword();
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
