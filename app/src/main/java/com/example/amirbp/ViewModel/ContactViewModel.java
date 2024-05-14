package com.example.amirbp.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.amirbp.DB.AppDb;
import com.example.amirbp.Model.Contact;

import java.util.List;

public class ContactViewModel extends ViewModel {
     private Context context;
    private MutableLiveData<List<Contact>> contactsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> is_inserted = new MutableLiveData<>();
    private  MutableLiveData<Boolean> is_updated = new MutableLiveData<>();
    private MutableLiveData<Boolean> is_delete = new MutableLiveData<>();

    private MutableLiveData<Contact> contactInfo = new MutableLiveData<>();

    public ContactViewModel(Application application) {
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

    public MutableLiveData<List<Contact>> getContactsList() {
        return contactsList;
    }

    public MutableLiveData<Contact> getContactInfoById() {
        return contactInfo;
    }

    public void callContactsListFromDB() {
        AppDb db = AppDb.getInstance(context);
        List<Contact> contacts = db.contactDao().getAllContact();
        contactsList.setValue(contacts);
    }
    public void insertContact(Contact contact) {
        try{
            is_inserted.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.contactDao().addContact(contact);
            is_inserted.postValue(true);
            callContactsListFromDB();

        }catch (Exception e){
            is_inserted.postValue(false);
            e.printStackTrace();
        }


    }
    public void updateContact( Contact contact) {
        try{
            is_updated.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.contactDao().updateContact(contact);
            is_inserted.postValue(true);
            callContactsListFromDB();
        }catch (Exception e){
            is_updated.postValue(false);
            e.printStackTrace();
        }

    }
    public void deleteContact( Contact contact) {
        try{
            is_delete.postValue(false);
            AppDb db = AppDb.getInstance(context);
            db.contactDao().deleteContact(contact);
            is_delete.postValue(true);
            callContactsListFromDB();

        }catch (Exception e){
            is_delete.postValue(false);
            e.printStackTrace();
        }

    }

    /////////////////////////
    public void getContactsListById( int id) {
        AppDb db = AppDb.getInstance(context);
       Contact contacts = db.contactDao().getContactById(id);
       contactInfo.setValue(contacts);
    }
}
