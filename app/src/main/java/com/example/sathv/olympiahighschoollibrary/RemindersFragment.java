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

    NotificationCompat.Builder notification;
    private static final int uniqueid = 11125;

    ListView lvn;
    TextView message;
    ReminderBooksAdapter adapter;

    ArrayList<ReminderBook> reminderBooks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reminders, container, false);
        getActivity().setTitle("Reminders");

        lvn = (ListView) view.findViewById(R.id.reminderbooks);
        reminderBooks = new ArrayList<ReminderBook>();
        message = (TextView) view.findViewById(R.id.noremind);

        Button bcheck = (Button) view.findViewById(R.id.vcheck);
        Button breserve = (Button) view.findViewById(R.id.vreserve);

        bcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new CheckedFragment()).commit();
            }
        });

        breserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new ReservedFragment()).commit();
            }
        });

        //setHasOptionsMenu(true);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.setTime(currentDate);


        if (BookInformation.reminderdates.isEmpty()) {
            Log.d("BAD", "arraylists are empty in reminder fragment");

            message.setText("No reminders currently");

        } else {
            for (int i = 0; i < BookInformation.reminderdates.size(); i++) {
                //a reminder needs to be made for this book
                if (currentDate.equals(BookInformation.reminderdates.get(i))) {

                    reminderBooks.add(new ReminderBook(BookInformation.checkedoutbookstitles.get(i).toString(), (int) BookInformation.checkedoutbooksimages.get(i)
                            , "Book is DUE IN 2 DAYS"));

                    // Log.d("BAD", "arraylist are full");
                }
            }

        }
        // notification = new NotificationCompat.Builder(getActivity());
        // notification.setAutoCancel(true);

        return view;
    }

    public void notifyonclick(View view) {
        notification.setSmallIcon(R.drawable.bear);
        notification.setTicker("this is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("this is the title");
        notification.setContentText("body of the notifcation");

        /*FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, new AccountFragment()).commit();

        Intent intent = new Intent(this, Activities.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and issues it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());*/

    }
}
