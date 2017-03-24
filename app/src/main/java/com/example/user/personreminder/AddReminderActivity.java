package com.example.user.personreminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.personreminder.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ADD_REMINDER_ACTIVITY";

    DatabaseHelper myDB;

    TextView saveReminder;
    TextView cancelReminderSetup;

    EditText title;
    EditText description;
    EditText location;
    TextView contact;
    Button addContact;
    TextView addPickdate;
    TextView addPickTime;
    TextView addReminderDate ;
    TextView addReminderTime ;

    PendingIntent pendingIntent ;
    AlarmManager alarm_manager ;
    public static final int PICK_CONTACT = 1;
    String titleData;
    String descriptionData;
    String locationData;
    String contactData;
    int alertData ;

    String cNumber ;
    String name ;
    String date ;
    String time ;

    String reminderDate ;
    String reminderTime ;


    int year;
    int month;
    int day;

    int hours;
    int minutes;

    int reminderYear;
    int reminderMonth;
    int reminderDay;

    int reminderHours;
    int reminderMinutes;

    int DATE_DIALOG_ID = 1;
    int TIME_DIALOG_ID = 0;
    int REMINDER_DATE_DIALOG_ID = 1;
    int REMINDER_TIME_DIALOG_ID = 0;

    SwitchCompat alertBox ;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        myDB = new DatabaseHelper(this) ;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        title = (EditText) findViewById(R.id.addTitle);
        description = (EditText) findViewById(R.id.addDescription);
        location = (EditText) findViewById(R.id.addLocation);
        contact = (TextView) findViewById(R.id.contact);
        addPickdate = (TextView) findViewById(R.id.addPickDate);
        addPickTime = (TextView) findViewById(R.id.addPickTime);
        addReminderDate = (TextView) findViewById(R.id.addReminderDate) ;
        addReminderTime = (TextView) findViewById(R.id.addReminderTime) ;

        addContact = (Button) findViewById(R.id.addContact);
        saveReminder = (TextView) findViewById(R.id.saveReminder);
        cancelReminderSetup = (TextView) findViewById(R.id.cancelReminderSetup);

        addPickdate.setOnClickListener(this);
        addPickTime.setOnClickListener(this);
        addReminderDate.setOnClickListener(this);
        addReminderTime.setOnClickListener(this);
        addContact.setOnClickListener(this);
        saveReminder.setOnClickListener(this);
        cancelReminderSetup.setOnClickListener(this);

        addReminderDate.setText(day + "-" + month + "-" + year);
        addReminderTime.setText(hours + ":" + minutes);
        addPickdate.setText(day + "-" + month + "-" + year);
        addPickTime.setText(hours + ":" + minutes);

        alertBox = (SwitchCompat) findViewById(R.id.addAlert) ;
        final LinearLayout layone= (LinearLayout) findViewById(R.id.layoutForAlert);
        layone.setVisibility(View.GONE);

        alertBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d(TAG, "isChecked");
                    layone.setVisibility(View.VISIBLE);
                }else{
                    Log.d(TAG, "isNotChecked");
                    layone.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addReminderDate:
                showDialog(REMINDER_DATE_DIALOG_ID);
                break;
            case R.id.addReminderTime:
                showDialog(REMINDER_TIME_DIALOG_ID);
                break;
            case R.id.addPickDate:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.addPickTime:
                showDialog(TIME_DIALOG_ID);
                break;
            case R.id.saveReminder:
                SaveReminder();
                break;
            case R.id.cancelReminderSetup:
                finish();
                break;
            case R.id.addContact:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_CONTACT);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            //System.out.println("number is:" + cNumber);
                        }
                        name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        contact.setText(name + " : " + cNumber);
                    }
                }
                break;
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this, datePickerListener, year, month, day);
        } else if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this, timePickerListener, hours, minutes, false);
        } else if (id == REMINDER_DATE_DIALOG_ID) {
            return new DatePickerDialog(this, reminderDatePickerListener, year, month, day);
        } else if (id == REMINDER_TIME_DIALOG_ID) {
            return new TimePickerDialog(this, reminderTimePickerListener, hours, minutes, false);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearData, int monthOfYear, int dayOfMonth) {
            year = yearData;
            month = monthOfYear + 1;
            day = dayOfMonth;
            addPickdate.setText(day + "-" + month + "-" + year);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hours = hourOfDay;
            minutes = minute;
            addPickTime.setText(hours + "-" + minutes);
        }
    };

    private DatePickerDialog.OnDateSetListener reminderDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearData, int monthOfYear, int dayOfMonth) {
            reminderYear = yearData;
            reminderMonth = monthOfYear + 1;
            reminderDay = dayOfMonth;
            addReminderDate.setText(reminderDay + "-" + reminderMonth + "-" + reminderYear);
        }
    };

    private TimePickerDialog.OnTimeSetListener reminderTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            reminderHours = hourOfDay;
            reminderMinutes = minute;
            addReminderTime.setText(reminderHours + "-" + reminderMinutes);
        }
    };

    private void SaveReminder() {

        titleData = title.getText().toString().trim();
        descriptionData = description.getText().toString().trim();
        locationData = location.getText().toString().trim();
        date = day + "-" + month + "-" + year ;
        time = hours + "-" + minutes ;
        if(alertBox.isChecked()){
            alertData = 1 ;
            reminderDate = day + "-" + month + "-" + year ;
            reminderTime = hours + "-" + minutes ;
        }

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent my_intent = new Intent(this, Alarm_Receiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, reminderYear);
        calendar.set(Calendar.MONTH, reminderMonth);
        calendar.set(Calendar.DAY_OF_MONTH, reminderDay);
        calendar.set(Calendar.HOUR_OF_DAY, reminderHours);
        calendar.set(Calendar.MINUTE, reminderMinutes);

        my_intent.putExtra("extra", "alarm on");

        pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        contactData = cNumber ;
        if(title == null){
            Toast.makeText(this, "Title cannot be empty"  , Toast.LENGTH_LONG).show();
        }else if(cNumber == null){
            Toast.makeText(this, "Choose a Contact"  , Toast.LENGTH_LONG).show();
        }else{
            SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase() ;
            Cursor contactCheckCursor = myDB.checkContact(sqLiteDatabase, cNumber) ;
            if(contactCheckCursor.moveToFirst()){
                Log.e(TAG, "count" + contactCheckCursor.getCount());
                int contact_id = contactCheckCursor.getInt(0) ;
                Log.e(TAG, "count" + contact_id); String number = contactCheckCursor.getString(2);
                myDB.insertIntoReminders(titleData, descriptionData, locationData, reminderDate, reminderTime, alertData, date, time, contact_id) ;
                finish();
                Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show() ;
            }else{
                Cursor contactGetCursor =  myDB.insertIntoContacts(cNumber, name, null) ;
                if(contactGetCursor.moveToFirst()){
                    int contact_id = contactGetCursor.getInt(0) ;
                    myDB.insertIntoReminders(titleData, descriptionData, locationData, reminderDate, reminderTime, alertData, date, time,contact_id) ;
                    finish();
                    Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show() ;
                }else{
                    Toast.makeText(this, "some error", Toast.LENGTH_LONG).show();
                }

            }
        }



    }
}
