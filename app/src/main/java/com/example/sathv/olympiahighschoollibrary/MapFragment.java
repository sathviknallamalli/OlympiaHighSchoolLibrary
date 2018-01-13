package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

/**
 * Created by sathv on 11/28/2017.
 */

public class MapFragment extends Fragment {

    public MapFragment() {

    }

    GyroscopeObserver gyroscopeObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.map, container, false);
        getActivity().setTitle("Map of the OHS library");
        setHasOptionsMenu(false);

        //create gyroscope observer object
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 5);

        //set the panorama image for gyro observer
        final PanoramaImageView panoramaImageView = (PanoramaImageView) view.findViewById(R.id.panorama_image_view);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        Button libbutton = (Button) view.findViewById(R.id.libbutton);
        Button hbutton = (Button) view.findViewById(R.id.helpbutton);
        Button fiction = (Button) view.findViewById(R.id.fiction);
        Button work = (Button) view.findViewById(R.id.work);

        libbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ohslibrary360));
                panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
            }
        });

        hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.drawable.helpdesk360));
                panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
            }
        });

        fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.mipmap.fictionsection));
                panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
            }
        });

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.mipmap.workarea));
                panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
            }
        });
        return view;
    }

    //move along the panorama when resumed
    @Override
    public void onResume() {
        super.onResume();
        gyroscopeObserver.register(getActivity());
    }

    //stop the gyroscope movement if paused
    @Override
    public void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
