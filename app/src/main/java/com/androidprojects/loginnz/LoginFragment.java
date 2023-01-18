package com.androidprojects.loginnz;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    public EditText edit_id, edit_password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false); // fragment_login 레이아웃들(레이아웃, 위젯등)을 객체화 하여 메모리에 올리는 작업.
        edit_id = (EditText) rootView.findViewById(R.id.emp_id);
        edit_password = (EditText) rootView.findViewById(R.id.emp_password);

        Button login = rootView.findViewById(R.id.loginbutton); //버튼 지정
        login.setOnClickListener(new View.OnClickListener() { // 클릭 리스너 설정
            @Override
            public void onClick(View view) { // 클릭시 이벤트 설정
                loginRequest();
            }
        });
        return rootView;
    }

    private void loginRequest() { // 로그인 정보 파싱후 처리
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject user = jsonObject.getJSONObject("user");
                    String id = user.getString("id");
                    String token = jsonObject.getString("token");
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setJWT(token, id);
                } catch (JSONException ex) {
                    ex.printStackTrace();

                }
            }
        };

        LoginPost LoginPost = new LoginPost(edit_id.getText().toString(), edit_password.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(LoginPost);
    }
}