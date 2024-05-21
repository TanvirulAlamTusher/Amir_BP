package com.example.amirbp.Model;



import java.util.List;

public class BackupData {
    List<Contact> contacts;
    List<Note> notes;


    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
