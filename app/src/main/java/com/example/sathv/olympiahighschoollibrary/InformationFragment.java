package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sathv on 11/28/2017.
 */

public class InformationFragment extends Fragment {

    public InformationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        //set the appropriate layout to diplay when the fragment is clicked
        View view = inflater.inflate(R.layout.information, container, false);
        getActivity().setTitle("Information");

        Button b = (Button) view.findViewById(R.id.call);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone("3605967037");
            }
        });

        return view;
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

}
