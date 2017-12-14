package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by sathv on 11/28/2017.
 */

public class CatalogFragment extends Fragment {

    public CatalogFragment() {

    }

    String[] title = {"1984", "candymakers"};
    String[] author = {"George Owell", "Wendy Mass"};
    String[] pageCount = {"268","464"};
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.catalog, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listofbooks);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        getActivity().setTitle("Catalog");


        return view;
    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);

            TextView bookTitle = (TextView) view.findViewById(R.id.bookTitle);
            TextView authorTitle = (TextView) view.findViewById(R.id.bookAuthor);
            TextView pagecount = (TextView) view.findViewById(R.id.pageCount);

            bookTitle.setText(title[i]);
            authorTitle.setText(author[i]);
            pagecount.setText(pageCount[i]);

            return null;
        }
    }
}

