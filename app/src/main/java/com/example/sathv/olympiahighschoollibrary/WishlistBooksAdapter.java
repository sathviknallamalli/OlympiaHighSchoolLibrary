package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class WishlistBooksAdapter extends ArrayAdapter<WishlistBook> {

    Context context;
    int resource;
    ArrayList<WishlistBook> arraylistwishlistbooks = null;

    //wishlistbook adapter constructor
    public WishlistBooksAdapter(Context context, int resource, ArrayList<WishlistBook> arraylistwishlistbooks) {
        super(context, resource, arraylistwishlistbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistwishlistbooks = arraylistwishlistbooks;
    }

    //laoad each row into the list view and setting fields
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        WishlistBook wishlistBook = arraylistwishlistbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.wishlistlayout, parent, false);
        }

        //retrieve each field
        TextView bookTitle = (TextView) convertView.findViewById(R.id.wishlisttitle);
        TextView authorinreserved = (TextView) convertView.findViewById(R.id.wishlistauthor);
        TextView categorywishlist = (TextView) convertView.findViewById(R.id.wishlistcategory);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.wishlistimage);
        Button remove = (Button) convertView.findViewById(R.id.removebutton);

        //set each field with appropriate string through the wishlistBook currently being loaded and get the globalvarables
        bookTitle.setText(wishlistBook.title);
        authorinreserved.setText(wishlistBook.author);
        categorywishlist.setText(wishlistBook.category);
        bookImage.setImageResource(wishlistBook.imageid);

        //set the button in the row the appropriate tag
        remove.setTag(position);

        //remove button onclick action
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = (Integer) view.getTag();
                //remove the appropriate index object from the arraylist being used
                arraylistwishlistbooks.remove(index.intValue());

                //remove from the array as well
                BookAdapter.wishlistbooks.remove(index.intValue());
                //update the addedornot and change to the option of being able to add to wishlist

                CatalogFragment cf = new CatalogFragment();
                cf.addedornot[position] = "Add to wishlist";
               // cf.addedornotorig[position] = "Add to wishlist";
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
