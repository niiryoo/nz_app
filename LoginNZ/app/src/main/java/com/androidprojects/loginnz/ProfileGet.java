package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfileGet extends StringRequest {
    final static String url = "http://20.211.44.13:5000/profile/";
    final static String searchurl = "http://20.211.44.13:5000/profile/?user=";
    String JWT = null;
    private Map<String, String> map;

    public ProfileGet(String jwt,
                   Response.Listener<String> listener) { //모든 유저 프로파일 조회 - HTTP 헤더에 인증 토큰 요구됨
        super(Request.Method.GET, url, listener, null);
        this.JWT = jwt;
    }
    public ProfileGet(String jwt, String user_id,
                      Response.Listener<String> listener) {
        super(Request.Method.GET, searchurl+user_id, listener, null);
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