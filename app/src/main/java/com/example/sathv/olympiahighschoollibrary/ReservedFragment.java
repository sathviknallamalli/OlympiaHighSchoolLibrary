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

        lvr = (ListView) view.findViewById(R.id.listofreservedbooks);
        reservedBooks = new ArrayList<ReservedBook>();
        message = (TextView) view.findViewById(R.id.noreserve);

        setHasOptionsMenu(true);

        if (BookInformation.reservedbooktitles.isEmpty() || BookInformation.reservedbookauthor.isEmpty()
                || BookInformation.reservedbookimages.isEmpty()) {
            Log.d("BAD", "arraylists are empty in checked fragment");

            message.setText("You currently have no books reserved");


        } else {
            for (int i = 0; i < BookInformation.reservedbooktitles.size(); i++) {

                reservedBooks.add(new ReservedBook(BookInformation.reservedbooktitles.get(i).toString(), BookInformation.reservedbookauthor.get(i).toString(),
                        (int) BookInformation.reservedbookimages.get(i)));

                Log.d("BAD", "arraylist are full");


            }

            adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, reservedBooks);
            lvr.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activities, menu);
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

                ArrayList<ReservedBook> filteredValues = new ArrayList<ReservedBook>(reservedBooks);

                for (int i = 0; i < reservedBooks.size(); i++) {

                    if (!(reservedBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(reservedBooks.get(i));
                    }
                }

                adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, filteredValues);
                lvr.setAdapter(adapter);

                return false;
            }
        };
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void resetSearch() {
        adapter = new ReservedBooksAdapter(getActivity().getApplicationContext(), R.layout.itemforreserved, reservedBooks);
        lvr.setAdapter(adapter);
    }
}
