package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.javaClasses.GlobalVariables;

public class CustomerServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
    }
}