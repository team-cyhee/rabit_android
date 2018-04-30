package com.cyhee.android.rabit;
/*
 * TODO: login status saving
 * DONE email, password save option
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public SharedPreferences sharedPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* register */
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent (LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        sharedPre = getSharedPreferences("email", Context.MODE_PRIVATE);

        /* login */

        final EditText emailText = findViewById(R.id.emailText);
        final EditText passwordText = findViewById(R.id.passwordText);

        final String defaultEmail = sharedPre.getString("email","NONE");
        emailText.setText(defaultEmail);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                /* set email as default */
                if (!defaultEmail.equals(email)) {
                    SharedPreferences.Editor editor = sharedPre.edit();
                    editor.putString("email", email);
                    editor.apply();
                }

                Response.Listener<String> responseLsn = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(LoginActivity.this, "welcome", Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(LoginActivity.this, MypageActivity.class);
                                LoginActivity.this.startActivity(loginIntent);
                                finish();
                            } else {
                                AlertDialog.Builder alt_bil = new AlertDialog.Builder(LoginActivity.this);
                                AlertDialog dialog = alt_bil.setMessage("wrong email or password")
                                        .setNegativeButton("ok", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginReq = new LoginRequest(email, password, responseLsn);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginReq);
            }
        });
    }
}