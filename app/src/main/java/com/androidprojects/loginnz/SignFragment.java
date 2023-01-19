package com.androidprojects.loginnz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignFragment extends Fragment {
    EditText emp_name, emp_id, emp_pw, emp_pwconfirm, emp_email, emp_tel;
    Button confirm_button, create_button;
    RadioButton admin_radio, emp_radio;
    TextView back, confirm_text;
    AlertDialog dialog;
    ImageView back_sign;

    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign, container, false);
        emp_name = rootView.findViewById(R.id.Name);
        emp_id = rootView.findViewById(R.id.ID);
        emp_pw = rootView.findViewById(R.id.pw);
        emp_pwconfirm = rootView.findViewById(R.id.pw_confirm);
        emp_email = rootView.findViewById(R.id.email);
        emp_tel = rootView.findViewById(R.id.tel);

        confirm_button = rootView.findViewById(R.id.confirm_button);
        create_button = rootView.findViewById(R.id.create_button);

        admin_radio = rootView.findViewById(R.id.admin_radio);
        emp_radio = rootView.findViewById(R.id.emp_radio);

        confirm_text = rootView.findViewById(R.id.confirm_text);



        confirm_button.setOnClickListener(new View.OnClickListener() { // 비밀번호 확인 버튼 기능 구현
            @Override
            public void onClick(View view) {
                String pw = emp_pw.getText().toString();
                String pw2 = emp_pwconfirm.getText().toString();

                if (pw.equals(pw2)) {
                    confirm_text.setText("Password match");
                    confirm_text.setTextColor(Color.parseColor("#000000"));
                    create_button.setEnabled(true);
                } else {
                    confirm_text.setText("Password do not match");
                    confirm_text.setTextColor(Color.parseColor("#FF0000"));
                    create_button.setEnabled(false);
                }
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = emp_name.getText().toString();
                final String id = emp_id.getText().toString();
                final String pw = emp_pw.getText().toString();
                final String email = emp_email.getText().toString();
                final String tel = emp_tel.getText().toString();

                if (name.equals("") || id.equals("") || pw.equals("") || email.equals("") || tel.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    dialog = builder.setMessage("Please fill out all items").setNegativeButton("check", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getContext(),"Registration Successful", Toast.LENGTH_LONG).show();
                                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                                activity.FragmentView(0); // 메인엑티비티 내 선언된 FragmentView() 메소드 호출. 해당 값은 백엔드에서 가져올 예쩡
                            } else{
                                Toast.makeText(getContext(), "Registration Fail", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
            }
        });


        back_sign = rootView.findViewById(R.id.back_signUp);
        back_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity(); // 프래그먼트에서 메인엑티비티 접근
                activity.FragmentView(6);
            }
        });

        return rootView;
    }

}