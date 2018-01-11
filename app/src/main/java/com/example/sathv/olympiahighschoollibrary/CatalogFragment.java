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
    static String[] title = {"1984", "candymakers", "to kill a mockingbird", "Lord of the Flies", "The Quants", "Bringing Down the House",
            "Captain Underpants", "City of Bones", "Red queen", "Selection", "Ungifted", "Physical science for eight grade with some absurdly long name"};
    String[] author = {"George Owell", "wendy Mass", "harper Lee", "william golding", "stephen bradley",
            "ehtan somehitng", "Dav pilkey", "cassandra clare", "victoria aveyard", "kierra cass", "gordon korman", "some dudde"};
    int[] pageCount = {268, 464, 284, 158, 258, 698, 300, 485, 388, 327, 280, 1523};
    String[] categories = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction", "fiction",
            "supernatural", "fiction", "fiction", "fiction", "textbook"};
    String[] isbns = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599",
            "9780434011247", "9788543809717", "978-1-41691428-0", "978-0-06-231063-7", "978-0-06-205994-9", "978-0-06-174266-8",
            "isbn number"};
    String[] summaries = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.",
            "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym",
            "Bringing down the house summary", "captain summary", "Suddenly able to see demons and the Darkhunters who are dedicated to returning them to their own dimension, fifteen-year-old Clary Fray is drawn into this " +
            "bizarre world when her mother disappears and Clary herself is almost killed by a monster.", "In a world divided by blood--those with common, Red blood serve the Silver-blooded elite," +
            "who are gifted with superhuman abilities--seventeen-year-old Mare, a Red, discovers she has an ability of her own.",
            "Sixteen-year-old America Singer is living in the caste-divided nation of Illea, which formed after the war that destroyed the United States. America is chosen to compete" +
                    "in the Selection--a contest to see which girl can win the heart of Illea's prince", "Due to an administrative mix-up, troublemaker Donovan Curtis is sent to the Academy of Scholastic Distinction," +
            "a special program for gifted and talented students, after pulling a major prank at middle school.",
            "the summary for the physical science eight grade textbook"};
    static String[] statuses = {"0", "1", "1", "0", "0", "1", "0", "1", "0", "1", "1", "1"};
    public int[] picids = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted, R.drawable.bear};
    static String[] addedornot = {"Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist"};

    //the first set of arrays will get manipulated during the search, so the original copy is kept too
    static String[] titleorig = {"1984", "candymakers", "to kill a mockingbird", "Lord of the Flies", "The Quants", "Bringing Down the House",
            "Captain Underpants", "City of Bones", "Red queen", "Selection", "Ungifted", "Physical science for eight grade"};
    String[] authororig = {"George Owell", "wendy Mass", "harper Lee", "william golding", "stephen bradley",
            "ehtan somehitng", "Dav pilkey", "cassandra clare", "victoria aveyard", "kierra cass", "gordon korman", "some dudde"};
    int[] pageCountorig = {268, 464, 284, 158, 258, 698, 300, 485, 388, 327, 280, 1523};
    String[] categoriesorig = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction", "fiction",
            "supernatural", "fiction", "fiction", "fiction", "textbook"};
    String[] isbnsorig = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599",
            "9780434011247", "9788543809717", "978-1-41691428-0", "978-0-06-231063-7", "978-0-06-205994-9", "978-0-06-174266-8",
            "isbn number"};
    String[] summariesorig = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.",
            "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym",
            "Bringing down the house summary", "captain summary", "Suddenly able to see demons and the Darkhunters who are dedicated to returning them to their own dimension, fifteen-year-old Clary Fray is drawn into this " +
            "bizarre world when her mother disappears and Clary herself is almost killed by a monster.", "In a world divided by blood--those with common, Red blood serve the Silver-blooded elite," +
            "who are gifted with superhuman abilities--seventeen-year-old Mare, a Red, discovers she has an ability of her own.",
            "Sixteen-year-old America Singer is living in the caste-divided nation of Illea, which formed after the war that destroyed the United States. America is chosen to compete" +
                    "in the Selection--a contest to see which girl can win the heart of Illea's prince", "Due to an administrative mix-up, troublemaker Donovan Curtis is sent to the Academy of Scholastic Distinction," +
            "a special program for gifted and talented students, after pulling a major prank at middle school.",
            "the summary for the physical science eight grade textbook"};
    static String[] statusesorig = {"0", "1", "1", "0", "0", "1", "0", "1", "0", "1", "1", "1"};
    public int[] picidsorig = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted, R.drawable.bear};
    static String[] addedornotorig = {"Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist"};

    private ListView lvBook;
    private BookAdapter adapter;
    private BookAdapter filteredvaluesadapter;

    //static variables to access so for the bookinformation activity
    public static String titleofthebook;
    public static String authorofthebook;
    public static String category;
    public static int pg;
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

        //make a Book for each title,author,pagecount,isbb,status,image id and load into arraylist
        for (int i = 0; i < title.length; i++) {
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
                        pageCount = removeEltInt(pageCount, i);
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

