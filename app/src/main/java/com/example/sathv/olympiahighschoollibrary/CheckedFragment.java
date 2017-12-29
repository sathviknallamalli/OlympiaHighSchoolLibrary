package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;

/**
 * Created by sathv on 11/28/2017.
 */

public class CheckedFragment extends Fragment {

    public CheckedFragment() {

    }

    ListView lvc;
    ArrayList<CheckedBook> checkedBooks;
    TextView message;

    private CheckedBooksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Checked out books");
        View view = inflater.inflate(R.layout.checkedbooks, container, false);

        lvc = (ListView) view.findViewById(R.id.listofcheckedbooks);
        checkedBooks = new ArrayList<CheckedBook>();
        message = (TextView) view.findViewById(R.id.nobookmessage);

        //set the search bar option
        setHasOptionsMenu(true);

        //if the checkedout arraylists are empty, then display a message
        if (BookInformation.checkedoutbooksimages.isEmpty() || BookInformation.checkedoutbookstitles.isEmpty()
                || BookInformation.checkedoutbooksdates.isEmpty()) {

            message.setText("You currently have no books checked out");
        }
        //if not then add each checked out book to an arraylist and make adapter
        else {
            for (int i = 0; i < BookInformation.checkedoutbookstitles.size(); i++) {

                //add checked book to arraylist
                checkedBooks.add(new CheckedBook(BookInformation.checkedoutbookstitles.get(i).toString(), BookInformation.checkedoutbooksdates.get(i).toString(),
                        (int) BookInformation.checkedoutbooksimages.get(i)));

            }
            //set adapter
            adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
            lvc.setAdapter(adapter);
        }
        return view;
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
