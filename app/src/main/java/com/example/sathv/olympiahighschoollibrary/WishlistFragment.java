package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;

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
    private WishlistBooksAdapter filteredvaluesadapter;

    public static String titleofthebook;
    public static String authorofthebook;
    public static String category;
    public static String pg;
    public static int id;
    public static String isbn;
    public static String summary;
    public static String status;

    CatalogFragment cf;

    static Book selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.wishlist, container, false);
        getActivity().setTitle("Wishlist");
        setHasOptionsMenu(true);

        cf = new CatalogFragment();

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
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        resetSearch();
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
                ArrayList<WishlistBook> filteredValues = new ArrayList<WishlistBook>(wishlistBooks);

                for (int i = 0; i < wishlistBooks.size(); i++) {
                    //if the title of each reserved book does not contains the searchbar input, then remove the book
                    if (!(wishlistBooks.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(wishlistBooks.get(i));
                        filteredvaluesadapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.customlayout, filteredValues);
                    }
                }

                //set the adapter with filteredvalues arraylist
                lvw.setAdapter(filteredvaluesadapter);

                return false;
            }
        };
        //set listener and hint
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");

        MenuItem lg = menu.findItem(R.id.item_logout);

        lg.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getView().getContext(), Login.class));
                return false;
            }
        });
    }

    //reset search method
    public void resetSearch() {
        //set adapter with original arraylists
        adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, wishlistBooks);
        lvw.setAdapter(adapter);
    }
}
