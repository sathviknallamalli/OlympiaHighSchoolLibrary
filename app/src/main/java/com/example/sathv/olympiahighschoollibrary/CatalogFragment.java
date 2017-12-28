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

    static String[] title = {"1984", "candymakers", "to kill a mockingbird", "Lord of the Flies", "The Quants", "Bringing Down the House",
            "Captain Underpants", "City of Bones", "Red queen", "Selection", "Ungifted"};
    String[] author = {"George Owell", "wendy Mass", "harper Lee", "william golding", "stephen bradley",
            "ehtan somehitng", "Dav pilkey", "cassandra clare", "victoria aveyard", "kierra cass", "gordon korman"};
    int[] pageCount = {268, 464, 284, 158, 258, 698, 300, 485, 388, 327, 280};
    String[] categories = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction", "fiction",
            "supernatural", "fiction", "fiction", "fiction"};
    String[] isbns = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599",
            "9780434011247", "9788543809717", "978-1-41691428-0", "978-0-06-231063-7", "978-0-06-205994-9", "978-0-06-174266-8"};
    String[] summaries = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.",
            "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym",
            "Bringing down the house summary", "captain summary", "Suddenly able to see demons and the Darkhunters who are dedicated to returning them to their own dimension, fifteen-year-old Clary Fray is drawn into this " +
            "bizarre world when her mother disappears and Clary herself is almost killed by a monster.", "In a world divided by blood--those with common, Red blood serve the Silver-blooded elite," +
            "who are gifted with superhuman abilities--seventeen-year-old Mare, a Red, discovers she has an ability of her own.",
            "Sixteen-year-old America Singer is living in the caste-divided nation of Illea, which formed after the war that destroyed the United States. America is chosen to compete" +
                    "in the Selection--a contest to see which girl can win the heart of Illea's prince", "Due to an administrative mix-up, troublemaker Donovan Curtis is sent to the Academy of Scholastic Distinction," +
            "a special program for gifted and talented students, after pulling a major prank at middle school."};
    static String[] statuses = {"0", "1", "1", "0", "0", "1", "0", "1", "0", "1", "1"};

    public int[] picids = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted};

    static String[] addedornot = {"Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist"};

    //ORIGINAL DUPLICATES
    String[] titleorig = {"1984", "candymakers", "to kill a mockingbird", "Lord of the Flies", "The Quants", "Bringing Down the House",
            "Captain Underpants", "City of Bones", "Red queen", "Selection", "Ungifted"};
    String[] authororig = {"George Owell", "Wendy Mass", "Harper Lee", "william golding", "stephen bradley",
            "ehtan somehitng", "Dav pilkey", "cassandra clare", "victoria aveyard", "kierra cass", "gordon korman"};
    int[] pageCountorig = {268, 464, 284, 158, 258, 698, 300, 485, 388, 327, 280};
    String[] categoriesorig = {"fiction", "fiction", "historical fiction", "fiction", "nonfiction", "nonfiction", "fiction",
            "supernatural", "fiction", "fiction", "fiction"};
    String[] isbnsorig = {"9780451518651", "9781306765190", "9788373015470", "9780807218181", "9781847940599",
            "9780434011247", "9788543809717", "978-1-41691428-0", "978-0-06-231063-7", "978-0-06-205994-9", "978-0-06-174266-8"};
    String[] summariesorig = {"In George Orwell's 1984, Winston Smith wrestles with oppression in Oceania, a place where the Party scrutinizes human actions with ever-watchful Big Brother.",
            "candymakers synonyms", "tkam summary", "ord of the flies summmary", "the quants synonym",
            "Bringing down the house summary", "captain summary", "Suddenly able to see demons and the Darkhunters who are dedicated to returning them to their own dimension, fifteen-year-old Clary Fray is drawn into this " +
            "bizarre world when her mother disappears and Clary herself is almost killed by a monster.", "In a world divided by blood--those with common, Red blood serve the Silver-blooded elite," +
            "who are gifted with superhuman abilities--seventeen-year-old Mare, a Red, discovers she has an ability of her own.",
            "Sixteen-year-old America Singer is living in the caste-divided nation of Illea, which formed after the war that destroyed the United States. America is chosen to compete" +
                    "in the Selection--a contest to see which girl can win the heart of Illea's prince", "Due to an administrative mix-up, troublemaker Donovan Curtis is sent to the Academy of Scholastic Distinction," +
            "a special program for gifted and talented students, after pulling a major prank at middle school."};
    String[] statusesorig = {"0", "1", "1", "0", "0", "1", "0", "1", "0", "1", "1"};

    public int[] picidsorig = {R.drawable.owellbook, R.drawable.candymakers, R.drawable.tkam, R.drawable.lotf, R.drawable.quants, R.drawable.house
            , R.drawable.cpd, R.drawable.cb, R.drawable.rq, R.drawable.selection, R.drawable.ungifted};

    static String[] addedornotorig = {"Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist", "Add to wishlist"};


    private ListView lvBook;
    private BookAdapter adapter;
    private BookAdapter adaptertwo;
    public static String titleofthebook;
    public static String authorofthebook;
    public static String category;
    public static int pg;
    public static int id;
    public static String isbn;
    public static String summary;
    public static int pos;

    // ArrayList<Integer> picids = new ArrayList<Integer>();

    static ArrayList<Book> books, bookstwo;

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

        lvBook = (ListView) view.findViewById(R.id.listofbooks);
        books = new ArrayList<Book>();
        bookstwo = new ArrayList<Book>();

        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals("0")) {
                statuses[i] = "Available";
                statusesorig[i] = "Available";
            } else if (statuses[i].equals("1")) {
                statuses[i] = "Unavailable";
                statusesorig[i] = "Unavailable";
            }
        }

        for (int i = 0; i < title.length; i++) {
            books.add(new Book(capitalzeTitle(titleorig[i]), capitalizeauthor(authororig[i]), pageCountorig[i], picidsorig[i], capitalizeauthor(categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            bookstwo.add(new Book(capitalzeTitle(titleorig[i]), capitalizeauthor(authororig[i]), pageCountorig[i], picidsorig[i], capitalizeauthor(categoriesorig[i]), addedornotorig[i], isbns[i], statuses[i], summaries[i]));
            lvBook.setTextFilterEnabled(true);

        }

        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, books);
        lvBook.setAdapter(adapter);


        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {


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
        //inflater.inflate(R.menu.activities, menu);
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

                final ArrayList<Book> filteredValues = new ArrayList<Book>(books);

                for (int i = 0; i < books.size(); i++) {

                    if (!(books.get(i).title.toLowerCase()).contains(newText.toLowerCase())) {
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
                        adaptertwo = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, filteredValues);
                    }
                }


                lvBook.setAdapter(adaptertwo);

                lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {


                        for (int i = 0; i < title.length; i++) {
                            if (position == i) {

                                titleofthebook = capitalzeTitle(filteredValues.get(i).title);
                                authorofthebook = capitalizeauthor(filteredValues.get(i).author);
                                category = capitalizeauthor(filteredValues.get(i).category);
                                pg = filteredValues.get(i).pageCount;
                                id = filteredValues.get(i).imageid;
                                isbn = filteredValues.get(i).isbn;
                                summary = filteredValues.get(i).summary;
                                setStatus(filteredValues.get(i).status, i);
                                pos = i;

                                Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                                startActivityForResult(appInfo, i);


                                Log.d("BAD", "title of the book in for loop for search" + titleofthebook);
                                Log.d("BAD", "author of the book in for loop for search" + authorofthebook);

                            }
                        }
                    }
                });


                return false;
            }
        };
        searchView.setOnQueryTextListener(listener);
        searchView.setQueryHint("Search a book by title");

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void resetSearch() {

        adapter = new BookAdapter(getActivity().getApplicationContext(), R.layout.customlayout, bookstwo);
        lvBook.setAdapter(adapter);

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long val) {


                /*for (int i = 0; i < statuses.length; i++) {
                    if (statuses[i].equals("0")) {
                        statuses[i] = "Available";
                        statusesorig[i] = "Available";
                    } else if (statuses[i].equals("1")) {
                        statuses[i] = "Unavailable";
                        statusesorig[i] = "Unavailable";
                    }
                }*/

                for (int i = 0; i < title.length; i++) {
                    if (position == i) {

                        titleofthebook = capitalzeTitle(titleorig[i]);
                        authorofthebook = capitalizeauthor(authororig[i]);
                        category = capitalizeauthor(categoriesorig[i]);
                        pg = pageCountorig[i];
                        id = picidsorig[i];
                        isbn = isbnsorig[i];
                        summary = summariesorig[i];
                        setStatus(statusesorig[i], i);
                        pos = i;


                        Intent appInfo = new Intent(view.getContext(), BookInformation.class);
                        startActivityForResult(appInfo, i);


                        Log.d("BAD", "title of the book in for loop reset search" + titleofthebook);
                        Log.d("BAD", "author of the book in for loop reset search" + authorofthebook);

                    }
                }
            }
        });


    }

    public int[] removeEltInt(int[] arr, int remIndex) {
        for (int i = remIndex; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        return arr;
    }

    public String[] removeeltString(String[] arr, int remIndex) {
        for (int i = remIndex; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        return arr;
    }

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

    public String capitalizeauthor(String fullauthor) {
        String words[] = fullauthor.split(" ");
        String modifiedauthor = "";

        for (int i = 0; i < words.length; i++) {
            words[i] = upperCaseFirst(words[i]);
            modifiedauthor += words[i] + " ";
        }
        return modifiedauthor;
    }

    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }


}

