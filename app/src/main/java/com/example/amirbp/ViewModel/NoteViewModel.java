package com.example.amirbp.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.amirbp.DB.AppDb;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.Model.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<List<Note>> noteList = new MutableLiveData<>();
    private MutableLiveData<Boolean> is_inserted = new MutableLiveData<>();
    private  MutableLiveData<Boolean> is_updated = new MutableLiveData<>();
    private MutableLiveData<Boolean> is_delete = new MutableLiveData<>();

    private MutableLiveData<Note> noteInfo = new MutableLiveData<>();

    public NoteViewModel(Application application) {
        this.context = application;
    }

    public MutableLiveData<Boolean> getIs_inserted() {
        return is_inserted;
    }

    public MutableLiveData<Boolean> getIs_updated() {
        return is_updated;
    }

    public MutableLiveData<Boolean> getIs_delete() {
        return is_delete;
    }

    public MutableLiveData<List<Note>> getContactsList() {
        return noteList;
    }

    public MutableLiveData<Note> getNoteInfoById() {
        return noteInfo;
    }

    public void callNoteListFromDB() {
        AppDb db = AppDb.getInstance(context);
        List<Note> notes = db.noteDao().getAllNote();
        noteList.setValue(notes);
    }
    public void insertNote(Note note) {
        try{
            is_inserted.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.noteDao().addNote(note);
            is_inserted.postValue(true);
          callNoteListFromDB();

        }catch (Exception e){
            is_inserted.postValue(false);
            e.printStackTrace();
        }


    }
    public void getNoteById( int id) {
        AppDb db = AppDb.getInstance(context);
       Note note= db.noteDao().getNoteById(id);
        noteInfo.setValue(note);
    }

    public void updateNote(Note note) {
        try{
            is_updated.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.noteDao().updateNote(note);
            is_updated.postValue(true);
           callNoteListFromDB();
        }catch (Exception e){
            is_updated.postValue(false);
            e.printStackTrace();
        }

    }
    public void deleteNoteById(int id) {
        try{
            is_delete.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.noteDao().deleteNoteById(id);
            is_delete.postValue(true);
            callNoteListFromDB();

        }catch (Exception e){
            is_delete.postValue(false);
            e.printStackTrace();
        }

    }
}
