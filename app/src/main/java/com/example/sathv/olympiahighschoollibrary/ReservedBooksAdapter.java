package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReservedBooksAdapter extends ArrayAdapter<ReservedBook> {

    Context context;
    int resource;
    ArrayList<ReservedBook> arraylistreservedbooks = null;

    public ReservedBooksAdapter(Context context, int resource, ArrayList<ReservedBook> arraylistreservedbooks) {
        super(context, resource, arraylistreservedbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistreservedbooks = arraylistreservedbooks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservedBook reservedBook = arraylistreservedbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemforreserved, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.titleofbookinreserved);
        TextView authorinreserved = (TextView) convertView.findViewById(R.id.reservedauthor);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.reservedimage);
        Button deleteButton = (Button) convertView.findViewById(R.id.remove);

        bookTitle.setText(reservedBook.title);
        authorinreserved.setText(reservedBook.author);
        bookImage.setImageResource(reservedBook.imageid);

        deleteButton.setTag(position);

        deleteButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer index = (Integer) v.getTag();
                        arraylistreservedbooks.remove(index.intValue());
                        notifyDataSetChanged();
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Deletion");
                        builder.setMessage("Are you sure you want to cancel this reservation");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();*/

                    }
                }
        );

        return convertView;
    }
}
