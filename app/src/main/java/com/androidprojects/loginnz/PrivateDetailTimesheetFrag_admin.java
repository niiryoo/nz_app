package com.androidprojects.loginnz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


// 출근화면에서 바로 보일수 있는...
public class PrivateDetailTimesheetFrag_admin extends Fragment {

    private Button btn_ok;
    TextView id, name, email, phone;
    TextView mon_start, mon_end, mon_dep, mon_note;
    String JWT, ID;
    RecyclerView recyc_private;
    List<String> startTime_list = new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.timesheet_privatedetails, container, false);

        id = rootView.findViewById(R.id.tv_pd_id);
        name = rootView.findViewById(R.id.tv_pd_name);
        email = rootView.findViewById(R.id.tv_pd_email);
        phone = rootView.findViewById(R.id.tv_pd_phone);
        btn_ok = rootView.findViewById(R.id.btn_pt_ok);
        recyc_private = rootView.findViewById(R.id.recyc_private);



        if(JWT == null && ID == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 JWT 받아온 값 넣기
                ID = getArguments().getString("ID"); // mainactivity에서 ID 받아온 값 넣기
            }
        }
        Response.Listener<String> profileListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    id.setText(jsonResponse.getString("code"));
                    name.setText(jsonResponse.getString("first_name") + " " + jsonResponse.getString("last_name"));
                    email.setText(jsonResponse.getString("email"));
                    phone.setText(jsonResponse.getString("phone"));
                } catch (JSONException ex) {
                    ex.printStackTrace();

                }

            }
        };

        ProfileGet profileGet = new ProfileGet(JWT, ID, profileListener);
        RequestQueue profilequeue = Volley.newRequestQueue(getContext());
        profilequeue.add(profileGet);

       /** Response.Listener<String> timesheetListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String start = jsonObject.getString("start_time");
                        startTime_list.add(start);
                    }
                    mon_start.setText(startTime_list.get(0));
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        };
        TimesheetGet timesheetGet = new TimesheetGet(ID, JWT, timesheetListener);
        RequestQueue timesheetqueue = Volley.newRequestQueue(getContext());
        timesheetqueue.add(timesheetGet);**/





        btn_ok = rootView.findViewById(R.id.btn_pt_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                activity.FragmentView(1);
            }
        });



        return rootView;
    }
}
