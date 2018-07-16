package com.todo.user.model;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * purpose:To set properties 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 16/07/18
 */
@Service
public class Mail implements Serializable{

	private static final long serialVersionUID = 1L;
	private String to;
	private String subject;
	private String body;
	public Mail() {	
	}
	/**
	 * @return
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

}
