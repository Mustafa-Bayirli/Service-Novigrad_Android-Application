package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.SQLiteProject.SQLiteUserDatabase;
import com.example.javaClasses.GlobalVariables;

public class MainActivity extends AppCompatActivity {

    TextView hello;
    TextView type;
    Button go;

    Intent intent;

    SQLiteUserDatabase databaseAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAccessor = new SQLiteUserDatabase(this);

        hello = (TextView) findViewById(R.id.helloUser);
        type = (TextView) findViewById(R.id.userType);
        go = (Button) findViewById(R.id.continueButton);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
        String username = variables.getUsername();
        String password = variables.getPassword();

        String name = (String )(databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.firstName) +
                " " + databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.lastName));



        hello.setText("hello " + name );

        int userType = -1;
        String userTypeString;

        try {
            userTypeString = databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.userType);
            userType = Integer.parseInt(userTypeString);
        } catch (Exception e) {}

        switch(userType) {
            case 0:
                userTypeString = " customer";
                intent = new Intent(this, CustomerActivity.class);
                break;
            case 1:
                userTypeString = " branch employee";
                intent = new Intent(this, BranchEmployeeActivity.class);
                break;
            case 2:
                userTypeString = "n admin";
                intent = new Intent(this, AdminMainActivity.class);
                break;
            default:
                userTypeString = " Error";
                intent = new Intent(this, ErrorActivity.class);
                break;
        }

        type.setText("You are a" + userTypeString);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}