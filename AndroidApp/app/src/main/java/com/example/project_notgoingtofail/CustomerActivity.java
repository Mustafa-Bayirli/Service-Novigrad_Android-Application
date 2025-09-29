package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.SQLiteProject.SQLiteServiceDatabase;
import com.example.javaClasses.GlobalVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    ListView services;

    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteServiceDatabase serviceDatabase = new SQLiteServiceDatabase(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        services = (ListView) findViewById(R.id.customerListView);
        mapButton = (Button) findViewById(R.id.findByAddress);

        final List<String> serviceNames = Arrays.asList(serviceDatabase.getServicesAsList());
        ArrayAdapter<String> servicesAdaper = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, serviceNames);
        services.setAdapter(servicesAdaper);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddressToMapActivity.class);
                startActivity(intent);
            }
        });

        services.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalVariables variables = (GlobalVariables) getApplicationContext();
                variables.setServiceWanted(serviceNames.get(position));
                Intent intent = new Intent(CustomerActivity.this, CustomerServiceActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}