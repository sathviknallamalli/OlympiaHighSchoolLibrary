package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReservedBooksAdapter extends ArrayAdapter<ReservedBook> {

    Context context;
    int resource;
    private Firebase mReffname;
    ArrayList<ReservedBook> arraylistreservedbooks = null;

    //reserved book adapter constructor
    public ReservedBooksAdapter(Context context, int resource, ArrayList<ReservedBook> arraylistreservedbooks) {
        super(context, resource, arraylistreservedbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistreservedbooks = arraylistreservedbooks;
    }

    //display each book in listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservedBook reservedBook = arraylistreservedbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemforreserved, parent, false);
        }

        //retrieve variables
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView authorinreserved = (TextView) convertView.findViewById(R.id.reservedauthor);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookimage);
        Button deleteButton = (Button) convertView.findViewById(R.id.remove);

        //set each field with necessary variables by variables from reservedBook which is the current book being loaded
        bookTitle.setText(reservedBook.title);
        authorinreserved.setText(reservedBook.author);
        bookImage.setImageResource(reservedBook.imageid);

        //set tag to current row for the book
        deleteButton.setTag(position);

        //delete button onclick
        deleteButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Integer index = (Integer) v.getTag();
                        //delete the appropriate index from arraylist
                        arraylistreservedbooks.remove(index.intValue());

                        //remove the appropriate element from the appropriate arraylists for reserved books as well
                        BookInformation.reservedbooktitles.remove(index.intValue());
                        BookInformation.reservedbookimages.remove(index.intValue());
                        BookInformation.reservedbookauthor.remove(index.intValue());
                        notifyDataSetChanged();


                    }
                }
        );
        return convertView;
    }
}
