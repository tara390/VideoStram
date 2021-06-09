package com.manddprojectconsulant.videostram.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.common.api.GoogleApiClient;
import com.manddprojectconsulant.videostram.R;


public class UploadFragment extends Fragment {

    private static Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private GridView mGridView;
    private ImageLoader mImageLoader;


    public UploadFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_upload, container, false);


        return view;
    }
}