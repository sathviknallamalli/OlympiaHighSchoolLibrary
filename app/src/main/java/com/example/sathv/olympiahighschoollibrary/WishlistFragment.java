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
        View view = inflater.inflate(R.layout.wishlist, container, false);
        getActivity().setTitle("Wishlist");

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
            adapter = new WishlistBooksAdapter(getActivity().getApplicationContext(), R.layout.wishlistlayout, BookAdapter.wishlistbooks);
            lvw.setAdapter(adapter);
        }
        return view;
    }
}
