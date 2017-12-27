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

    public ReminderBooksAdapter(Context context, int resource, ArrayList<ReminderBook> arrayreminderbooks) {
        super(context, resource, arrayreminderbooks);
        this.context = context;
        this.resource = resource;
        this.arrayreminderbooks = arrayreminderbooks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReminderBook reminderBook = arrayreminderbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.reminderlayout, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.reminderbookname);
        TextView dueDatePhrase = (TextView) convertView.findViewById(R.id.reminderduephrase);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.reminderduephrase);

        bookTitle.setText(reminderBook.title);
        bookImage.setImageResource(reminderBook.imageid);
        dueDatePhrase.setText(reminderBook.duedatephrase);

        return convertView;
    }

}
