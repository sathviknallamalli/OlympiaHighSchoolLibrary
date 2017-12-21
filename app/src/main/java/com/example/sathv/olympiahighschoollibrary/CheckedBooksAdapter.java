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

public class CheckedBooksAdapter extends ArrayAdapter<CheckedBook> {

    Context context;
    int resource;
    ArrayList<CheckedBook> arraylistcheckedbooks = null;

    public CheckedBooksAdapter(Context context, int resource, ArrayList<CheckedBook> arraylistcheckedbooks ) {
        super(context, resource, arraylistcheckedbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistcheckedbooks = arraylistcheckedbooks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedBook checkedBook = arraylistcheckedbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemforchecked, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.titleofbookinreserved);
        TextView dateinchecked = (TextView) convertView.findViewById(R.id.checkedoutdateforrowitem);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.reservedimage);

        bookTitle.setText(checkedBook.title);
        dateinchecked.setText("DUE DATE: "+ checkedBook.date);
        dateinchecked.setTextColor(convertView.getResources().getColor(R.color.crimson));
        bookImage.setImageResource(checkedBook.imageid);

        return convertView;
    }
}
