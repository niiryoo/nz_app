package com.androidprojects.loginnz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpFragment extends Fragment {
    ArrayAdapter<CharSequence> arrayAdapter;
    Spinner spinner;
    EditText note;
    TextView mypage;
    Button start, end;
    String JWT, ID, DEPCODE = null;
    List<String> dep_list = new ArrayList<String>();
    List<String> depcode_list = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        makespiner();
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_emp, container, false);
        note = rootView.findViewById(R.id.tv_issue);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (JWT == null && ID == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 JWT 받아온 값 넣기
                ID = getArguments().getString("ID"); // mainactivity에서 ID 받아온 값 넣기
            }
        }

        mypage = rootView.findViewById(R.id.myPage_amp);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "details 버튼 클릭되었음.", Toast.LENGTH_SHORT).show();

                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                activity.FragmentView(7);
            }
        });

        start = rootView.findViewById(R.id.Start);
        end = rootView.findViewById(R.id.End);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strChoiceItems = {"OK", "cancel"};
                builder.setTitle("출근??");
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
                                        Toast.makeText(getContext(), jsonResponse.getString("start_time"), Toast.LENGTH_SHORT).show();
                                        Log.d("start_time",jsonResponse.getString("start_time"));
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


                String[] strChoiceItems = {"OK", "cancel"};
                builder.setTitle("퇴근??");
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
                                        Toast.makeText(getContext(), jsonResponse.getString("end_time"), Toast.LENGTH_SHORT).show();
                                        Log.d("end_time",jsonResponse.getString("end_time"));
                                        note.setVisibility(View.INVISIBLE);
                                        start.setVisibility(View.VISIBLE);
                                        end.setVisibility(View.INVISIBLE);
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();

                                    }
                                }
                            };

                            EndPost endpost = new EndPost(ID, JWT, note.toString(), responseListener);
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
        spinner = rootView.findViewById(R.id.spinner);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}