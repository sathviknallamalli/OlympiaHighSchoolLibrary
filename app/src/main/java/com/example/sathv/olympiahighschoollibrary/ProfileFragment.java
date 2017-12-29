package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sathv on 11/28/2017.
 */

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

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
        View view = inflater.inflate(R.layout.profile, container, false);
        getActivity().setTitle("Your profile");

        Login l2 = new Login();

        name = (TextView) view.findViewById(R.id.fullName);
        gr = (TextView) view.findViewById(R.id.grade);
        val = (TextView) view.findViewById(R.id.value);
        reserval = (TextView) view.findViewById(R.id.reservedval);
        b2 = (Button) view.findViewById(R.id.changepd);
        b = (Button) view.findViewById(R.id.changeaccount);

        //assign each field the proper information by retrieving variables; username, name, checkedoutcount, and reservedcount
        name.setText(l2.getFullName());
        gr.setText("Grade: " + l2.getGrade());
        val.setText(BookInformation.checkedoutcount + "");
        reserval.setText(BookInformation.reservedcount + "");

        //changeaccount onclick
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the fragment of manage acount
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new AccountFragment()).commit();
            }
        });

        //change pd onclick
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the activity to change password
                Intent activities = new Intent(getActivity(), ChangePassword.class);
                startActivity(activities);
            }
        });
        return view;
    }
}
