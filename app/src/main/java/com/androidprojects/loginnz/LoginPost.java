package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginPost extends StringRequest {
    final static String url = "http://20.211.44.13:5000/login/";

    private Map<String, String> map;

    public LoginPost(String userID, String password,
                   Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        map = new HashMap<>();
        map.put("username", userID);
        map.put("password", password);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError { // 헤더에 정보 추가
        Map params = new HashMap();
        return params;
    }

}
