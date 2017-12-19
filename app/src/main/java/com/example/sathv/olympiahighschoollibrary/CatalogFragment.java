package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    String[] title = {"1984", "candymakers", "TKAM", "lord of the flies", "the quants", "bringing down the house"};
    String[] author = {"George Owell", "Wendy Mass", "Harper Lee", "william golding", "stephen bradley", "ehtan somehitng"};
    int[] pageCount = {268, 464, 284, 158, 258, 698};
    String[] categories = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction"};
    String[] isbns = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599", "9780434011247"};
    String[] summaries = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.", "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym", "Bringing down the house summary"};
    static String [] statuses = {"0", "1", "1", "0", "0", "1"};

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

        final int picids[] = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house};

        lvBook = (ListView) view.findViewById(R.id.listofbooks);
        ArrayList<Book> books = new ArrayList<Book>();

        for (int i = 0; i < title.length; i++) {
            books.add(new Book(title[i], author[i], pageCount[i], picids[i], categories[i]));
        }
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);


        for (int i = 0; i < statuses.length; i++) {
            if(statuses[i].equals("0")) {
                statuses[i] = "Available";
            }
            else{
                statuses[i] = "Unavailable";
            }
        }

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {


                for (int i = 0; i < title.length; i++) {
                    if (position == i) {
                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);

                        titleofthebook = title[i];
                        authorofthebook = author[i];
                        category = categories[i];
                        pg = pageCount[i];
                        id = picids[i];
                        isbn = isbns[i];
                        summary = summaries[i];
                        setStatus(statuses[i], i);

                        pos = i;

                        Log.d("BAD", "title of the book in for loop" + titleofthebook);
                        Log.d("BAD", "author of the book in for loop" + authorofthebook);

                    }
                }
            }
        });

        return view;
    }
}

