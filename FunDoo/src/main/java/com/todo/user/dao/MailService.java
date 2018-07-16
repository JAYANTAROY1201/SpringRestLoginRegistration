package com.todo.user.dao;

import javax.mail.MessagingException;

import com.todo.user.model.Mail;

/**
 * purpose: Sending mail
 * @author JAYANTA ROY
 * @version 1.0
 * @since 16/07/18
 */
public interface MailService {
	/**
	 * This method will send mail
	 * @param mail
	 * @throws MessagingException
	 */
	public void sendMail(Mail mail) throws MessagingException;
}
