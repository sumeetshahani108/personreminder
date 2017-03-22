package com.example.user.personreminder.data;

/**
 * Created by user on 20-03-2017.
 */

public class ContactList {
    private int id ;
    private String name ;
    private String number ;

    public ContactList(){

    }

    public ContactList(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
