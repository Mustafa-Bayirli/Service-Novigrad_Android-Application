package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SQLiteProject.SQLiteBranchDatabase;
import com.example.SQLiteProject.SQLiteUserDatabase;
import com.example.javaClasses.GlobalVariables;

public class RegisterActivity extends AppCompatActivity {

    Button createAccountButton;
    Button createBackButton;
    static EditText createUsername;
    static EditText createPassword;
    static EditText createFirstName;
    static EditText createLastName;
    CheckBox createBranchAccount;
    EditText branchIDNumber;

    SQLiteUserDatabase databaseAccessor;
    SQLiteBranchDatabase branchDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createBackButton = (Button) findViewById(R.id.createBackButton);
        createUsername = (EditText) findViewById(R.id.createUsername);
        createPassword = (EditText) findViewById(R.id.createPassword);
        createFirstName = (EditText) findViewById(R.id.createFirstName);
        createLastName = (EditText) findViewById(R.id.createLastName);
        createBranchAccount = (CheckBox) findViewById(R.id.createBranchAccount);
        branchIDNumber = (EditText) findViewById(R.id.branchIDNumber);
        databaseAccessor = new SQLiteUserDatabase(RegisterActivity.this);
        branchDatabase = new SQLiteBranchDatabase(RegisterActivity.this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Integer.parseInt(branchIDNumber.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Please make sure an integer was entered for the branch ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (createUsername.getText().toString().equals("") || createPassword.getText().toString().equals("") || createFirstName.getText().toString().equals("") || createLastName.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please make sure that all fields are filled in.", Toast.LENGTH_SHORT).show();
                } else if (createBranchAccount.isChecked() && branchDatabase.getBranchAsList().length==2) {
                    Toast.makeText(RegisterActivity.this, "There are no branches in service right now, Contact an administrator", Toast.LENGTH_SHORT).show();
                } else if (createBranchAccount.isChecked() && branchIDNumber.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please select a branch id", Toast.LENGTH_SHORT).show();
                } else if (createBranchAccount.isChecked() && (Integer.parseInt(branchIDNumber.getText().toString()) >= branchDatabase.getBranchAsList().length || Integer.parseInt(branchIDNumber.getText().toString()) < 0)) {
                    Toast.makeText(RegisterActivity.this, "There is no branch in service right now, Contact an administrator", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isExist = databaseAccessor.checkUsername(createUsername.getText().toString());

                    if (isExist) {
                        Toast.makeText(RegisterActivity.this, "Username " + createUsername.getText().toString() + " is already taken, please enter another username.", Toast.LENGTH_SHORT).show();
                    } else {
                        int type = createBranchAccount.isChecked() ? 1 : 0;
                        String branchID;
                        if (type == 0) {
                            branchID = "0";
                        } else if (type == 1) {
                            branchID = (branchIDNumber.getText().toString());
                        } else {
                            branchID = "1";
                        }

                        String[] userData = {
                                createUsername.getText().toString(),
                                createPassword.getText().toString(),
                                createFirstName.getText().toString(),
                                createLastName.getText().toString(),
                                String.valueOf(type),
                                branchID
                        };
                        databaseAccessor.createNewUser(userData);

                        GlobalVariables variables = (GlobalVariables) getApplicationContext();
                        variables.setUsername(userData[0]);
                        variables.setPassword(userData[1]);

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        createBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
