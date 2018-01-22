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

import java.util.ArrayList;

/**
 * Created by sathv on 11/28/2017.
 */

public class CatalogFragment extends Fragment {

    public CatalogFragment() {

    }

    //load all the information for book display and put into arrays; titles, authors, pagecount, isbns, summaries, statuses, picids, addedornot

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] title = Login.getTils();
    String[] author = Login.getAuths();
    String[] pageCount = Login.getPgs();
    String[] categories = Login.getCs();
    String[] isbns = Login.getIss();
    String[] summaries = Login.getSs();
    static String[] statuses = Login.getStatuss();
    public int[] picids = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted, R.drawable.bear,
            R.drawable.bear};
    static String[] addedornot = new String[title.length];

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] titleorig = Login.getTils();
    String[] authororig = Login.getAuths();
    String[] pageCountorig = Login.getPgs();
    String[] categoriesorig = Login.getCs();
    String[] isbnsorig = Login.getIss();
    String[] summariesorig = Login.getSs();
    static String[] statusesorig = Login.getStatuss();
    public int[] picidsorig = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted, R.drawable.bear,
            R.drawable.bear, R.drawable.bear, R.drawable.bear};
    static String[] addedornotorig = new String[title.length];

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

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
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
            books.add(new Book(capitalzeTitle(titleorig[i]), capitalizeauthor(authororig[i]), pageCountorig[i], picidsorig[i], capitalizeauthor(categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            bookstwo.add(new Book(capitalzeTitle(titleorig[i]), capitalizeauthor(authororig[i]), pageCountorig[i], picidsorig[i], capitalizeauthor(categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
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

                        titleofthebook = capitalzeTitle(title[i]);
                        authorofthebook = capitalizeauthor(author[i]);
                        category = capitalizeauthor(categories[i]);
                        pg = pageCount[i];
                        id = picids[i];
                        isbn = isbns[i];
                        summary = summaries[i];
                        setStatus(statuses[i], i);
                        pos = i;

                        //then start the bookinfo activity
                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);

                    }
                }
            }
        });

        return view;
    }


    //the options menu that contains search action
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.activities, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();
        //the listener
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    //if the search bar is empty, load the original listview using the resetsearch method that is defined below
                    resetSearch();
                    return false;
                }

                //filtered values of book based on the search
                final ArrayList<Book> filteredValues = new ArrayList<Book>(books);

                for (int i = 0; i < books.size(); i++) {

                    //if the title of each book does not contaain the string from the search bar, then delete it from the listview
                    //and remove from the filtered values arraylist
                    if (!(books.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {

                        //remove each field
                        filteredValues.remove(books.get(i));
                        title = removeeltString(title, i);
                        author = removeeltString(author, i);
                        categories = removeeltString(categories, i);
                        pageCount = removeeltString(pageCount, i);
                        picids = removeEltInt(picids, i);
                        isbns = removeeltString(isbns, i);
                        summaries = removeeltString(summaries, i);
                        statuses = removeeltString(statuses, i);
                        addedornot = removeeltString(addedornot, i);
                        //then set the adapter for filteredvalues
                        filteredvaluesadapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, filteredValues);
                    }
                }

                lvBook.setAdapter(filteredvaluesadapter);

                //then declare the onclick listener if a book is clicked after they searched
                lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {

                        for (int i = 0; i < title.length; i++) {
                            if (position == i) {

                                //assign the static variables to the appropriate information retrieved from filtered values arraylist
                                titleofthebook = capitalzeTitle(filteredValues.get(i).title);
                                authorofthebook = capitalizeauthor(filteredValues.get(i).author);
                                category = capitalizeauthor(filteredValues.get(i).category);
                                pg = filteredValues.get(i).pageCount;
                                id = filteredValues.get(i).imageid;
                                isbn = filteredValues.get(i).isbn;
                                summary = filteredValues.get(i).summary;
                                setStatus(filteredValues.get(i).status, i);
                                pos = i;

                                //similarly also start the bookinformation activity
                                Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                                startActivityForResult(appInfo, i);
                            }
                        }
                    }
                });
                return false;
            }
        };
        //set the appropriate listener and hint for searchbar
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");
    }

    //reset search method used when the search bar is empty and the originnal list view is set with orig arrays
    public void resetSearch() {
        //books two was originally set with orig arrays
        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, bookstwo);
        lvBook.setAdapter(adapter);

        //onclick listener set similarly to open the book info class
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {

                for (int i = 0; i < title.length; i++) {
                    if (position == i) {

                        //static variables are assign the appropriate fields based on array retrieval
                        titleofthebook = capitalzeTitle(titleorig[i]);
                        authorofthebook = capitalizeauthor(authororig[i]);
                        category = capitalizeauthor(categoriesorig[i]);
                        pg = pageCountorig[i];
                        id = picidsorig[i];
                        isbn = isbnsorig[i];
                        summary = summariesorig[i];
                        setStatus(statusesorig[i], i);
                        pos = i;

                        //book info activity started with all the details of the book
                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);
                    }
                }
            }
        });


    }

    //remove an integer elemtn from an array
    public int[] removeEltInt(int[] arr, int remIndex) {
        for (int i = remIndex; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        return arr;
    }

    //remove a string element from an array
    public String[] removeeltString(String[] arr, int remIndex) {
        for (int i = remIndex; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        return arr;
    }

    //capitalize the title string by splitting the spaces and uppercasing the first letter of the word
    public String capitalzeTitle(String fulltitle) {

        String words[] = fulltitle.split(" ");

        String modifiedtitle = "";

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("of") || words[i].equals("the") || words[i].equals("a") || words[i].equals("an") || words[i].equals("and")
                    || words[i].equals("on")) {
                modifiedtitle += words[i] + " ";
            } else {
                words[i] = upperCaseFirst(words[i]);
                modifiedtitle += words[i] + " ";
            }
        }
        return modifiedtitle;
    }

    //capitalizing the author name
    public String capitalizeauthor(String fullauthor) {
        String words[] = fullauthor.split(" ");
        String modifiedauthor = "";

        for (int i = 0; i < words.length; i++) {
            words[i] = upperCaseFirst(words[i]);
            modifiedauthor += words[i] + " ";
        }
        return modifiedauthor;
    }

    //uppercase method that will uppercase the first letter of the word
    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }


}

