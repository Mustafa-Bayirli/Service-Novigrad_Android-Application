package com.example.project_notgoingtofail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.example.SQLiteProject.SQLiteBranchDatabase;
import com.example.SQLiteProject.SQLiteServiceDatabase;
import com.example.SQLiteProject.SQLiteUserDatabase;
import com.example.javaClasses.RequestMaster;

import java.util.Arrays;
import java.util.List;

public class AdminBranchServiceEditingActivity extends AppCompatActivity{

    SQLiteServiceDatabase serviceDatabase;
    ArrayAdapter<String> adapter;

    Button backButton;
    Button createButton;

    ListView listViewServices;
    List<String> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_branch_service_editing);

        serviceDatabase = new SQLiteServiceDatabase(this);

        backButton = (Button) findViewById(R.id.adminServiceBackButton);
        createButton = (Button) findViewById(R.id.adminNewServiceButton);

        listViewServices = (ListView) findViewById(R.id.listViewService);
        services = Arrays.asList(serviceDatabase.getServicesAsList());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, services);
        listViewServices.setAdapter(adapter);

        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdateService(position);
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
                showCreateService();
            }
        });
    }

    public void showCreateService() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.create_dialog_service, null);
        dialogBuilder.setView(dialogView);

        final TextView ServiceID = (TextView) dialogView.findViewById(R.id.ServiceIDNew);
        final EditText ServiceName = (EditText) dialogView.findViewById(R.id.ServiceName);
        final CheckBox ResidenceProof = (CheckBox) dialogView.findViewById(R.id.ServicePOR);
        final CheckBox StatusProof = (CheckBox) dialogView.findViewById(R.id.ServicePOS);
        final CheckBox UserPhoto = (CheckBox) dialogView.findViewById(R.id.ServicePOU);
        final EditText ServiceQuestion = (EditText) dialogView.findViewById(R.id.ServiceQuestion);
        final Button createService = (Button) dialogView.findViewById(R.id.ServicebuttonCreate);
        final Button backButton = (Button) dialogView.findViewById(R.id.ServicebuttonClose);

        if (services.size() == 0) {
            ServiceID.setText("1");
        } else {
            int ID = 1;
            while (serviceDatabase.checkExistingService(ID)) ID++;
            ServiceID.setText(ID+"");
        }

        final AlertDialog b = dialogBuilder.create();
        b.show();

        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ServiceName.getText().toString().equals("")) {
                    Toast.makeText(AdminBranchServiceEditingActivity.this, "Please make sure that the entered name is valid", Toast.LENGTH_SHORT).show();;
                } else {
                    RequestMaster newService = new RequestMaster(Integer.parseInt(ServiceID.getText().toString()), ServiceName.getText().toString(), ResidenceProof.isChecked(), StatusProof.isChecked(), UserPhoto.isChecked(), ServiceQuestion.getText().toString());
                    serviceDatabase.createNewService(newService);
                    services = Arrays.asList(serviceDatabase.getServicesAsList());
                    adapter = new ArrayAdapter<String>(AdminBranchServiceEditingActivity.this, android.R.layout.simple_list_item_1, services);
                    listViewServices.setAdapter(adapter);
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

    public void showUpdateService(int ID) {

        RequestMaster service = serviceDatabase.getService(ID);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_service, null);
        dialogBuilder.setView(dialogView);

        final int thisID = ID;

        final TextView ServiceID = (TextView) dialogView.findViewById(R.id.UpdateServiceID);
        final EditText ServiceName = (EditText) dialogView.findViewById(R.id.UpdateServiceName);
        final CheckBox ResidenceProof = (CheckBox) dialogView.findViewById(R.id.UpdateServicePOR);
        final CheckBox StatusProof = (CheckBox) dialogView.findViewById(R.id.UpdateServicePOS);
        final CheckBox UserPhoto = (CheckBox) dialogView.findViewById(R.id.UpdateServicePOU);
        final EditText ServiceQuestion = (EditText) dialogView.findViewById(R.id.UpdateServiceQuestion);
        final Button updateService = (Button) dialogView.findViewById(R.id.UpdateServicebuttonUpdate);
        final Button backButton = (Button) dialogView.findViewById(R.id.UpdateServicebuttonClose);



        ServiceID.setText("Service ID: " + ID);
        ServiceName.setText(service.getName());
        ResidenceProof.setChecked(service.getPOR());
        StatusProof.setChecked(service.getPOS());
        UserPhoto.setChecked(service.getPOU());
        ServiceQuestion.setText(service.getOtherInformation());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ServiceName.getText().toString().equals("")) {
                    Toast.makeText(AdminBranchServiceEditingActivity.this, "Please make sure that the entered name is valid", Toast.LENGTH_SHORT).show();;
                } else {
                    RequestMaster newService = new RequestMaster(thisID, ServiceName.getText().toString(), ResidenceProof.isChecked(), StatusProof.isChecked(), UserPhoto.isChecked(), ServiceQuestion.getText().toString());
                    serviceDatabase.updateService(thisID, newService);
                    services = Arrays.asList(serviceDatabase.getServicesAsList());
                    adapter = new ArrayAdapter<String>(AdminBranchServiceEditingActivity.this, android.R.layout.simple_list_item_1, services);
                    listViewServices.setAdapter(adapter);
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
}