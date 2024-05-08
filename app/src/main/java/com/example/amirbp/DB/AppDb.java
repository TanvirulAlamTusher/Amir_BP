package com.example.amirbp.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.amirbp.Api.ContactDao;
import com.example.amirbp.Model.Contact;


@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    private static AppDb INSTANCE;

    public static AppDb getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDb.class, "App_DB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract ContactDao contactDao();

}
