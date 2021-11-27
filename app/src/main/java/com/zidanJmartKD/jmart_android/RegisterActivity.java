package com.zidanJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zidanJmartKD.R;
import com.zidanJmartKD.jmart_android.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.registerButton);
        EditText inputNameRegisterActivity = findViewById(R.id.inputNameRegisterActivity);
        EditText inputEmailRegisterActivity = findViewById(R.id.inputEmailRegisterActivity);
        EditText inputPasswordRegisterActivity = findViewById(R.id.inputPasswordRegisterActivity);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterRequest newRegister = new RegisterRequest(
                        inputNameRegisterActivity.getText().toString(),
                        inputEmailRegisterActivity.getText().toString(),
                        inputPasswordRegisterActivity.getText().toString(),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject != null)
                                    {
                                        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                        Intent loginSuccess = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(loginSuccess);
                                    }
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }
}