package com.example.amirbp.Api;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.amirbp.Model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAllContact();
    @Insert
    void addContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getContactById(int id);
}
