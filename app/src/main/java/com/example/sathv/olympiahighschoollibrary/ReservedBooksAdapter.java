package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReservedBooksAdapter extends ArrayAdapter<ReservedBook> {

    Context context;
    int resource;
    ArrayList<ReservedBook> arraylistreservedbooks = null;
    ReservedBook reservedBook;

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
        reservedBook = arraylistreservedbooks.get(position);

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

                        notifyDataSetChanged();

                        deletereservation();
                    }
                }
        );
        return convertView;
    }

    private void deletereservation() {
        Firebase mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + reservedBook.title);
        mReffname.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //store the information in Map
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String whoreservedto = map.get("reservations");
                String checkedoutto = map.get("checkedoutto");
                String dateoutconfirm = map.get("duedate");
                String statusdelete = map.get("status");
                SharedPreferences preferences = context.getSharedPreferences("todelete", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getContext().getString(R.string.whodelete), whoreservedto);
                editor.putString(getContext().getString(R.string.checkedoutdelete), checkedoutto);
                editor.putString(getContext().getString(R.string.duedatedelete), dateoutconfirm);
                editor.putString(getContext().getString(R.string.statusdelete), statusdelete);
                editor.commit();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        SharedPreferences preferences = context.getSharedPreferences("todelete", Context.MODE_PRIVATE);
        String wordstring = preferences.getString(getContext().getString(R.string.whodelete), "");
        String deletechecked = preferences.getString(getContext().getString(R.string.checkedoutdelete), "");
        String deletedate = preferences.getString(getContext().getString(R.string.duedatedelete), "");
        String statusdeleted = preferences.getString(getContext().getString(R.string.statusdelete), "");

        String categorydelete = null, pagecountdelete = null, summarydelete = null, isbndelete = null;

        for (int i = 0; i < Login.getTils().length; i++) {
            if (Login.getTils()[i].equals(reservedBook.title)) {
                categorydelete = Login.getCs()[i];
                pagecountdelete = Login.getPgs()[i];
                summarydelete = Login.getSs()[i];
                isbndelete = Login.getIss()[i];
            }
        }


        wordstring = wordstring.replace(wordstring, "");

        final FirebaseBook bookdets = new FirebaseBook(reservedBook.title, reservedBook.author,
                categorydelete, pagecountdelete, summarydelete, isbndelete, statusdeleted, deletedate, deletechecked, wordstring);
        mReffname.setValue(bookdets);
    }
}
