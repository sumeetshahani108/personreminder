package com.example.user.personreminder.data;

/**
 * Created by user on 22-03-2017.
 */

public class ReminderList {
    private int reminder_id ;
    private String reminder_title ;
    private String reminder_description ;
    private String reminder_date ;
    private String reminder_time ;
    private String alert_date ;
    private String alert_time ;

    public ReminderList(){

    }

    public ReminderList(int reminder_id, String reminder_title, String reminder_date, String reminder_description, String reminder_time, String alert_time, String alert_date) {
        this.reminder_title = reminder_title;
        this.reminder_date = reminder_date;
        this.reminder_description = reminder_description;
        this.reminder_time = reminder_time;
        this.alert_time = alert_time;
        this.alert_date = alert_date;
        this.reminder_id = reminder_id ;
    }

    public String getReminder_title() {
        return reminder_title;
    }

    public void setReminder_title(String reminder_title) {
        this.reminder_title = reminder_title;
    }

    public String getReminder_description() {
        return reminder_description;
    }

    public void setReminder_description(String reminder_description) {
        this.reminder_description = reminder_description;
    }

    public String getReminder_date() {
        return reminder_date;
    }

    public void setReminder_date(String reminder_date) {
        this.reminder_date = reminder_date;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }

    public String getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(String alert_date) {
        this.alert_date = alert_date;
    }

    public String getAlert_time() {
        return alert_time;
    }

    public void setAlert_time(String alert_time) {
        this.alert_time = alert_time;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }
}

