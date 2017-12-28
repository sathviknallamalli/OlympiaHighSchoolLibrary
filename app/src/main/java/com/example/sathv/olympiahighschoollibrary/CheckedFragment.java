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

        setHasOptionsMenu(true);

        if (BookInformation.checkedoutbooksimages.isEmpty() || BookInformation.checkedoutbookstitles.isEmpty()
                || BookInformation.checkedoutbooksdates.isEmpty()) {
            Log.d("BAD", "arraylists are empty in checked fragment");

            message.setText("You currently have no books checked out");


        } else {
            for (int i = 0; i < BookInformation.checkedoutbookstitles.size(); i++) {

                checkedBooks.add(new CheckedBook(BookInformation.checkedoutbookstitles.get(i).toString(), BookInformation.checkedoutbooksdates.get(i).toString(),
                        (int) BookInformation.checkedoutbooksimages.get(i)));

                Log.d("BAD", "arraylist are full");


            }

            adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
            lvc.setAdapter(adapter);
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    resetSearch();
                    return false;
                }

                ArrayList<CheckedBook> filteredValues = new ArrayList<CheckedBook>(checkedBooks);

                for (int i = 0; i < checkedBooks.size(); i++) {

                    if (!(checkedBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(checkedBooks.get(i));
                    }
                }

                adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, filteredValues);
                lvc.setAdapter(adapter);

                return false;
            }
        };
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");

        super.onCreateOptionsMenu(menu, inflater);

    }

    public void resetSearch() {
        adapter = new CheckedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforchecked, checkedBooks);
        lvc.setAdapter(adapter);
    }
}
