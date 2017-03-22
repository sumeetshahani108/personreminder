package com.example.user.personreminder.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3 ;
    public static final String DATABASE_NAME = "personreminder.db" ;

    //table contact
    public static final String TABLE_CONTACTS = "contacts" ;
    public static final String CONTACTS_ID = "_id" ;
    public static final String CONTACTS_NAME = "name" ;
    public static final String CONTACTS_NUMBER = "number" ;
    public static final String CONTACTS_IMAGE = "image" ;

    //table reminders
    public static final String TABLE_REMINDERS = "reminders" ;
    public static final String REMINDER_ID = "_id" ;
    public static final String REMINDER_TITLE = "title" ;
    public static final String REMINDER_DESCRIPTION = "description" ;
    public static final String REMINDER_LOCATION = "location" ;
    public static final String REMINDER_CREATED_AT = "created_at" ;
    public static final String REMINDER_CONTACT_ID = "contact_id" ;
    public static final String REMINDER_ALERT = "alert" ;
    public static final String DATE  = "date" ;
    public static final String TIME  = "time" ;
    public static final String REMINDER_ALERT_DATE  = "alert_date" ;
    public static final String REMINDER_ALERT_TIME  = "alert_time" ;

    public static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + " ( " +
            CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CONTACTS_NAME + " TEXT NOT NULL, " +
            CONTACTS_NUMBER + " TEXT NOT NULL, " +
            CONTACTS_IMAGE + " BLOB" +
            ");" ;

    public static final String CREATE_TABLE_REMINDERS = "CREATE TABLE " + TABLE_REMINDERS + " ( " +
            REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            REMINDER_TITLE + " TEXT NOT NULL, " +
            REMINDER_DESCRIPTION + " TEXT, " +
            REMINDER_LOCATION + " TEXT, " +
            REMINDER_CREATED_AT + " TEXT, " +
            REMINDER_CONTACT_ID + " INTEGER NOT NULL, " +
            REMINDER_ALERT + " INTEGER NOT NULL, " +
            DATE + " TEXT, " +
            TIME + " TEXT, " +
            REMINDER_ALERT_DATE + " TEXT, " +
            REMINDER_ALERT_TIME + " TEXT" +
            ");" ;
    private static final String TAG = "DATABASE_HELPER";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION );
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context,DATABASE_NAME, factory ,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DATABASE CREATED" );
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_REMINDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "UPGRADING DB VERSION FROM " + oldVersion + " to " + newVersion) ;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public Cursor checkContact(SQLiteDatabase db, String cNumber){
        String[] projection = {
                CONTACTS_ID ,
                CONTACTS_NAME,
                CONTACTS_NUMBER
        } ;

        String where = CONTACTS_NUMBER + " = " + cNumber ;
        return db.rawQuery("SELECT * FROM contacts WHERE number = '"+cNumber+"'", null);
    }

    public Cursor insertIntoContacts(String cNumber, String name, Blob image){
        String[] projection = {
                CONTACTS_ID
        } ;
        String where = CONTACTS_NUMBER + " = " + cNumber ;
        String order_by = CONTACTS_ID + "DESC" ;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(CONTACTS_NUMBER, cNumber);
        contentValues.put(CONTACTS_NAME, name);
        sqLiteDatabase.insert(TABLE_CONTACTS, null, contentValues) ;
        return sqLiteDatabase.rawQuery("SELECT * FROM contacts WHERE number = '"+cNumber+"'", null);
    }

    public boolean insertIntoReminders(String titleData,String descriptionData,String locationData,String reminderDate, String reminderTime, int alertData,String date,String time, int contact_id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(REMINDER_TITLE, titleData) ;
        contentValues.put(REMINDER_DESCRIPTION, descriptionData) ;
        contentValues.put(REMINDER_LOCATION, locationData) ;
        contentValues.put(REMINDER_CREATED_AT, getDateTime()) ;
        contentValues.put(REMINDER_ALERT, alertData) ;
        contentValues.put(DATE, date) ;
        contentValues.put(TIME, time) ;
        contentValues.put(REMINDER_ALERT_TIME, reminderTime) ;
        contentValues.put(REMINDER_ALERT_DATE, reminderDate) ;
        contentValues.put(REMINDER_CONTACT_ID, contact_id) ;
        long bool =  sqLiteDatabase.insert(TABLE_REMINDERS, null, contentValues) ;
        if(bool == -1)
            return true ;
        else
            return false ;
    }

    public Cursor getContactsOfReminders(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM contacts" , null);
        //Log.d("DatabaseHelper", res.getCount()+"");
        return res ;
    }

    public Cursor getReminders(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_REMINDERS + " WHERE contact_id = '" + id + "'",null) ;
        return res ;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
