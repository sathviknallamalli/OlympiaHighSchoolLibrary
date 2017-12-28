package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public WishlistBooksAdapter(Context context, int resource, ArrayList<WishlistBook> arraylistreservedbooks) {
        super(context, resource, arraylistreservedbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistwishlistbooks = arraylistreservedbooks;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        WishlistBook wishlistBook = arraylistwishlistbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.wishlistlayout, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.wishlisttitle);
        TextView authorinreserved = (TextView) convertView.findViewById(R.id.wishlistauthor);
        TextView categorywishlist = (TextView) convertView.findViewById(R.id.wishlistcategory);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.wishlistimage);
        TextView remove = (TextView) convertView.findViewById(R.id.removebutton);

        bookTitle.setText(wishlistBook.title);
        authorinreserved.setText(wishlistBook.author);
        categorywishlist.setText(wishlistBook.category);
        bookImage.setImageResource(wishlistBook.imageid);

        remove.setTag(position);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = (Integer) view.getTag();
                arraylistwishlistbooks.remove(index.intValue());
                notifyDataSetChanged();
                BookAdapter.wishlistbooks.remove(position);
                CatalogFragment.addedornot[position] = "Add to wishlist";
                CatalogFragment.addedornotorig[position] = "Add to wishlist";

                //BookAdapter.add.setText("Add to wishlist");
            }
        });

        return convertView;
    }

}
