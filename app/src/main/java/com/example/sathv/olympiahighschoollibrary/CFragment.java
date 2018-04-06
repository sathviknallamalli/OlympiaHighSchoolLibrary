package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CFragment extends Fragment {



//load all the information for book display and put into arrays; titles, authors, pagecount, isbns, summaries, statuses, picids, addedornot

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] title = Login.getTils();
    String[] author = Login.getAuths();
    String[] pageCount = Login.getPgs();
    String[] categories = Login.getCs();
    String[] isbns = Login.getIss();
    String[] summaries = Login.getSs();
    static String[] statuses = Login.getStatuss();
    public int[] picids = {R.drawable.candymakers, R.drawable.msd, R.drawable.wheights, R.drawable.lotf, R.drawable.frank, R.drawable.selection
            , R.drawable.owellbook, R.drawable.cpd, R.drawable.rq, R.drawable.hamelt, R.drawable.ungifted, R.drawable.pp,
            R.drawable.ts, R.drawable.af, R.drawable.cb, R.drawable.tkam, R.drawable.hfin, R.drawable.quants, R.drawable.farenheit,
            R.drawable.odys, R.drawable.dc, R.drawable.house, R.drawable.rj, R.drawable.gg, R.drawable.three, R.drawable.giver,
            R.drawable.ofmm, R.drawable.awake};
    static String[] addedornot = new String[Login.getTils().length];

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] titleorig = Login.getTils();
    String[] authororig = Login.getAuths();
    String[] pageCountorig = Login.getPgs();
    String[] categoriesorig = Login.getCs();
    String[] isbnsorig = Login.getIss();
    String[] summariesorig = Login.getSs();
    static String[] statusesorig = Login.getStatuss();
    public int[] picidsorig = {R.drawable.candymakers, R.drawable.msd, R.drawable.wheights, R.drawable.lotf, R.drawable.frank, R.drawable.selection
            , R.drawable.owellbook, R.drawable.cpd, R.drawable.rq, R.drawable.hamelt, R.drawable.ungifted, R.drawable.pp,
            R.drawable.ts, R.drawable.af, R.drawable.cb, R.drawable.tkam, R.drawable.hfin, R.drawable.quants, R.drawable.farenheit,
            R.drawable.odys, R.drawable.dc, R.drawable.house, R.drawable.rj, R.drawable.gg, R.drawable.three, R.drawable.giver,
            R.drawable.ofmm, R.drawable.awake};
    static String[] addedornotorig = new String[Login.getTils().length];

    private ListView lvBook;
    private BookAdapter adapter;
    private BookAdapter filteredvaluesadapter;

    //static variables to access so for the bookinformation activity
    public static String titleofthebook;
    public static String authorofthebook;
    public static String category;
    public static String pg;
    public static int id;
    public static String isbn;
    public static String summary;
    public static int pos;

    //arraylist to add all the books into
    static ArrayList<Book> books, bookstwo;

    //getter and setter for status because it will manipulate
    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status, int pos) {
        CatalogFragment.status = status;

        statuses[pos] = status;
    }

    public static String status;

    static Book selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.catalog, container, false);
        getActivity().setTitle("Library Catalog");

        //allow to display search option
        setHasOptionsMenu(true);

        //assign
        lvBook = (ListView) view.findViewById(R.id.listofbooks);
        books = new ArrayList<Book>();
        bookstwo = new ArrayList<Book>();

        //translate the binary in statuses to dispplay for user into text
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals("0")) {
                statuses[i] = "Available";
                statusesorig[i] = "Available";
            } else if (statuses[i].equals("1")) {
                statuses[i] = "Unavailable";
                statusesorig[i] = "Unavailable";
            }
        }
        for (int i = 0; i < addedornotorig.length; i++) {
            addedornotorig[i] = "Add to wishlist";
            addedornot[i] = "Add to wishlist";
        }


        //make a Book for each title,author,pagecount,isbb,status,image id and load into arraylist
        for (int i = 0; i < titleorig.length; i++) {
            books.add(new Book((titleorig[i]), (authororig[i]), pageCountorig[i], picidsorig[i], (categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            bookstwo.add(new Book((titleorig[i]), (authororig[i]), pageCountorig[i], picidsorig[i], (categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            lvBook.setTextFilterEnabled(true);

        }

        //set adapter for listview
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);

        //on click listener for list view when a row is clicked
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {

                //based on what book is selected, the appropriate information is retrieved and assigned to the static variables
                for (int i = 0; i < title.length; i++) {
                    if (position == i) {

                        titleofthebook = title[i];
                        authorofthebook = (author[i]);
                        category = (categories[i]);
                        pg = pageCount[i];
                        id = picids[i];
                        isbn = isbns[i];
                        summary = summaries[i];
                        setStatus(statuses[i], i);
                        pos = i;

                        //then start the bookinfo activity
                        selected = books.get(position);
                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);

                    }
                }
            }
        });

        return view;
    }
}
