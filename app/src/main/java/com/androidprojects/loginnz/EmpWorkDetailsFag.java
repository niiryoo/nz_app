package com.androidprojects.loginnz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.Calendar;

public class EmpWorkDetailsFag extends Fragment {

    String urlStr = "http://20.211.44.13:5000/profile/";
    RequestQueue requestQueue;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Button button_ok;
    MainActivity mainActivity;
    String JWT, ID = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timesheet_details, container, false);




        return rootView;
    }

}
