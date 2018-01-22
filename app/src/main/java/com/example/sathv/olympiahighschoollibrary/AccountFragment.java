package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathv on 11/28/2017.
 */

public class AccountFragment extends Fragment {

    public AccountFragment() {

    }

    Login l = new Login();
    EditText fnametemp;
    EditText lnametemp;
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

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.account, container, false);
        getActivity().setTitle("Manage account");
        setHasOptionsMenu(false);

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
        lnametitle =  (TextView) view.findViewById(R.id.lastnametitle);
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

                String[] names = l.getFullName().split(" ");
                fnametemp.setText(names[0]);
                lnametemp.setText(names[1]);
                emailtemp.setText(l.getEmail());
                usernametemp.setText(l.getUsername());
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

    public void updateinfo() {
        String url = "https://sathviknallamalli.000webhostapp.com/updateaccountfragment.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getView().getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //start and initialize the dialog with message
                mDialog.setMessage("Making changes...");
                mDialog.show();
                if (response.trim().equals("success")) {
                    //if the php script returns "success" token then let user know and send email
                    Login l = new Login();

                    String fullnamenew = fnametemp.getText().toString() + " " + lnametemp.getText().toString();
                    l.setFullName(fullnamenew);
                    l.setEmail(emailtemp.getText().toString());
                    Login.setUsername(usernametemp.getText().toString());

                    mDialog.dismiss();

                    String emailRaw = l.getEmail();

                    //set subject and message for the email beign sent
                    String subject = "Account changes";
                    String message = "You have changed your account settings using the Olympia High School Library app and here is the latest information " + "\nUsername " + usernametemp.getText() + "\nFull name " + fnametemp.getText() + " " + lnametemp.getText() + "\nEmail " + emailtemp.getText();

                    SendMailShare sm = new SendMailShare(getContext(), emailRaw, subject, message, "Saved. Restart the app to see the changes");

                    sm.execute();

                } else {
                    Toast.makeText(getContext(), "Oops something went wrong" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //send the appropriate hashmap vairables as parameters into the php script
                params.put("username", usernametemp.getText().toString().trim().replace(" ", ""));
                params.put("firstname", fnametemp.getText().toString().trim());
                params.put("lastname", lnametemp.getText().toString().trim());
                params.put("email", emailtemp.getText().toString().trim().replace(" ", ""));

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


}
