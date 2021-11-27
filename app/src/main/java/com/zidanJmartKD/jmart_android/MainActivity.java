package com.zidanJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.zidanJmartKD.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView HelloUser_Main = findViewById(R.id.HelloUser_Main);
        HelloUser_Main.setText("Hello " + LoginActivity.getLoggedAccount().name + "!");
    }
}