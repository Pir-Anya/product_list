package com.devcolibri.DBAndroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseHandler db = new DatabaseHandler(this);

        System.out.println("Inserting ..");
        db.addContact(new Contact("Empty and One contact", "806800000"));

        System.out.println("Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            System.out.print("Name: ");
            System.out.println(log);
        }

        db.deleteAll();
    }
}
