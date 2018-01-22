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

import java.util.ArrayList;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Reserved books");
        View view = inflater.inflate(R.layout.reservedbooks, container, false);

        //set variables
        lvr = (ListView) view.findViewById(R.id.listofreservedbooks);
        reservedBooks = new ArrayList<ReservedBook>();
        message = (TextView) view.findViewById(R.id.noreserve);

        //enable search menu
        setHasOptionsMenu(true);

        //check to see if reserved arraylists are empty or not
        if (BookInformation.reservedbooktitles.isEmpty() || BookInformation.reservedbookauthor.isEmpty()
                || BookInformation.reservedbookimages.isEmpty()) {

            //if emtpy, display message
            message.setText("You currently have no books reserved");

        } else {
            for (int i = 0; i < BookInformation.reservedbooktitles.size(); i++) {

                //create new reservedbook and retrieve fields by getting static arraylists and using get method
                reservedBooks.add(new ReservedBook(BookInformation.reservedbooktitles.get(i).toString(), BookInformation.reservedbookauthor.get(i).toString(),
                        (int) BookInformation.reservedbookimages.get(i)));

            }
            //set adapter to listview
            adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, reservedBooks);
            lvr.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
