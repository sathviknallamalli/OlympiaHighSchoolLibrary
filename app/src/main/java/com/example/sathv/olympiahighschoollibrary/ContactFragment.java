package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sathv on 11/28/2017.
 */

public class ContactFragment extends Fragment {

    public ContactFragment() {

    }

    TextView name;
    TextView gr;
    TextView val;
    TextView reserval;
    Button b2;
    Button b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.contact, container, false);
        getActivity().setTitle("Contact");
        setHasOptionsMenu(false);


        return view;
    }
}
