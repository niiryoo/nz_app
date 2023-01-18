package com.androidprojects.loginnz;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DepartmentGet extends StringRequest {
    final static String url = "http://20.211.44.13:5000/department/";
    final static String searchurl = "http://20.211.44.13:5000/department/?code=";
    String JWT = null;
    private Map<String, String> map;

    public DepartmentGet(String jwt, Response.Listener<String> listener) { // 모든 부서 정보 조회 - HTTP 헤더에 인증 토큰 요구됨 (개발버전은 필요하지 않음)
        super(Request.Method.GET, url, listener, null);
        this.JWT = jwt;
    }

    public DepartmentGet(String jwt, String dep_code, Response.Listener<String> listener){ // 부서 정보 코드로 조회
        super(Request.Method.GET, searchurl+dep_code, listener, null);
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
