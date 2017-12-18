package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private ListView lvBook;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.catalog, container, false);
        getActivity().setTitle("Catalog");

        int picids[] = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house};

        lvBook = (ListView) view.findViewById(R.id.listofbooks);
        ArrayList<Book> books = new ArrayList<Book>();

        for (int i = 0; i < title.length; i++) {
            books.add(new Book(title[i], author[i], pageCount[i], picids[i]));
        }
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);

       /* lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent activities = new Intent(getActivity().getApplication(), BookInformation.class);
                startActivity(activities);

                String selectedBookTitle = lvBook.getItemAtPosition(i).toString();
                Log.d("thign", "onItemClick: " + selectedBookTitle);
            }
        });*/

        return view;
    }
}

