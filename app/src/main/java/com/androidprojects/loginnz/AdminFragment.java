package com.androidprojects.loginnz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminFragment extends Fragment {
    ArrayAdapter<CharSequence> arrayAdapter;
    EditText note;
    TextView mypage, emp_info, currentdate;
    Button start, end;
    Spinner spinner;
    String JWT, ID, DEPCODE = null;
    ImageView back_admin;
    List<String> dep_list = new ArrayList<>();
    List<String> depcode_list = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        makespiner();
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        note = rootView.findViewById(R.id.et_issue_admin);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (JWT == null && ID == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 JWT 받아온 값 넣기
                ID = getArguments().getString("ID"); // mainactivity에서 ID 받아온 값 넣기
            }
        }
        checktimesheet(); // 데이터베이스에 start, end 레코드 예외처리

        currentdate = rootView.findViewById(R.id.date);
        long current = System.currentTimeMillis(); // 1970년 1월 1일부터 몇 밀리세컨드가 지났는지를 반환함
        Date date = new Date(current);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String getTime = simpleDateFormat.format(date);
        currentdate.setText(getTime);

        mypage = rootView.findViewById(R.id.myPage_ad);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                activity.FragmentView(8);
            }
        });
        start = rootView.findViewById(R.id.Start);
        end = rootView.findViewById(R.id.End);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strChoiceItems = {"Yes", "No"};
                builder.setTitle("Do you want to press the 'START' button?");
                builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (position == 0) {
                            // 확인 눌렀을 때
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        //Toast.makeText(getContext(), jsonResponse.getString("start_time"), Toast.LENGTH_SHORT).show();
                                        spinner.setVisibility(View.INVISIBLE);
                                        note.setVisibility(View.VISIBLE);
                                        end.setVisibility(View.VISIBLE);
                                        start.setVisibility(View.INVISIBLE);
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            };

                            StartPost startpost = new StartPost(ID, JWT, DEPCODE, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(startpost);

                        } else if (position == 1) {
                            // 취소 눌렀을 때
                        }
                    }
                });
                builder.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] strChoiceItems = {"Yes", "No"};
                builder.setTitle("Do you want to press the 'END' button?");
                builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (position == 0) {
                            // 확인 눌렀을 때
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        //Toast.makeText(getContext(), jsonResponse.getString("end_time"), Toast.LENGTH_SHORT).show();
                                        spinner.setVisibility(View.VISIBLE);
                                        note.setVisibility(View.INVISIBLE);
                                        start.setVisibility(View.VISIBLE);
                                        end.setVisibility(View.INVISIBLE);
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            };

                            EndPost endpost = new EndPost(ID, JWT, note.getText().toString(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(endpost);

                        } else if (position == 1) {
                            // 취소 눌렀을 때
                        }
                    }
                });
                builder.show();
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

        back_admin = rootView.findViewById(R.id.back_admin);
        back_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Quits the application.")
                        .setMessage("Are you sure you want to quit the application?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "quits the application.", Toast.LENGTH_SHORT).show();
                                //getActivity().getSupportFragmentManager()
                                //        .beginTransaction().remove(AdminFragment.this).commit();
                                ActivityCompat.finishAffinity(getActivity());


                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        });


        spinner = rootView.findViewById(R.id.spinner_ad);
        return rootView;
    }

    private void makespiner() { // 부서 정보 파싱 후 spiner에 추가
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String dep = jsonObject.getString("name");
                        dep_list.add(dep); // 부서명 저장
                        depcode_list.add(jsonObject.getString("code")); // code 저장
                    }
                    arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, dep_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(arrayAdapter);
                    spinner.setPrompt("Department");
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            DEPCODE = depcode_list.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            DEPCODE = depcode_list.get(0);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        DepartmentGet DepartmentGet = new DepartmentGet(JWT, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(DepartmentGet);
    }

    private void checktimesheet() { // 데이터베이스에 start, end 레코드 예외처리
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String start_time = jsonObject.getString("start_time");
                    String end_time = jsonObject.getString("end_time");
                    if (start_time == null) {
                        spinner.setVisibility(View.VISIBLE);
                        note.setVisibility(View.INVISIBLE);
                        start.setVisibility(View.VISIBLE);
                        end.setVisibility(View.INVISIBLE);
                    }
                    if (start_time != null && end_time == "null") {
                        DEPCODE = jsonObject.getString("department_id");
                        spinner.setVisibility(View.INVISIBLE);
                        note.setVisibility(View.VISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        end.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();

                }
            }
        };
        TimesheetGet timesheetGet = new TimesheetGet(ID, JWT, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(timesheetGet);
    }
}