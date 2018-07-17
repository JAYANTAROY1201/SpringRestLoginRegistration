package com.todo.noteservice.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.exception.NoteReaderException;
import com.todo.noteservice.dao.GeneralMongoNote;
import com.todo.noteservice.dao.GeneralNoteService;
import com.todo.noteservice.model.Note;

/**
 * purpose:
 * 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
@Service
public class NoteServiceImpl implements GeneralNoteService {
	@Autowired
	private GeneralMongoNote repo;

	/* (non-Javadoc)
	 * @see com.todo.noteservice.dao.GeneralNoteService#doCreateNote(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void doCreateNote(String title, String description, String authorId) {
		Note note = new Note();
		note.set_id("hk");
		note.setAuthorId(authorId);
		note.setTitle(title);
		note.setDescription(description);
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		note.setDateOfCreation(formatter.format(new Date()));
		note.setLastDateOfModified(formatter.format(new Date()));
		repo.save(note);
	}

	/* (non-Javadoc)
	 * @see com.todo.noteservice.dao.GeneralNoteService#doReadNote(java.lang.String)
	 */
	@Override
	public void doReadNote(String userID) throws NoteReaderException {
		Optional<Note>[] noteOptional = repo.findByAuthorId(userID);
		if (noteOptional.length == 0)
			throw new NoteReaderException("No note found");
		for (Optional<Note> note : noteOptional) {
			System.out.println(note.get().toString());
		}

	}

	/* (non-Javadoc)
	 * @see com.todo.noteservice.dao.GeneralNoteService#doUpdateNote()
	 */
	@Override
	public void doUpdateNote() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todo.noteservice.dao.GeneralNoteService#doDeleteNote()
	 */
	@Override
	public void doDeleteNote() {
		// TODO Auto-generated method stub

	}
}
