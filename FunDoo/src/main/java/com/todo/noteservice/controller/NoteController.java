package com.todo.noteservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.exception.NoteReaderException;
import com.todo.noteservice.dao.GeneralNoteService;
import com.todo.noteservice.services.NoteServiceImpl;
import com.todo.userservice.controller.UserController;
import com.todo.userservice.utility.JwtTokenBuilder;

/**
 * purpose: 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@RestController
@RequestMapping("/fundoo/note")
public class NoteController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	NoteServiceImpl noteService;

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<String> createNote(@RequestParam String title, @RequestParam String description,
			@RequestParam String jwt) {

		noteService.doCreateNote(title, description, JwtTokenBuilder.parseJWT(jwt).getId());
		logger.info("New note created");
		return new ResponseEntity<String>("New note created", HttpStatus.OK);
	}

	@RequestMapping(value = "/viewnotes", method = RequestMethod.POST)
	public ResponseEntity<String> createNote(@RequestParam String jwt) throws NoteReaderException {

		noteService.doReadNote(JwtTokenBuilder.parseJWT(jwt).getId());
		return new ResponseEntity<String>("request granted", HttpStatus.OK);
	}

}
