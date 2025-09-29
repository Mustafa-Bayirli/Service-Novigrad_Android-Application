package com.example.project_notgoingtofail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SQLiteProject.SQLiteUserDatabase;

import java.util.*;
import java.util.List;

public class AdminUserEditingActivity extends AppCompatActivity {

    SQLiteUserDatabase databaseAccessor;

    ArrayAdapter<String> adapter;

    Button backButton;

    ListView listViewUsers;
    List<String> users;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_editing);

        databaseAccessor = new SQLiteUserDatabase(this);

        backButton = (Button) findViewById(R.id.adminBackButton);

        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        users = Arrays.asList(databaseAccessor.getUsersList());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
        listViewUsers.setAdapter(adapter);

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AdminUserEditingActivity.this, "Password: " + databaseAccessor.getPassword(users.get(i)), Toast.LENGTH_SHORT).show();

            }
        });

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt((String) (databaseAccessor.getDatabaseData(users.get(i), databaseAccessor.getPassword(users.get(i)), SQLiteUserDatabase.DATABASE_DATA.userType))) != 2) {
                    showUpdateDeleteCustomer(users.get(i), databaseAccessor.getPassword(users.get(i)));
                } else {
                    Toast.makeText(AdminUserEditingActivity.this, "This is an admin account, you cannot edit it", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showUpdateDeleteCustomer(final String username, final String password) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_customer, null);
        dialogBuilder.setView(dialogView);

        final TextView userName = (TextView) dialogView.findViewById(R.id.showUsername);
        final TextView userPassword  = (TextView) dialogView.findViewById(R.id.showUserPassword);
        final TextView userFirstName = (TextView) dialogView.findViewById(R.id.showUserFirstName);
        final TextView userLastName  = (TextView) dialogView.findViewById(R.id.showUserLastName);
        final TextView userType  = (TextView) dialogView.findViewById(R.id.showUserType);
        final Button closeDialog = (Button) dialogView.findViewById(R.id.buttonCloseDialog);
        final Button deleteUser = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        userName.setText("Username: " + username);
        userPassword.setText("Password: " + password);
        userFirstName.setText("First name: " + databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.firstName).toString());
        userLastName.setText("Last name: " + databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.lastName).toString());
        userType.setText("User type: " + databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.userType).toString());

        dialogBuilder.setTitle("Editing user: " + username);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseAccessor.deleteUser(username)) {
                    Toast.makeText(AdminUserEditingActivity.this, "User Successfully Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminUserEditingActivity.this, "Error Deleting User", Toast.LENGTH_SHORT).show();
                }
                users = Arrays.asList(databaseAccessor.getUsersList());
                adapter = new ArrayAdapter<String>(AdminUserEditingActivity.this, android.R.layout.simple_list_item_1, users);
                listViewUsers.setAdapter(adapter);
                b.dismiss();
            }
        });
    }
}