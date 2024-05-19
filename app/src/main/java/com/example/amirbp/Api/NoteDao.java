package com.example.amirbp.Api;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.amirbp.Model.Contact;
import com.example.amirbp.Model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAllNote();
    @Insert
    void addNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM Note WHERE id = :id")
    Note getNoteById(int id);

    //delete by id
    @Query("DELETE FROM Note WHERE id = :id")
    void deleteNoteById(int id);
}
