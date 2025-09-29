package com.example.project_notgoingtofail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SQLiteProject.SQLiteBranchDatabase;
import com.example.SQLiteProject.SQLiteServiceDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminBranchEditingActivity extends AppCompatActivity {

    SQLiteBranchDatabase databaseAccessor;
    SQLiteServiceDatabase serviceDatabase;

    ArrayAdapter<String> adapter;

    Button backButton;
    Button createButton;

    ListView listViewBranches;
    List<String> branches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_branch_editing);

        databaseAccessor = new SQLiteBranchDatabase(this);
        serviceDatabase = new SQLiteServiceDatabase(this);

        backButton = (Button) findViewById(R.id.adminBranchBackButton);
        createButton = (Button) findViewById(R.id.adminNewBranchButton);

        listViewBranches = (ListView) findViewById(R.id.listViewBranch);
        branches = Arrays.asList(databaseAccessor.getBranchAsList());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branches);
        listViewBranches.setAdapter(adapter);

        listViewBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 || i == 1) {
                    Toast.makeText(AdminBranchEditingActivity.this, "This is a default branch, you cannot edit it", Toast.LENGTH_SHORT).show();
                } else {
                }

            }
        });

        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 || i == 1) {
                    Toast.makeText(AdminBranchEditingActivity.this, "This is a default branch, you cannot edit it", Toast.LENGTH_SHORT).show();
                } else {
                    showUpdateBranch(i);
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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateBranch();
            }
        });
    }

    private void showCreateBranch() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.create_dialog_branch, null);
        dialogBuilder.setView(dialogView);

        final TextView branchID = (TextView) dialogView.findViewById(R.id.showBranchIDCreate);
        final EditText branchName = (EditText) dialogView.findViewById(R.id.BranchNameCreate);
        final Button createBranch = (Button) dialogView.findViewById(R.id.buttonCreate);
        final Button backButton = (Button) dialogView.findViewById(R.id.buttonBackCreatePhoto);

        if (branches.size() == 0) {
            branchID.setText("1");
        } else {
            int ID = 1;
            while (databaseAccessor.checkBranchExisting(ID)) ID++;
            branchID.setText(ID+"");
        }

        dialogBuilder.setTitle("Create Branch: " + branchID.getText().toString());
        final AlertDialog b = dialogBuilder.create();
        b.show();


        createBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchName.getText().toString().equals("")) {
                    Toast.makeText(AdminBranchEditingActivity.this, "Please make sure there is a name", Toast.LENGTH_SHORT).show();
                } else {
                    databaseAccessor.createNewBranch(Integer.parseInt(branchID.getText().toString()), branchName.getText().toString());
                    Toast.makeText(AdminBranchEditingActivity.this, "Branch Created\nTo Add Services, please edit the branch", Toast.LENGTH_LONG).show();
                    branches = Arrays.asList(databaseAccessor.getBranchAsList());
                    adapter = new ArrayAdapter<String>(AdminBranchEditingActivity.this, android.R.layout.simple_list_item_1, branches);
                    listViewBranches.setAdapter(adapter);

                    b.dismiss();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

    private void showUpdateBranch(int ID) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_branch, null);
        dialogBuilder.setView(dialogView);

        final int IDbranch = ID;


        final TextView branchID = dialogView.findViewById(R.id.showBranchID);
        final EditText branchName = dialogView.findViewById(R.id.branchName);
        final Button updateName = dialogView.findViewById(R.id.updateBranch);
        final Button deleteBranch = dialogView.findViewById(R.id.buttonDeleteBranch);
        final EditText ServiceToAdd = dialogView.findViewById(R.id.ServiceIDToAdd);
        final Button AddService = dialogView.findViewById(R.id.AddServiceButton);
        final ListView services = dialogView.findViewById(R.id.listViewServicesBranch);

        final List<String> servicesAdded = Arrays.asList(databaseAccessor.getServicesProvided(IDbranch));
        final ArrayList<String> serviceNames = new ArrayList<String>();
        for (int i = 0; i < servicesAdded.size(); i++) {
            if (servicesAdded.get(i) != "") serviceNames.add(serviceDatabase.getService(Integer.parseInt(servicesAdded.get(i))).getName() + " (ID: " + servicesAdded.get(i) + ")");
        }

        ArrayAdapter<String> servicesAdaper = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, serviceNames);
        services.setAdapter(servicesAdaper);

        branchID.setText("Branch ID: " + IDbranch);

        branchName.setText(databaseAccessor.getDatabaseData(IDbranch, SQLiteBranchDatabase.DATABASE_DATA.name).toString());


        dialogBuilder.setTitle("Update Branch: " + IDbranch);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchName.getText().toString().equals("")) {
                    Toast.makeText(AdminBranchEditingActivity.this, "Please make sure there is a name", Toast.LENGTH_SHORT).show();
                } else {
                    databaseAccessor.updateBranch(IDbranch, branchName.getText().toString());
                    Toast.makeText(AdminBranchEditingActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();

                    branches = Arrays.asList(databaseAccessor.getBranchAsList());
                    adapter = new ArrayAdapter<String>(AdminBranchEditingActivity.this, android.R.layout.simple_list_item_1, branches);
                    listViewBranches.setAdapter(adapter);

                }
            }
        });

        deleteBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAccessor.deleteBranch(IDbranch);
                Toast.makeText(AdminBranchEditingActivity.this, "Branch Deleted", Toast.LENGTH_SHORT).show();
                branches = Arrays.asList(databaseAccessor.getBranchAsList());
                adapter = new ArrayAdapter<String>(AdminBranchEditingActivity.this, android.R.layout.simple_list_item_1, branches);
                listViewBranches.setAdapter(adapter);

                b.dismiss();
            }
        });

        AddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ServiceToAdd.getText().toString().equals("")) {
                    Toast.makeText(AdminBranchEditingActivity.this, "Please enter an ID first", Toast.LENGTH_SHORT).show();
                } else {
                    if (serviceDatabase.checkExistingService(Integer.parseInt(ServiceToAdd.getText().toString()))) {
                        String[] temp = databaseAccessor.getServicesProvided(IDbranch);
                        ArrayList<String> servicesList = new ArrayList<String>(Arrays.asList(temp));
                        servicesList.remove("");
                        if (servicesList.contains(ServiceToAdd.getText().toString())) {
                            Toast.makeText(AdminBranchEditingActivity.this, "Service ID is already added", Toast.LENGTH_SHORT).show();
                        } else {
                            servicesList.add(ServiceToAdd.getText().toString());
                            Collections.sort(servicesList);
                            temp = servicesList.toArray(new String[servicesList.size()]);
                            databaseAccessor.updateServices(IDbranch, temp);

                            final List<String> servicesAdded = Arrays.asList(databaseAccessor.getServicesProvided(IDbranch));
                            final ArrayList<String> serviceNames = new ArrayList<String>();
                            for (int i = 0; i < servicesAdded.size(); i++) {
                                serviceNames.add(serviceDatabase.getService(Integer.parseInt(servicesAdded.get(i))).getName() + " (ID: " + servicesAdded.get(i) + ")");
                            }

                            ArrayAdapter<String> servicesAdaper = new ArrayAdapter<String>(AdminBranchEditingActivity.this, android.R.layout.simple_list_item_1, serviceNames);
                            services.setAdapter(servicesAdaper);
                        }
                    } else {
                        Toast.makeText(AdminBranchEditingActivity.this, "Service ID is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        services.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String[] temp = databaseAccessor.getServicesProvided(IDbranch);
                    List<String> list = Arrays.asList(temp);
                    list.remove(servicesAdded.get(position));
                    temp = list.toArray(new String[list.size()]);
                    databaseAccessor.updateServices(IDbranch, temp);

                    final List<String> servicesAdded = Arrays.asList(databaseAccessor.getServicesProvided(IDbranch));
                    final ArrayList<String> serviceNames = new ArrayList<String>();
                    for (int i = 0; i < servicesAdded.size(); i++) {
                        serviceNames.add(serviceDatabase.getService(Integer.parseInt(servicesAdded.get(i))).getName() + " (ID: " + servicesAdded.get(i) + ")");
                    }

                    ArrayAdapter<String> servicesAdaper = new ArrayAdapter<String>(AdminBranchEditingActivity.this, android.R.layout.simple_list_item_1, serviceNames);
                    services.setAdapter(servicesAdaper);

                    Toast.makeText(AdminBranchEditingActivity.this, "Service Removed", Toast.LENGTH_SHORT).show();

                    return true;
                } catch (Exception e) {
                    Toast.makeText(AdminBranchEditingActivity.this, "Service could not be removed", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }
}