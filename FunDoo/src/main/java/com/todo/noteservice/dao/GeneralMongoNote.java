package com.todo.noteservice.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.todo.noteservice.model.Note;

/**
 * purpose: 
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
public interface GeneralMongoNote extends MongoRepository<Note,String>
{
   public Optional<Note>[] findByAuthorId(String userId);
}
