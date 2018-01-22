package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    TextView name;
    TextView gr;
    TextView val;
    TextView reserval;
    Button b2;
    Button b;
    Button changegrade;
    ProgressDialog mDialog;
    String newGrade;
    Login l = new Login();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile, container, false);
        getActivity().setTitle("Your profile");
        setHasOptionsMenu(false);

        Login l2 = new Login();

        name = (TextView) view.findViewById(R.id.fullName);
        gr = (TextView) view.findViewById(R.id.grade);
        val = (TextView) view.findViewById(R.id.value);
        reserval = (TextView) view.findViewById(R.id.reservedval);
        b2 = (Button) view.findViewById(R.id.changepd);
        b = (Button) view.findViewById(R.id.changeaccount);
        changegrade = (Button) view.findViewById(R.id.changegrade);
        mDialog = new ProgressDialog(view.getContext());

        //assign each field the proper information by retrieving variables; username, name, checkedoutcount, and reservedcount
        name.setText(l2.getFullName());

        if (l2.getGrade() == null) {
            gr.setText("Grade: " + "Unknown");
        } else {
            gr.setText("Grade: " + l2.getGrade());

        }
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

        changegrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence grades[] = new CharSequence[]{"9", "10", "11", "12"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Change grade");
                builder.setItems(grades, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        updategrade();

                        gr.setText("Grade: " + grades[which]);
                        newGrade = grades[which].toString();




                    }
                });
                builder.show();
            }
        });
        return view;
    }

    private void updategrade() {
        String url = "https://sathviknallamalli.000webhostapp.com/updategrade.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getView().getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //start and initialize the dialog with message
                mDialog.setMessage("Changing grade...");
                mDialog.show();
                if (response.trim().equals("success")) {
                    //if the php script returns "success" token then let user know and send email
                    l.setGrade(newGrade);

                    mDialog.dismiss();
                    Toast.makeText(getContext(), "Grade has been changed", Toast.LENGTH_SHORT).show();

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
                params.put("username", l.getUsername());
                params.put("grade", newGrade);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
