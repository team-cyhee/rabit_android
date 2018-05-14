package com.cyhee.android.rabit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String url = "http://192.168.168.105/Login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        /*
         * TODO: make errorListener
         */
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
