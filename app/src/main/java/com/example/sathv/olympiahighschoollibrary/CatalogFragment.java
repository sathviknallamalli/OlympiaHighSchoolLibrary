package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sathv on 11/28/2017.
 */

public class CatalogFragment extends Fragment {

    public CatalogFragment() {

    }

    String[] title = {"1984", "candymakers", "TKAM", "lord of the flies", "the quants", "bringing down the house", "captain underpants"};
    String[] author = {"George Owell", "Wendy Mass", "Harper Lee", "william golding", "stephen bradley", "ehtan somehitng", "Dav pilkey"};
    int[] pageCount = {268, 464, 284, 158, 258, 698, 300};
    String[] categories = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction", "fiction"};
    String[] isbns = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599", "9780434011247", "9788543809717"};
    String[] summaries = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.",
            "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym",
            "Bringing down the house summary", "captain summary"};
    static String[] statuses = {"0", "1", "1", "0", "0", "1", "0"};

    private ListView lvBook;
    private BookAdapter adapter;
    public static String titleofthebook;
    public static String authorofthebook;
    public static String category;
    public static int pg;
    public static int id;
    public static String isbn;
    public static String summary;
    public static int pos;

    ArrayList<Book> books;

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status, int pos) {
        CatalogFragment.status = status;

        statuses[pos] = status;
    }

    public static String status;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.catalog, container, false);
        getActivity().setTitle("Catalog");

        setHasOptionsMenu(true);

        final int picids[] = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf,
                R.drawable.quants, R.drawable.house, R.drawable.cpd};

        lvBook = (ListView) view.findViewById(R.id.listofbooks);
        books = new ArrayList<Book>();


        for (int i = 0; i < title.length; i++) {
            books.add(new Book(title[i], author[i], pageCount[i], picids[i], categories[i]));
            lvBook.setTextFilterEnabled(true);

        }

        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);

        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals("0")) {
                statuses[i] = "Available";
            } else {
                statuses[i] = "Unavailable";
            }
        }

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {


                for (int i = 0; i < title.length; i++) {
                    if (position == i) {

                        titleofthebook = title[i];
                        authorofthebook = author[i];
                        category = categories[i];
                        pg = pageCount[i];
                        id = picids[i];
                        isbn = isbns[i];
                        summary = summaries[i];
                        setStatus(statuses[i], i);
                        pos = i;


                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);


                        Log.d("BAD", "title of the book in for loop" + titleofthebook);
                        Log.d("BAD", "author of the book in for loop" + authorofthebook);

                    }
                }
            }
        });


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

                ArrayList<Book> filteredValues = new ArrayList<Book>(books);

                for (int i = 0; i < books.size(); i++) {

                    if (!(books.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
                        filteredValues.remove(books.get(i));
                    }
                }

                adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, filteredValues);
                lvBook.setAdapter(adapter);


                return false;
            }
        };
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void resetSearch() {
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);
    }


}

