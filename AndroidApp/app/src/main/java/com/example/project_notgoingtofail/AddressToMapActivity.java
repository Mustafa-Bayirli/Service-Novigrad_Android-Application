package com.example.project_notgoingtofail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.SQLiteProject.SQLiteBranchDatabase;

import java.util.Arrays;
import java.util.List;

public class AddressToMapActivity extends AppCompatActivity {

    ListView mapList;
    List<String> addresses;

    SQLiteBranchDatabase databaseAccessor;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_to_map);

        databaseAccessor = new SQLiteBranchDatabase(this);

        mapList = (ListView) findViewById(R.id.mapList);

        final String[] branchNames = databaseAccessor.getBranchAsList();
        final String[] branchAddress = databaseAccessor.getBranchAddresses();

        String[] results = new String[branchNames.length-2];

        for (int i = 2; i < branchNames.length; i++) {
            results[i-2] = "Name: " + branchNames[i] + "\nAddress: " + branchAddress[i];
        }

        addresses = Arrays.asList(results);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addresses);
        mapList.setAdapter(adapter);

        mapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+branchAddress[position+2]));
                startActivity(intent);
            }
        });
    }
}