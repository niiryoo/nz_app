package com.androidprojects.loginnz;

import static java.util.Locale.*;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class EmpWorkInfoFragment extends Fragment {
    String urlStr = "http://20.211.44.13:5000/profile/";
    RequestQueue requestQueue;
    adminESTAdapter adapter;
    RecyclerView recyclerView;
    Calendar myCalendar;
    ImageView start_dateimg;
    ImageView end_dateimg;
    TextView start_et_date;
    TextView end_et_date, backtext;
    Button searchbutton, addpersonbutton;
    MainActivity mainActivity;
    String JWT, ID = null;
    public EmpWorkInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(JWT == null && ID == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 JWT 받아온 값 넣기
                ID = getArguments().getString("ID"); // mainactivity에서 ID 받아온 값 넣기
            }
        }
        mainActivity = (MainActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_emp_work_info, container, false);
        start_et_date = rootView.findViewById(R.id.start_datetext);
        end_et_date = rootView.findViewById(R.id.end_datetext);
        myCalendar = Calendar.getInstance();
        recyclerView = rootView.findViewById(R.id.recyclerview);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        updateLabel(3);
        makeRequest();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new adminESTAdapter();
        recyclerView.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener start_myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(0);
            }
        };

        DatePickerDialog.OnDateSetListener end_myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(1);
            }
        };
        start_dateimg = rootView.findViewById(R.id.start_dateimg);
        start_dateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), start_myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end_dateimg = rootView.findViewById(R.id.end_dateimg);
        end_dateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), end_myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        addpersonbutton = rootView.findViewById(R.id.addperson);
        addpersonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.FragmentView(3);
            }
        });
        return rootView;
    }

    private void updateLabel(int i) {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        switch (i){
            case 0:
                start_et_date.setText(sdf.format(myCalendar.getTime()));
                break;
            case 1:
                end_et_date.setText(sdf.format(myCalendar.getTime()));
                break;
            default:
                start_et_date.setText(sdf.format(myCalendar.getTime()));
                end_et_date.setText(sdf.format(myCalendar.getTime()));
                break;
        }

    }
    private void makeRequest() { // 미사용
        StringRequest request = new StringRequest(Request.Method.GET, urlStr, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map  params = new HashMap();
                params.put("authorization",JWT);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
    private void processResponse(String response) { //Json 데이터 파싱 // 미사용
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray == null) {
                Toast.makeText(getActivity().getApplicationContext(), "null", Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    adapter.addItem(jsonObject);
                }
                adapter.notifyDataSetChanged();
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}