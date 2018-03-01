package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
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

public class CheckedFragment extends Fragment {

    public CheckedFragment() {

    }

    ListView lvc;
    ArrayList<CheckedBook> checkedBooks;
    Firebase databasereference;
    TextView message;

    ArrayList<String> ddal = new ArrayList<>();
    ArrayList<String> ccal = new ArrayList<>();

    private CheckedBooksAdapter adapter;


    public String[] getCtits() {
        return ctits;
    }

    public void setCtits(String[] ctits) {
        this.ctits = ctits;
    }

    String[] ctits;


    public String[] getCds() {
        return cds;
    }

    public void setCds(String[] cds) {
        this.cds = cds;
    }

    View view;

    String cds[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Your bookshelf");
        view = inflater.inflate(R.layout.checkedbooks, container, false);

        load();

        lvc = (ListView) view.findViewById(R.id.listofcheckedbooks);
        checkedBooks = new ArrayList<CheckedBook>();
        message = (TextView) view.findViewById(R.id.nobookmessage);

        //set the search bar option
        setHasOptionsMenu(true);


        //if the checkedout arraylists are empty, then display a message
        if (getCds().length == 0 || getCtits().length == 0) {

            message.setText("You currently have no books checked out");
        }
        //if not then add each checked out book to an arraylist and make adapter
        else {
            for (int i = 0; i < ctits.length; i++) {

                //add checked book to arraylist
                checkedBooks.add(new CheckedBook(getCtits()[i], getCds()[i], R.drawable.bear));

            }
            //set adapter
            adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
            lvc.setAdapter(adapter);
        }
        return view;
    }

    private void load() {


      //  databasereference.child(CatalogFragment.titleofthebook).setValue();
        Firebase getbooksref = new Firebase("https://libeary-8d044.firebaseio.com/Books/");

        getbooksref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //collect all the books titles, authors, pagecounts, etc. and save in the arraylists
                ddal = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "duedate");
                ccal = collectitles((Map<String, Object>) dataSnapshot.getValue(), "title", Login.getUsername());

                //convert the arraylist to array and use setmethod
                setCds(ddal.toArray(new String[ddal.size()]));
                setCtits(ccal.toArray(new String[ccal.size()]));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private ArrayList<String> collectBookData(Map<String, Object> users, String fieldName) {
        ArrayList<String> information = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            information.add((String) singleUser.get(fieldName));
        }

        return information;
    }

    private ArrayList<String> collectitles(Map<String, Object> users, String fieldName, String usernamecheck) {
        ArrayList<String> information = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            if (singleUser.get("checkedoutto").toString().equals(usernamecheck)) {
                information.add((String) singleUser.get(fieldName));
            }
        }

        return information;
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
