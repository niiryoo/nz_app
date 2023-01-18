package com.androidprojects.loginnz;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminFragment extends Fragment {
    TextView hoursworked, mypage, emp_info;
    Button start, end, rest, restart;
    String JWT, ID = null;
    String url = "http://20.211.44.13:5000/department/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        if(JWT == null && ID == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 JWT 받아온 값 넣기
                ID = getArguments().getString("ID"); // mainactivity에서 ID 받아온 값 넣기
            }
        }

        hoursworked = rootView.findViewById(R.id.hourswork);

         hoursworked.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Dialog dialog = new Dialog(getContext());
                 dialog.setContentView(R.layout.timesheet);

                 // 총 일한시간 데이터 파싱해서 연결

                 Button btn_ok = dialog.findViewById(R.id.btn_ok_timeSheet);
                 btn_ok.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();  // 팝업창 끄기
                     }
                 });
                 dialog.show();
             }
         });

         mypage = rootView.findViewById(R.id.myPage);
         mypage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getContext(), "details 버튼 클릭되었음.", Toast.LENGTH_SHORT).show();

                 Dialog dialog = new Dialog(getContext());
                 dialog.setContentView(R.layout.details);

                 // details 데이터 파싱해서 연결

                 Button btn_ok = dialog.findViewById(R.id.btn_ok_details);
                 btn_ok.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();  // 팝업창 끄기
                     }
                 });
                 dialog.show();
             }
         });
        start = rootView.findViewById(R.id.Start);
        end = rootView.findViewById(R.id.End);
        rest = rootView.findViewById(R.id.rest);
        restart = rootView.findViewById(R.id.Restart);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.VISIBLE);
                end.setVisibility(View.INVISIBLE);
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart.setVisibility(View.VISIBLE);
                rest.setVisibility(View.INVISIBLE);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rest.setVisibility(View.VISIBLE);
                restart.setVisibility(View.INVISIBLE);
            }
        });
         emp_info = rootView.findViewById(R.id.empworkinfo);

         emp_info.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                 activity.FragmentView(6);
             }
         });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}