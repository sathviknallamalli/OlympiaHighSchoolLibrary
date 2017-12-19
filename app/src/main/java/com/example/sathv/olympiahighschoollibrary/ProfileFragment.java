package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sathv on 11/28/2017.
 */

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.profile, container, false);

        Login l2 = new Login();

        TextView name = (TextView) view.findViewById(R.id.fullName);
        name.setText(l2.getFullName());

        TextView gr = (TextView) view.findViewById(R.id.grade);
        gr.setText("Grade: " + l2.getGrade());

        TextView val = (TextView) view.findViewById(R.id.value);
        val.setText(BookInformation.checkedoutcount + "");

        TextView reserval = (TextView) view.findViewById(R.id.reservedval);
        reserval.setText(BookInformation.reservedcount + "");
        getActivity().setTitle("Your profile");


        return view;
    }


}
