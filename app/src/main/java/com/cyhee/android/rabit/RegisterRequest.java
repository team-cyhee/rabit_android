package com.cyhee.android.rabit;

/*
 * TODO: make errorListener
 * DONE check email is valid 1) is it duplicated?
 * DONE how to deal with possible null values (phone, age)
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String url = "http://192.168.168.105/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String password,
                           String phone, String age, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        String realPhone = phone;
        String realAge = age;
        if (realPhone.equals("")) realPhone = "0";
        if (realAge.equals("")) realAge = "0";
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("phone", realPhone);
        params.put("age", realAge);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
