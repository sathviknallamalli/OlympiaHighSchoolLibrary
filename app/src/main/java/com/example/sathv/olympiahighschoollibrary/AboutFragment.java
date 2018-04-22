package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sathv on 11/28/2017.
 */

public class AboutFragment extends Fragment {

    public AboutFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create a view of the appropriate xml file and display it
        View view = inflater.inflate(R.layout.about, container, false);
        //set the title of the screen
        getActivity().setTitle("Olympia High School Library");
        setHasOptionsMenu(false);



        return view;
    }
}
