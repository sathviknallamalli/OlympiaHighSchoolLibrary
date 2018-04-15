package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sathv on 11/28/2017.
 */

public class LogoutFragment extends Fragment {

    public LogoutFragment() {

    }

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create a view of the appropriate xml file and display it
        View view = inflater.inflate(R.layout.logout, container, false);
        //set the title of the screen
        getActivity().setTitle("Logout");

        mAuth = FirebaseAuth.getInstance();


        Button logout = view.findViewById(R.id.lg);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getView().getContext(), Login.class));
            }
        });

        return view;
    }


}


