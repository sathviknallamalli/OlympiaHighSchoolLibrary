package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReminderBooksAdapter extends ArrayAdapter<ReminderBook> {

    Context context;
    int resource;
    ArrayList<ReminderBook> arrayreminderbooks = null;

    //reminder adapter contructor
    public ReminderBooksAdapter(Context context, int resource, ArrayList<ReminderBook> arrayreminderbooks) {
        super(context, resource, arrayreminderbooks);
        this.context = context;
        this.resource = resource;
        this.arrayreminderbooks = arrayreminderbooks;
    }

    //action for each listview item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReminderBook reminderBook = arrayreminderbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.reminderlayout, parent, false);
        }

        //retrieve fields
        TextView bookTitle = (TextView) convertView.findViewById(R.id.reminderbookname);
        TextView dueDatePhrase = (TextView) convertView.findViewById(R.id.reminderduephrase);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.reminderduephrase);

        //set each field with appropriate fields, using the book created retrieve the variables because they are global
        bookTitle.setText(reminderBook.title);
        bookImage.setImageResource(reminderBook.imageid);
        dueDatePhrase.setText(reminderBook.duedatephrase);

        return convertView;
    }

}
