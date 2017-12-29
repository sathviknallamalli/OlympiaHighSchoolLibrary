package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    Context context;
    int resource;
    ArrayList<Book> books = null;

    static ArrayList<WishlistBook> wishlistbooks = new ArrayList<>();
    Book book;
    Button add;

    //book adapter contructor
    public BookAdapter(Context context, int resource, ArrayList<Book> books) {
        super(context, resource, books);
        this.context = context;
        this.resource = resource;
        this.books = books;
    }

    //set the layouts and fill the layout for each book in getView method
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        book = books.get(position);

        Log.d("ABC", "pos " + position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customlayout, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookCount = (TextView) convertView.findViewById(R.id.bookpg);
        TextView bookCategory = (TextView) convertView.findViewById(R.id.bookCategory);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookimage);

        //setting each field
        add = (Button) convertView.findViewById(R.id.button2);
        bookTitle.setText(book.title);
        bookAuthor.setText(book.author);
        bookCount.setText("Pagecount: " + book.pageCount);
        bookCategory.setText(book.category);
        bookImage.setImageResource(book.imageid);

        add.setText(book.added);

        //button onClick for add to wishlist button in each row based on tags
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if button sauys "Added" dont let adding
                if (add.getText().toString().equals("Added")) {
                    Toast.makeText(getContext(), "Already added to your wishlist", Toast.LENGTH_SHORT).show();
                } else {
                    //update the variable for the Book
                    Integer index = (Integer) view.getTag();

                    CatalogFragment.addedornot[position] = "Added";
                    CatalogFragment.addedornotorig[position] = "Added";
                    book.added = "Added";

                    add.setText("Added");

                    //and add to the wishlist arraylist
                    wishlistbooks.add(new WishlistBook(book.title, book.author, book.category, book.imageid));
                }

            }
        });
        return convertView;
    }
}
