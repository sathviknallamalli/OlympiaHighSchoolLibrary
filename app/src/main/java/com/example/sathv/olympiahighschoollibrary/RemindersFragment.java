package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sathv on 11/28/2017.
 */

public class RemindersFragment extends Fragment {

    public RemindersFragment() {

    }

    //notification variables
    NotificationCompat.Builder notification;
    private static final int uniqueid = 11125;

    ListView lvn;
    TextView message;
    ReminderBooksAdapter adapter;

    ArrayList<ReminderBook> reminderBooks;

    Button bcheck;
    Button breserve;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminders, container, false);
        getActivity().setTitle("Reminders");

        //assign variables
        lvn = (ListView) view.findViewById(R.id.reminderbooks);
        reminderBooks = new ArrayList<ReminderBook>();
        message = (TextView) view.findViewById(R.id.noremind);

        bcheck = (Button) view.findViewById(R.id.vcheck);
        breserve = (Button) view.findViewById(R.id.vreserve);

        //view checked out books button
        bcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open checked fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new CheckedFragment()).commit();
            }
        });

        //view reserved books button
        breserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open reserved fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new ReservedFragment()).commit();
            }
        });

        //get current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.setTime(currentDate);

        boolean isreminder = false;

        //check if any books were checked out that a reminder was set
        if (BookInformation.reminderdates.isEmpty()) {
            Log.d("BAD", "arraylists are empty in reminder fragment");

            message.setText("No reminders currently");

        } else {
            for (int i = 0; i < BookInformation.reminderdates.size(); i++) {
                //check if the current date matches the date for a reminder
                if (currentDate.equals(BookInformation.reminderdates.get(i))) {

                    //add book to the reminder books arraylist for listview
                    reminderBooks.add(new ReminderBook(BookInformation.checkedoutbookstitles.get(i).toString(), (int) BookInformation.checkedoutbooksimages.get(i)
                            , "Book is DUE IN 2 DAYS"));

                    isreminder = true;
                }
                //set adapter to listview
                adapter = new ReminderBooksAdapter(getActivity().getApplicationContext(), R.layout.reminderlayout, reminderBooks);
                lvn.setAdapter(adapter);
            }

        }
        //if books were checked out but current date is not same as reminder sate
        if (isreminder == false) {
            message.setText("No reminders currently");
        }

        return view;
    }

    //notifcationer button onclick
    public void notifyonclick(View view) {
        notification.setSmallIcon(R.drawable.bear);
        notification.setTicker("this is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("this is the title");
        notification.setContentText("body of the notifcation");
    }
}
