package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TimesheetGet extends StringRequest {
    final static String url = "http://20.211.44.13:5000/timesheet/";
    final static String searchurl = "http://20.211.44.13:5000/timesheet/?user=";
    String JWT = null;
    private Map<String, String> map;
    public TimesheetGet(String jwt, Response.Listener<String> listener) { // 모든 user에 대한 timesheet GET
        super(Request.Method.GET, url, listener, null);
        this.JWT = jwt;
    }

    public TimesheetGet(String userID, String jwt, Response.Listener<String> listener) { // 특정 유저 추출
        super(Request.Method.GET, searchurl+userID, listener, null);
        this.JWT = jwt;
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
