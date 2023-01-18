package com.androidprojects.loginnz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    String JWT = null;
    Bundle JWTbundle;
    Bundle IDbundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentView(0);
    }


    public void FragmentView(int fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragment){
            case 0 :
                LoginFragment loginFragment = new LoginFragment();
                transaction.replace(R.id.parentView, loginFragment).commit();
                break;
            case 1 :
                AdminFragment adminFragment = new AdminFragment();
                adminFragment.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, adminFragment).commit();
                break;
            case 2 :
                EmpFragment empFragment = new EmpFragment();
                empFragment.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, empFragment).commit();
                break;
            case 3:
                SignFragment signFragment = new SignFragment();
                signFragment.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, signFragment).commit();
                break;
            case 6: // Employee Work Info
                EmpWorkInfoFragment empWorkInfoFragment = new EmpWorkInfoFragment();
                empWorkInfoFragment.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, empWorkInfoFragment).commit();
                break;

            case 7: // private_detail_timesheet_emp
                PrivateDetailTimesheetFrag_emp privateDetailTimesheetFrag_emp = new PrivateDetailTimesheetFrag_emp();
                privateDetailTimesheetFrag_emp.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, privateDetailTimesheetFrag_emp).commit();
                break;

            case 8: // private_detail_timesheet_admin
                PrivateDetailTimesheetFrag_admin privateDetailTimesheetFrag_admin = new PrivateDetailTimesheetFrag_admin();
                privateDetailTimesheetFrag_admin.setArguments(JWTbundle);
                transaction.replace(R.id.parentView, privateDetailTimesheetFrag_admin).commit();
                break;

        }
    }

    public void setJWT(String jwt,String id) { // 로그인 프레그먼트에서 JWT를 받아 저장
        JWT = "token "+jwt;
        JWTbundle = new Bundle(); // 번들을 통해 값 전달
        JWTbundle.putString("JWT",JWT);//번들에 넘길 값 저장
        JWTbundle.putString("ID",id);//번들에 넘길 값 저장
        checkid(id); // 사용자 권한을 확인한 후 프레그먼트 로드
    }
    private void checkid(String id){
        String url = "http://20.211.44.13:5000/profile/?user="+id;
        makeRequest(url);
    }
    private void makeRequest(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map params = new HashMap();
                params.put("authorization", JWT);
                return params;
            }
        };
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void processResponse(String response ){ // json 데이터 파싱, response = 내가 요청한 get 값
        try{
            JSONObject jsonObject = new JSONObject(response);
            boolean is_manager = jsonObject.getBoolean("is_manager");
            if(is_manager){
                FragmentView(1);
            }
            else{
                FragmentView(2);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}



