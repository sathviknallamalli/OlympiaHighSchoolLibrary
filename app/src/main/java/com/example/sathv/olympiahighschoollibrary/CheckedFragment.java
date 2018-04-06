package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathv on 11/28/2017.
 */

public class CheckedFragment extends Fragment {

    public CheckedFragment() {

    }

    ListView lvc;
    ArrayList<CheckedBook> checkedBooks;
    Firebase refname;
    TextView message;


    ArrayList<String> information = new ArrayList<>();


    private CheckedBooksAdapter adapter;

    Login l = new Login();


    String ctits[];
    String cds[];
    // String cds[] = Login.getCds();

    Map<String, Object> titletemp, datetemp;

    View view;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Checked out books");
        view = inflater.inflate(R.layout.checkedbooks, container, false);


        lvc = (ListView) view.findViewById(R.id.listofcheckedbooks);
        checkedBooks = new ArrayList<CheckedBook>();
        message = (TextView) view.findViewById(R.id.nobookmessage);

        //set the search bar option
        setHasOptionsMenu(true);

        titletemp = new HashMap<String, Object>();
        datetemp = new HashMap<String, Object>();

        ctits = collectBookData("title", l.getUsername());
        cds = collectBookData("duedate", l.getUsername());


        //if the checkedout arraylists are empty, then display a message
        if (ctits.length == 0 || cds.length == 0) {

            message.setText("You currently have no books checked out");
        }
        //if not then add each checked out book to an arraylist and make adapter
        else {
            Log.d("BLANK", "ENTERED");
            for (int i = 0; i < ctits.length; i+=2) {

                //add checked book to arraylist
                checkedBooks.add(new CheckedBook(ctits[i], cds[i+1], R.drawable.bear));

            }
            //set adapter
            adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
            lvc.setAdapter(adapter);
        }
        return view;
    }

    private String[] collectBookData(final String fieldName, final String usernamecheck) {


        int val = Login.getTils().length;

        for (int i = 0; i < val; i++) {
            refname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + Login.getTils()[i]);

            refname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);

                    if (map.get("checkedoutto").equals(usernamecheck)) {
                        information.add(map.get(fieldName));

                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : information) {
                            stringBuilder.append(s);
                            stringBuilder.append(", ");
                        }

                        if (fieldName.equals("title")) {
                            SharedPreferences preferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("title", stringBuilder.toString());
                            editor.commit();
                        } else if (fieldName.equals("duedate")) {
                            SharedPreferences preferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("duedate", stringBuilder.toString());
                            editor.commit();
                        }

                    }
                    //Log.d("DUDE", information.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });


        }

        SharedPreferences preferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String wordstring = preferences.getString(fieldName, "");
        //  Log.d("DUDE", "AFTER" + wordstring);

        String[] temp = wordstring.split(", ");

        return temp;
    }

    //override menu onclick method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();
        //search bar onclicklistener
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if the search bar is empty then display the original listview through resetsearch mehtod
                if (newText == null || newText.trim().isEmpty()) {
                    resetSearch();
                    return false;
                }

                //make filtered values arraylist
                ArrayList<CheckedBook> filteredValues = new ArrayList<CheckedBook>(checkedBooks);

                for (int i = 0; i < checkedBooks.size(); i++) {

                    //if the title of each book doesnt contain what the search bar is, then remove the book from filteredvalues
                    if (!(checkedBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(checkedBooks.get(i));
                    }
                }

                //set the adapter to the new arraylist of filtered values
                adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, filteredValues);
                lvc.setAdapter(adapter);

                return false;
            }
        };
        //set searchbar listener and hint
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");
    }

    //reset search method to set original adapter for the original books
    public void resetSearch() {
        adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
        lvc.setAdapter(adapter);
    }
}
