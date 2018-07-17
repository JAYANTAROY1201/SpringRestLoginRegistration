package com.todo.noteservice.dao;

import com.todo.exception.NoteReaderException;

/**
 * purpose: 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
public interface GeneralNoteService {
	public void doCreateNote(String title, String description, String authorId);

	public void doReadNote(String userId) throws NoteReaderException;

	public void doUpdateNote();

	public void doDeleteNote();
}
