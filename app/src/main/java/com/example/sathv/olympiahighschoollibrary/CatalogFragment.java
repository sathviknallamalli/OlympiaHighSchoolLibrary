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
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by sathv on 11/28/2017.
 */

public class CatalogFragment extends Fragment {

    public CatalogFragment() {

    }

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] title = Login.getTils();
    String[] author = Login.getAuths();
    String[] pageCount = Login.getPgs();
    String[] categories = Login.getCs();
    String[] isbns = Login.getIss();
    String[] summaries = Login.getSs();
    static String[] statuses = Login.getStatuss();
    public int[] picids = {R.drawable.candymakers, R.drawable.wheights, R.drawable.lotf, R.drawable.frank, R.drawable.selection
            , R.drawable.owellbook, R.drawable.cpd, R.drawable.rq, R.drawable.hamelt, R.drawable.ungifted, R.drawable.pp,
            R.drawable.ts, R.drawable.af, R.drawable.cb, R.drawable.tkam, R.drawable.hfin, R.drawable.quants, R.drawable.farenheit,
            R.drawable.odys, R.drawable.dc, R.drawable.house, R.drawable.rj, R.drawable.gg, R.drawable.three, R.drawable.giver,
            R.drawable.ofmm, R.drawable.awake};
    static String[] addedornot = new String[Login.getTils().length];
    String[] reservations = Login.getResses();

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
    public static String reservedlist;

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

    public Book selected;

    FirebaseAuth mAuth;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.catalog, container, false);
        getActivity().setTitle("Library Catalog");


        mAuth = FirebaseAuth.getInstance();
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
            } else if (statuses[i].equals("1")) {
                statuses[i] = "Unavailable";
            }
        }
        for (int i = 0; i < addedornot.length; i++) {
            addedornot[i] = "Add to wishlist";
        }


        //make a Book for each title,author,pagecount,isbb,status,image id and load into arraylist
        for (int i = 0; i < title.length; i++) {
            books.add(new Book((title[i]), (author[i]), pageCount[i], picids[i], (categories[i]), addedornot[i], isbns[i],
                    statuses[i], summaries[i], reservations[i]));
            // bookstwo.add(new Book((titleorig[i]), (authororig[i]), pageCountorig[i], picidsorig[i], (categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            lvBook.setTextFilterEnabled(true);

        }

        //set adapter for listview
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);

        //on click listener for list view when a row is clicked
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View listview, int position, long val) {

                int i = position;

                //based on what book is

                titleofthebook = title[i];
                authorofthebook = (author[i]);
                category = (categories[i]);
                pg = pageCount[i];
                id = picids[i];
                isbn = isbns[i];
                summary = summaries[i];
                setStatus(statuses[i], i);
                reservedlist = reservations[i];
                pos = i;

                selected = new Book((title[i]), (author[i]), pageCount[i], picids[i], (categories[i]), addedornot[i], isbns[i],
                        statuses[i], summaries[i], reservations[i]);


                Intent appInfo = new Intent(view.getContext(), BookInformation.class);

                //    BookInformation bi = new BookInformation();
                //  bi.set(selected);

                startActivity(appInfo);

            }
        });


        return view;
    }


    //the options menu that contains search action


    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        resetSearch();
    }

    /*@Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        resetSearch();
    }*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.activities, menu);
        resetSearch();
        //inflater.inflate(R.menu.activities, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();


        //the listener
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //resetSearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    //if the search bar is empty, load the original listview using the resetsearch method that is defined below
                    resetSearch();
                    return false;
                } else {

                    //filtered values of book based on the search
                    final ArrayList<Book> filteredValues = new ArrayList<Book>(books);

                    for (int i = 0; i < books.size(); i++) {

                        //if the title of each book does not contaain the string from the search bar, then delete it from the listview
                        //and remove from the filtered values arraylist
                        if (!(books.get(i).getTitle().toLowerCase()).contains(newText.toLowerCase())) {

                            //remove each field
                            filteredValues.remove(books.get(i));
                            filteredvaluesadapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, filteredValues);
                        }
                    }

                    lvBook.setAdapter(filteredvaluesadapter);

                    //then declare the onclick listener if a book is clicked after they searched
                    lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {

                            int i = position;

                            //assign the static variables to the appropriate information retrieved from filtered values arraylist
                            titleofthebook = filteredValues.get(i).getTitle();
                            authorofthebook = filteredValues.get(i).getAuthor();
                            category = filteredValues.get(i).getCategory();
                            pg = filteredValues.get(i).getPageCount();
                            id = filteredValues.get(i).getImageid();
                            isbn = filteredValues.get(i).getIsbn();
                            summary = filteredValues.get(i).getSummary();
                            setStatus(filteredValues.get(i).getStatus(), i);
                            reservedlist = (filteredValues.get(i).getReservations());
                            pos = i;

                            //similarly also start the bookinformation activity
                            Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                            startActivityForResult(appInfo, i);

                        }
                    });
                    return false;
                }
            }
        };

        MenuItem lg = menu.findItem(R.id.item_logout);

        lg.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getView().getContext(), Login.class));
                return false;
            }
        });

        //set the appropriate listener and hint for searchbar
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");
    }

    //reset search method used when the search bar is empty and the originnal list view is set with orig arrays
    public void resetSearch() {
        //books two was originally set with orig arrays
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);

        //onclick listener set similarly to open the book info class
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {

                int i = position;

                //static variables are assign the appropriate fields based on array retrieval
                titleofthebook = title[i];
                authorofthebook = author[i];
                category = categories[i];
                pg = pageCount[i];
                id = picids[i];
                isbn = isbns[i];
                summary = summaries[i];
                setStatus(statuses[i], i);
                reservedlist = reservations[i];
                pos = i;

                //book info activity started with all the details of the book
                Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                startActivityForResult(appInfo, i);

            }
        });


    }
}
