package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EndPost extends StringRequest {
    final static String url = "http://20.211.44.13:5000/timesheet/";
    String JWT = null;
    private Map<String, String> map;

    public EndPost(String userID, String jwt, String note,
                     Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        this.JWT = jwt;
        map = new HashMap<>();
        map.put("user_id", userID);
        map.put("action", "end");
        map.put("notes", note);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError { // 헤더에 정보 추가
        Map params = new HashMap();
        params.put("authorization", JWT);
        return params;
    }

}