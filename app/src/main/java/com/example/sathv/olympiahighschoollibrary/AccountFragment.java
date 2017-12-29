package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sathv on 11/28/2017.
 */

public class AccountFragment extends Fragment {

    public AccountFragment() {

    }

    Login l = new Login();
    EditText nametemp;
    EditText emailtemp;
    EditText usernametemp;
    TextView nametitle;
    TextView emailtitle;
    TextView usernametitle;
    Button change;
    Button save;
    Button edit;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.account, container, false);
        getActivity().setTitle("Manage account");

        //retrieve views
        nametemp = (EditText) view.findViewById(R.id.nametemp);
        emailtemp = (EditText) view.findViewById(R.id.emailtemp);
        usernametemp = (EditText) view.findViewById(R.id.usernametemp);
        save = (Button) view.findViewById(R.id.save);
        change = (Button) view.findViewById(R.id.change);
        nametitle = (TextView) view.findViewById(R.id.nametitle);
        emailtitle = (TextView) view.findViewById(R.id.emailtitle);
        usernametitle = (TextView) view.findViewById(R.id.usernametitle);
        edit = (Button) view.findViewById(R.id.edit);

        //set visibility
        nametemp.setVisibility(View.GONE);
        usernametemp.setVisibility(View.GONE);
        emailtemp.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        usernametitle.setVisibility(View.GONE);
        nametitle.setVisibility(View.GONE);
        emailtitle.setVisibility(View.GONE);

        //edit button onclick
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nametemp.setVisibility(View.VISIBLE);
                usernametemp.setVisibility(View.VISIBLE);
                emailtemp.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);
                usernametitle.setVisibility(View.VISIBLE);
                nametitle.setVisibility(View.VISIBLE);
                emailtitle.setVisibility(View.VISIBLE);

                //set textfields with current info
                nametemp.setText(l.getFullName());
                emailtemp.setText(l.getEmail());
                usernametemp.setText(l.getUsername());
            }
        });

        //save button onclick
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the variables with updated fields
                l.setFullName(nametemp.getText().toString());
                l.setEmail(emailtemp.getText().toString());
                Login.setUsername(usernametemp.getText().toString());

                // Activities a = new Activities();

                //  a.studentName.setText(l.getFullName());
                //  a.email.setText(l.getEmail());

                //UPDATE TO DATABASE



                Toast.makeText(getActivity().getApplicationContext(), "Saved. Restart the app to see the changes", Toast.LENGTH_SHORT).show();
            }
        });

        //change button onclick
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open activity to change password
                Intent activities = new Intent(getActivity(), ChangePassword.class);
                startActivity(activities);
            }
        });
        return view;
    }


}
