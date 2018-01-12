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

public class WishlistFragment extends Fragment {

    public WishlistFragment() {

    }

    ListView lvw;
    ArrayList<WishlistBook> wishlistBooks;
    TextView message;

    private WishlistBooksAdapter adapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.wishlist, container, false);
        getActivity().setTitle("Wishlist");
        setHasOptionsMenu(true);

        //assign varible
        lvw = (ListView) view.findViewById(R.id.listofwishlistbooks);
        wishlistBooks = new ArrayList<WishlistBook>();
        message = (TextView) view.findViewById(R.id.nowishlist);

        //if wishlistbooks array is empty, then display message to no books in wishlist
        if (BookAdapter.wishlistbooks.isEmpty()) {
            message.setText("You currently have no books in your wishlist");

        } else {
            for (int i = 0; i < BookAdapter.wishlistbooks.size(); i++) {

                //add each book into the wishlistBooks arraylist
                wishlistBooks.add(BookAdapter.wishlistbooks.get(i));
            }
            //set as adapter
            adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, wishlistBooks);
            lvw.setAdapter(adapter);
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
                ArrayList<WishlistBook> filteredValues = new ArrayList<WishlistBook>(wishlistBooks);

                for (int i = 0; i < wishlistBooks.size(); i++) {
                    //if the title of each reserved book does not contains the searchbar input, then remove the book
                    if (!(wishlistBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(wishlistBooks.get(i));
                    }
                }

                //set the adapter with filteredvalues arraylist
                adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, filteredValues);
                lvw.setAdapter(adapter);

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
        adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, wishlistBooks);
        lvw.setAdapter(adapter);
    }
}
