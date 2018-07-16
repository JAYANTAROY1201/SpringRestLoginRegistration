package com.todo.user.services;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.todo.user.dao.MailService;
import com.todo.user.model.Mail;


/**
 * purpose: Implementation class for sending mail
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@Component
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	/** (non-Javadoc)
	 * @see com.todo.user.dao.MailService#sendMail(com.todo.user.model.Mail)
	 */
	@Override
	public void sendMail(Mail mail) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setSubject(mail.getSubject());
		helper.setTo(mail.getTo());
		helper.setText(mail.getBody());
		
		javaMailSender.send(message);
	}
}