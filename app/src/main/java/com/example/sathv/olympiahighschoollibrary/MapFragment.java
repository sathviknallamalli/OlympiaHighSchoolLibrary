package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sathv on 11/28/2017.
 */

public class MapFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.map, container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
