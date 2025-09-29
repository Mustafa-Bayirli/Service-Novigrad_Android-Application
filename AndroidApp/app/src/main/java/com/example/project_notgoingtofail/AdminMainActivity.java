package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {

    Button editUsers;
    Button editBranches;
    Button editServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        editUsers = (Button) findViewById(R.id.buttonAdminEditUser);
        editBranches = (Button) findViewById(R.id.buttonAdminEditBranches);
        editServices = (Button) findViewById(R.id.buttonAdminEditServices);

        editUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminUserEditingActivity.class);
                startActivity(intent);
            }

        });

        editBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminBranchEditingActivity.class);
                startActivity(intent);
            }

        });

        editServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminBranchServiceEditingActivity.class);
                startActivity(intent);
            }

        });
    }
}