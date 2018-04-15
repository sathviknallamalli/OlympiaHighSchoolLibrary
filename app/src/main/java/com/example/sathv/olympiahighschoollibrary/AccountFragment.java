package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by sathv on 11/28/2017.
 */

public class AccountFragment extends Fragment {

    public AccountFragment() {

    }

    Login l = new Login();
    //UI References
    EditText fnametemp;
    EditText lnametemp;
    FirebaseAuth mAuth;
    EditText emailtemp;
    EditText usernametemp;
    TextView fnametitle;
    TextView lnametitle;
    TextView emailtitle;
    TextView usernametitle;
    Button change;
    Button save;
    Button edit;
    ProgressDialog mDialog;

    SharedPreferences sharedPref;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create view and set title of activity
        View view = inflater.inflate(R.layout.account, container, false);
        getActivity().setTitle("Manage account");
        setHasOptionsMenu(false);

        sharedPref = view.getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        //retrieve views
        fnametemp = (EditText) view.findViewById(R.id.fnametemp);
        emailtemp = (EditText) view.findViewById(R.id.emailtemp);
        usernametemp = (EditText) view.findViewById(R.id.usernametemp);
        lnametemp = (EditText) view.findViewById(R.id.lnametemp);
        save = (Button) view.findViewById(R.id.save);
        change = (Button) view.findViewById(R.id.change);
        fnametitle = (TextView) view.findViewById(R.id.firstnametitle);
        emailtitle = (TextView) view.findViewById(R.id.emailtitle);
        usernametitle = (TextView) view.findViewById(R.id.usernametitle);
        lnametitle = (TextView) view.findViewById(R.id.lastnametitle);
        edit = (Button) view.findViewById(R.id.edit);

        //set visibility
        fnametemp.setVisibility(View.GONE);
        usernametemp.setVisibility(View.GONE);
        emailtemp.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        usernametitle.setVisibility(View.GONE);
        fnametitle.setVisibility(View.GONE);
        emailtitle.setVisibility(View.GONE);
        lnametitle.setVisibility(View.GONE);
        lnametemp.setVisibility(View.GONE);

        mDialog = new ProgressDialog(view.getContext());

        //edit button onclick
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnametemp.setVisibility(View.VISIBLE);
                usernametemp.setVisibility(View.VISIBLE);
                emailtemp.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);
                usernametitle.setVisibility(View.VISIBLE);
                fnametitle.setVisibility(View.VISIBLE);
                emailtitle.setVisibility(View.VISIBLE);
                lnametitle.setVisibility(View.VISIBLE);
                lnametemp.setVisibility(View.VISIBLE);

                //set textfields with current info


                String[] names = sharedPref.getString(getString(R.string.fullname), "full name").split(" ");

                if (names.length == 1) {
                    lnametemp.setText("no last name");
                } else {
                    lnametemp.setText(names[1]);
                }

                fnametemp.setText(names[0]);
                emailtemp.setText(sharedPref.getString(getString(R.string.email), "email"));
                usernametemp.setText(sharedPref.getString(getString(R.string.username), "username"));
            }
        });

        //save button onclick
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the variables with updated fields

                updateinfo();

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


    private void updateinfo() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();

        //save and update all the changes to Firebase
        Firebase ref = new Firebase("https://libeary-8d044.firebaseio.com/Users/" + userid);
        final UserInformation bookdets = new UserInformation(fnametemp.getText().toString().trim(), lnametemp.getText().toString().trim(),
                "Email/Password",usernametemp.getText().toString().trim().replace(" ", ""), Login.getPassword(),
                emailtemp.getText().toString().trim().replace(" ", ""), Login.getGrade());
        ref.setValue(bookdets);

        //set methods
        Login.setEmail(emailtemp.getText().toString().trim().replace(" ", ""));
        Login.setUsername(usernametemp.getText().toString().trim().replace(" ", ""));
        Login.setFullName(fnametemp.getText().toString().trim() + " " + lnametemp.getText().toString().trim());

        //send an email regarding the changes to the account
        mDialog.dismiss();

        String emailRaw = l.getEmail();

        //set subject and message for the email beign sent
        String subject = "Account changes";
        String message = "You have changed your account settings using the Olympia High School Library app and here is the latest information " + "\nUsername " + usernametemp.getText() + "\nFull name " + fnametemp.getText() + " " + lnametemp.getText() + "\nEmail " + emailtemp.getText();

        SendMailShare sm = new SendMailShare(getView().getContext(), emailRaw, subject, message, "Saved. Restart the app to see the changes");

        sm.execute();
    }


}
