package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sathv on 11/28/2017.
 */

public class InformationFragment extends Fragment {

    public InformationFragment() {

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.contact, container, false);


        getActivity().setTitle("Information");


        return view;
    }


}
