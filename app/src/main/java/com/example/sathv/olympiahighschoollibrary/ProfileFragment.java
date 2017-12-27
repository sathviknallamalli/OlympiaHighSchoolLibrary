package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sathv on 11/28/2017.
 */

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    EditText input;

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

        Button b = (Button) view.findViewById(R.id.changeaccount);
        Button b2 = (Button) view.findViewById(R.id.changepd);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new AccountFragment()).commit();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activities = new Intent(getActivity(), ChangePassword.class);
                startActivity(activities);

            }
        });


        return view;
    }

    public void changepd(View view) {

    }


}
