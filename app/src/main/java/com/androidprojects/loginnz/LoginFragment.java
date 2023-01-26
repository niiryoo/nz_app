package com.androidprojects.loginnz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class LoginFragment extends Fragment {
    public EditText edit_id, edit_password;
    String sSSID;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false); // fragment_login 레이아웃들(레이아웃, 위젯등)을 객체화 하여 메모리에 올리는 작업.
        edit_id = (EditText) rootView.findViewById(R.id.emp_id);
        edit_password = (EditText) rootView.findViewById(R.id.emp_password);

        sSSID = getWiFiSSID(getActivity()).toString();
        String SSID = "\"KelvinWiFi\"";
        Button login = rootView.findViewById(R.id.loginbutton); //버튼 지정
        login.setOnClickListener(new View.OnClickListener() { // 클릭 리스너 설정
            @Override
            public void onClick(View view) { // 클릭시 이벤트 설정
                if(sSSID.equals(SSID)){
                    loginRequest();
                }
                else{
                    Toast.makeText(getContext(),sSSID,Toast.LENGTH_LONG).show();
                }
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

    public String getWiFiSSID(Context mContext)
    {
        WifiManager manager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

}