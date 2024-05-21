package com.example.amirbp.Utils;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.JsonReader;
import android.util.Log;
import android.webkit.MimeTypeMap;


import com.example.amirbp.DB.AppDb;
import com.example.amirbp.Model.BackupData;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.Model.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackupUtils {
    private static final String TAG = "BackupUtils_TAG";
    private static final String BACKUP_FOLDER = "ContactList_Backup";
    private static final String BACKUP_FILE_PREFIX = "contact_list_backup_";
    private static final String BACKUP_FILE_EXTENSION = ".json";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static BackupUtils instance;
    private final Context context;


    public BackupUtils(Context context) {
        this.context = context;
    }

    public static BackupUtils getInstance(Context context) {
        if (instance == null) {
            instance = new BackupUtils(context);
        }
        return instance;
    }


    private String getBackupFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault());
        String timeStamp = sdf.format(new Date());
        return timeStamp + BACKUP_FILE_PREFIX + BACKUP_FILE_EXTENSION;
    }

    public String getMimeTypeFromUri(Uri uri) {
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private void triggerMediaScan(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);
    }

    public void saveAllBackup( List<Contact> contacts,
                               List<Note> notes, BackupCallback callback) {
        executor.execute(() -> {
            try {
                Log.d(TAG, "Starting backup process...");

                BackupData backupData = new BackupData();
                backupData.setContacts(contacts);
                backupData.setNotes(notes);


                String jsonString = new Gson().toJson(backupData);

                Log.d(TAG, "Serialized data into JSON");
                String fileName = getBackupFileName();
                Log.d(TAG, "Created backup file: " + fileName);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + File.separator + BACKUP_FOLDER);

                    ContentResolver contentResolver = context.getContentResolver();
                    Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
                    try (OutputStream outputStream = contentResolver.openOutputStream(uri)) {
                        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
                        Log.d(TAG, "Data written to output stream");
                        triggerMediaScan(uri);
                        mainThreadHandler.post(() -> callback.onSuccess(true));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "Error writing to output stream: " + e.getMessage());

                        mainThreadHandler.post(() -> callback.onError(e));
                    }

                } else {
                    try {
                        File backupDir = new File(Environment.getExternalStorageDirectory(), BACKUP_FOLDER);
                        if (!backupDir.exists()) {
                            backupDir.mkdirs();
                        }
                        File backupFile = new File(backupDir, fileName);
                        FileOutputStream fos = new FileOutputStream(backupFile);
                        fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
                        Log.d(TAG, "Data written to file output stream");

                        triggerMediaScan(Uri.fromFile(backupFile));
                        mainThreadHandler.post(() -> callback.onSuccess(true));
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "Error writing to file output stream: " + e.getMessage());

                        mainThreadHandler.post(() -> callback.onError(e));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Error during backup process: " + e.getMessage());

                mainThreadHandler.post(() -> callback.onError(e));
            }
        });
    }


    //restore
    public void restoreBackup(Uri backupUri, RestoreCallback callback) {
        try {
            // Read the backup data from the file
            BackupData backupData = readBackupDataFromFile(backupUri);

            // Get the data from the backup data
            List<Contact> contacts = backupData.getContacts();
            List<Note> note = backupData.getNotes();


            // Check and restore categories
            for (Contact c : contacts) {
                // Check if the category ID already exists in the app's database
                if (!isContactExistsInDb(c)) {
                    // Category doesn't exist, restore it
                    restoreContact(c);
                } else {
                    // Category already exists, skip restoring it
                }
            }

            // Check and restore products
            for (Note n : note) {
                // Check if the product ID already exists in the app's database
                if (!isNoteExistsInDb(n)) {
                    // Product doesn't exist, restore it
                    restoreNote(n);
                } else {
                    // Product already exists, skip restoring it
                }
            }

            // Notify success
            mainThreadHandler.post(callback::onSuccess);

        } catch (IOException e) {
            e.printStackTrace();
            mainThreadHandler.post(() -> callback.onError(e));
        }
    }


    private BackupData readBackupDataFromFile(Uri backupUri) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        List<Note> notes = new ArrayList<>();


        InputStream inputStream = context.getContentResolver().openInputStream(backupUri);
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            JsonReader jsonReader = new JsonReader(inputStreamReader);

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("contacts")) {
                    Type listType = new TypeToken<List<Contact>>() {}.getType();
                    contacts = new Gson().fromJson(String.valueOf(jsonReader), listType);
                } else if (name.equals("notes")) {
                    Type listType = new TypeToken<List<Note>>() {}.getType();
                    notes = new Gson().fromJson(String.valueOf(jsonReader), listType);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();

            jsonReader.close();
        }
        inputStream.close();

        BackupData backupData = new BackupData();
        backupData.setContacts(contacts);
        backupData.setNotes(notes);
        return backupData;
    }

    private boolean isContactExistsInDb(Contact contact) {
        AppDb appDataBase = AppDb.getInstance(context);
       int count = appDataBase.contactDao().getContactCountById(contact.getId());

     if(count>0){
       return  true;
     }
        return false;

    }

    private void restoreContact(Contact contact) {
         AppDb appDataBase = AppDb.getInstance(context);
        appDataBase.contactDao().addContact(contact);
    }

    private boolean isNoteExistsInDb(Note note) {
        AppDb appDataBase = AppDb.getInstance(context);
        int count = appDataBase.noteDao().getNoteCountById(note.getId());
        if(count>0){
            return  true;
        }
        return false;
    }

    private void restoreNote(Note note) {
        AppDb appDataBase = AppDb.getInstance(context);
        appDataBase.noteDao().addNote(note);

    }




    // RestoreCallback interface
    public interface RestoreCallback {
        void onSuccess();

        void onNoDataToRestore();

        void onError(Exception e);
    }




    // Callback interface
    public interface BackupCallback {
        void onSuccess(boolean b);

        void onError(Exception e);
    }

}
