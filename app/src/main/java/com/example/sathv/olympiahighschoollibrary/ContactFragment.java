package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sathv on 11/28/2017.
 */

public class ContactFragment extends Fragment {

    //This class is like a forum and allows the user to leave suggestions or any complains. Any new bugs discrovered
    //can be received from this and fixed
    public ContactFragment() {

    }

    ProgressDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.contact, container, false);
        getActivity().setTitle("Contact");
        setHasOptionsMenu(false);

        final EditText messageinput = (EditText) view.findViewById(R.id.messageinput);
        final EditText phoneinput = (EditText) view.findViewById(R.id.phoneinput);
        final EditText emailinput = (EditText) view.findViewById(R.id.emailinput);
        final EditText nameinput = (EditText) view.findViewById(R.id.nameinput);
        Button submit = (Button) view.findViewById(R.id.submit);
        mDialog = new ProgressDialog(view.getContext());

        messageinput.setVerticalScrollBarEnabled(true);
        emailinput.setHorizontalScrollBarEnabled(true);
        nameinput.setHorizontalScrollBarEnabled(true);
        phoneinput.setHorizontalScrollBarEnabled(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactname = nameinput.getText().toString();
                String contactphone = phoneinput.getText().toString();
                String contactemail = emailinput.getText().toString();
                String contactmessage = messageinput.getText().toString();

                mDialog.setMessage("Sending message");
                mDialog.show();

                mDialog.dismiss();

                //set subject and message for the email beign sent
                String subject = "Message from Olympia High School Library App";

                contactmessage = contactmessage + "\nSender name= " + contactname + "\nSender email for reference= " + contactemail +
                        "\nSender phone for reference= " + contactphone;

                SendMailShare sm = new SendMailShare(view.getContext(), "sathviknallamalli@gmail.com", subject, contactmessage, "Your message has been sent");
                sm.execute();

                messageinput.setText("");
                nameinput.setText("");
                phoneinput.setText("");
                emailinput.setText("");
            }
        });


        return view;
    }
}
