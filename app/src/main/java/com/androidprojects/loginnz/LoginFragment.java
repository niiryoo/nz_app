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
    static RequestQueue requestQueue;
    String urlStr = "http://20.211.44.13:5000/login/";
    public EditText edit_id,edit_password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false); // fragment_login 레이아웃들(레이아웃, 위젯등)을 객체화 하여 메모리에 올리는 작업.
        edit_id = (EditText)rootView.findViewById(R.id.emp_id);
        edit_password = (EditText)rootView.findViewById(R.id.emp_password);

        Button login = rootView.findViewById(R.id.loginbutton); //버튼 지정
        login.setOnClickListener(new View.OnClickListener() { // 클릭 리스너 설정
            @Override
            public void onClick(View view) { // 클릭시 이벤트 설정
                makeRequest();
            }
        });
        return rootView;
    }
    private void makeRequest() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, urlStr, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"연결오류",Toast.LENGTH_LONG).show();
            }
        }) {

            //만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            //이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            //POST방식으로 안할거면 없어도 되는거같다.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",edit_id.getText().toString());
                params.put("password",edit_password.getText().toString());
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
    private void processResponse(String response) { //Json 데이터 파싱
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject user = jsonObject.getJSONObject("user");
            String id = user.getString("id");
            String token = jsonObject.getString("token");
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.setJWT(token,id);

        } catch (JSONException e) {
            Log.d("error", String.valueOf(e));
            e.printStackTrace();
        }
    }
}