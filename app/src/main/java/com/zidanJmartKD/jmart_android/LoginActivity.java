package com.zidanJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.model.Account;
import com.zidanJmartKD.jmart_android.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount()
    {
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText inputEmailLogin = findViewById(R.id.inputEmailLogin);
        EditText inputPasswordLogin = findViewById(R.id.inputPasswordLogin);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerNowHyperlink = findViewById(R.id.registerNowHyperLink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest newLogin = new LoginRequest(
                        inputEmailLogin.getText().toString(),
                        inputPasswordLogin.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject != null) {
                                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                        loggedAccount = gson.fromJson(jsonObject.toString(), Account.class);
                                        Intent loginSuccess = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(loginSuccess);
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(newLogin);
            }
        });
        registerNowHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }

        });
    }
}