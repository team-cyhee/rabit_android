package com.cyhee.android.rabit;

// TODO: make passwordCheck react in real time

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    public SharedPreferences sharedPre;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPre = getSharedPreferences("email", Context.MODE_PRIVATE);

        final EditText nameText = findViewById(R.id.nameText);
        final EditText emailText = findViewById(R.id.emailText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final EditText passwordCheckText = findViewById(R.id.passwordCheckText);
        final EditText phoneText = findViewById(R.id.phoneText);
        final EditText ageText = findViewById(R.id.ageText);


        /* get email */
        AccountManager acntManager = AccountManager.get(this);
        Account[] accounts = acntManager.getAccounts();
        emailText.setText(accounts[0].name);

        final String defaultEmail = sharedPre.getString("email","NONE");
        if (!defaultEmail.equals(""))  emailText.setText(defaultEmail);

        /* get phone number */
        TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String devicePhoneNumber = telManager.getLine1Number();
        phoneText.setText(devicePhoneNumber);

        /* register */
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String passwordCheck = passwordCheckText.getText().toString();
                String phone = phoneText.getText().toString();
                String age = ageText.getText().toString();

                /* set email as default */
                if (!defaultEmail.equals(email)) {
                    SharedPreferences.Editor editor = sharedPre.edit();
                    editor.putString("email", email);
                    editor.apply();
                }

                AlertDialog dialog;
                AlertDialog.Builder alt_bil = new AlertDialog.Builder(RegisterActivity.this);
                /* check if name, password, phone is null */
                // TODO: make code simpler
                if (name.equals("")) {
                    dialog = alt_bil.setMessage("name, please...")
                            .setCancelable(false)
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nameText.setSelection(nameText.getText().length());
                                            dialog.cancel();
                                        }
                                    })
                            .create();
                    dialog.show();
                    return;
                } else if (email.equals("")) {
                    dialog = alt_bil.setMessage("email, please...")
                            .setCancelable(false)
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            emailText.setSelection(emailText.getText().length());
                                            dialog.cancel();
                                        }
                                    })
                            .create();
                    dialog.show();
                    return;
                }else if (password.equals("")) {
                    dialog = alt_bil.setMessage("password, please...")
                            .setCancelable(false)
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            passwordText.setSelection(passwordText.getText().length());
                                            dialog.cancel();
                                        }
                                    })
                            .create();
                    dialog.show();
                    return;
                } else if (!passwordCheck.equals(password)) {
                    dialog = alt_bil.setMessage("password checking is wrong")
                            .setCancelable(false)
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            passwordCheckText.setText("");
                                            passwordCheckText.setSelection(passwordCheckText.getText().length());
                                            dialog.cancel();
                                        }
                                    })
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseLsn = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRsp = new JSONObject(response);
                            boolean success = jsonRsp.getBoolean("success");
                            if (success) {
                                Toast.makeText(RegisterActivity.this, "welcome to join", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                AlertDialog.Builder alt_bil = new AlertDialog.Builder(RegisterActivity.this);
                                AlertDialog dialog = alt_bil.setMessage("the email has an account")
                                        .setNegativeButton("ok", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerReq = new RegisterRequest(name, email, password, phone, age, responseLsn);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerReq);
            }
        });

    }
}
