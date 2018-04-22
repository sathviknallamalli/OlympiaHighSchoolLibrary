package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.SearchView;
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
import java.util.Map;

/**
 * Created by sathv on 11/28/2017.
 */

public class ReservedFragment extends Fragment {

    public ReservedFragment() {

    }

    ListView lvr;
    ArrayList<ReservedBook> reservedBooks;
    TextView message;

    private ReservedBooksAdapter adapter;
    ArrayList<String> information = new ArrayList<>();

    View view;

    String rtits[], rauths[];
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Reserved books");
        view = inflater.inflate(R.layout.reservedbooks, container, false);

        //set variables
        lvr = (ListView) view.findViewById(R.id.listofreservedbooks);
        reservedBooks = new ArrayList<ReservedBook>();
        message = (TextView) view.findViewById(R.id.noreserve);

        //enable search menu

        rtits = collectreservedbooks("title");
        rauths = collectreservedbooks("author");

        //check to see if reserved arraylists are empty or not
        if (rtits.length == 0 || rauths.length == 0) {

            //if emtpy, display message
            message.setText("You currently have no reserved books");

        } else {
            for (int i = 0; i < rtits.length; i++) {

                //create new reservedbook and retrieve fields by getting static arraylists and using get method
                reservedBooks.add(new ReservedBook(rtits[i], rauths[i], R.drawable.bear));

            }
            //set adapter to listview
            adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, reservedBooks);
            lvr.setAdapter(adapter);
        }
        return view;
    }

    public String[] collectreservedbooks(final String fieldname) {

        int val = Login.getTils().length;

        Firebase refname;

        for (int i = 0; i < val; i++) {
            refname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + Login.getTils()[i]);

            refname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);

                    SharedPreferences sp =view.getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    String emailofuser = sp.getString(getString(R.string.email), "");

                    if (map.get("reservations").contains(emailofuser)) {
                        information.add(map.get(fieldname));

                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : information) {
                            stringBuilder.append(s);
                            stringBuilder.append(", ");
                        }

                        SharedPreferences preferences = view.getContext().getSharedPreferences("gatherreserve", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(fieldname, stringBuilder.toString());
                        editor.commit();

                    }
                    //Log.d("DUDE", information.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });


        }
        SharedPreferences preferences = view.getContext().getSharedPreferences("gatherreserve", Context.MODE_PRIVATE);
        String wordstring = preferences.getString(fieldname, "");
        //  Log.d("DUDE", "AFTER" + wordstring);

        String[] temp = wordstring.split(", ");

        return temp;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activities, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();
        //set listener for searchbar
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    //if search bar input is empty, then reset search by displaying original listview
                    resetSearch();
                    return false;
                }

                //make filtered values from the original reservedBooks arraylist
                ArrayList<ReservedBook> filteredValues = new ArrayList<ReservedBook>(reservedBooks);

                for (int i = 0; i < reservedBooks.size(); i++) {
                    //if the title of each reserved book does not contains the searchbar input, then remove the book
                    if (!(reservedBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(reservedBooks.get(i));
                    }
                }

                //set the adapter with filteredvalues arraylist
                adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, filteredValues);
                lvr.setAdapter(adapter);

                return false;
            }
        };
        //set listener and hint
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");
    }

    //reset search method
    public void resetSearch() {
        //set adapter with original arraylists
        adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, reservedBooks);
        lvr.setAdapter(adapter);
    }


}
