package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StartPost extends StringRequest {
    final static String url = "http://puni.mooo.com:5000/timesheet/";
    String JWT = null;
    private Map<String, String> map;

    public StartPost(String userID, String jwt, String DEPCODE, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        this.JWT = jwt;
        map = new HashMap<>();
        map.put("user_id", userID);
        map.put("department", DEPCODE);
        map.put("action", "start");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError { // 헤더에 정보 추가
        Map params = new HashMap();
        params.put("authorization", JWT);
        return params;
    }

}
