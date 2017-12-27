package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.wishlist, container, false);

        getActivity().setTitle("Wishlist");

        lvw = (ListView) view.findViewById(R.id.listofwishlistbooks);
        wishlistBooks = new ArrayList<WishlistBook>();
        message = (TextView) view.findViewById(R.id.nowishlist);

        //setHasOptionsMenu(true);

        if (BookAdapter.wishlistbooks.isEmpty()) {
            Log.d("BAD", "arraylists are empty in wishlist fragment");

            message.setText("You currently have no books in your wishlist");


        } else {
            for (int i = 0; i < BookAdapter.wishlistbooks.size(); i++) {

                wishlistBooks.add(BookAdapter.wishlistbooks.get(i));

                Log.d("BAD", "arraylist are full in wishlist");

            }

            adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, wishlistBooks);
            lvw.setAdapter(adapter);
        }

        return view;
    }


}
