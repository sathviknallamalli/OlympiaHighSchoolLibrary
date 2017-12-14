package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        View view = inflater.inflate(R.layout.map, container, false);

        PanoramaImageView panoramaImageView = (PanoramaImageView) view.findViewById(R.id.panorama_image_view);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        getActivity().setTitle("Map of the OHS library");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        gyroscopeObserver.register(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
