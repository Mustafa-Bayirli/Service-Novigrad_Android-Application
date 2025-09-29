package com.example.project_notgoingtofail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.SQLiteProject.SQLiteBranchDatabase;
import com.example.SQLiteProject.SQLiteServiceDatabase;
import com.example.SQLiteProject.SQLiteUserDatabase;
import com.example.javaClasses.GlobalVariables;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button signupButton;
    EditText editUsername;
    EditText editPassword;

    SQLiteUserDatabase databaseAccessor;
    SQLiteBranchDatabase branchDataBase;
    SQLiteServiceDatabase serviceDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        databaseAccessor = new SQLiteUserDatabase(this);
        branchDataBase = new SQLiteBranchDatabase(this);
        serviceDatabase = new SQLiteServiceDatabase(this);

        databaseAccessor.createAdministrator();
        branchDataBase.createBaseBranch();
        serviceDatabase.createExampleServices();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editUsername.getText().toString().equals("")||editPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter a username and password.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isExist = databaseAccessor.checkUserExisting(editUsername.getText().toString(), editPassword.getText().toString());

                    if (isExist) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", editUsername.getText().toString());

                        GlobalVariables variables = (GlobalVariables) getApplicationContext();
                        variables.setUsername(editUsername.getText().toString());
                        variables.setPassword(editPassword.getText().toString());

                        startActivity(intent);
                    } else {
                        editPassword.setText(null);
                        Toast.makeText(LoginActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}