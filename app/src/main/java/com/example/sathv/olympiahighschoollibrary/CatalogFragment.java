package com.example.sathv.olympiahighschoollibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sathv on 11/28/2017.
 */

public class CatalogFragment extends Fragment {

    public CatalogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Catalog");


        return inflater.inflate(R.layout.catalog,container,false);
    }


}
