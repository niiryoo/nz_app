package com.androidprojects.loginnz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


// 출근화면에서 바로 보일수 있는...
public class PrivateDetailTimesheetFrag_emp extends Fragment {

    private Button btn_ok;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.timesheet_privatedetails, container, false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                activity.FragmentView(2);
            }
        });




        return rootView;
    }
}
