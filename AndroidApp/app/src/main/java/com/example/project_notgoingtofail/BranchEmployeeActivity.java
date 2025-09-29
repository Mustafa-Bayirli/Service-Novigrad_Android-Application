package com.example.project_notgoingtofail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SQLiteProject.SQLiteBranchDatabase;
import com.example.SQLiteProject.SQLiteUserDatabase;
import com.example.javaClasses.GlobalVariables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BranchEmployeeActivity extends AppCompatActivity {

    SQLiteUserDatabase databaseAccessor;
    SQLiteBranchDatabase branchDatabase;

    TextView branchName;
    TextView workingHours;
    TextView address;
    TextView phone;

    Button viewRequests;
    Button editServices;

    Button editHours;
    Button editAddress;
    Button editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_employee);

        databaseAccessor = new SQLiteUserDatabase(this);
        branchDatabase = new SQLiteBranchDatabase(this);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
        String username = variables.getUsername();
        String password = variables.getPassword();
        final int branchID = Integer.parseInt(databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.branchID).toString());

        branchName = (TextView) findViewById(R.id.nameOfBranch);
        workingHours = (TextView) findViewById(R.id.workingHoursOfBranch);
        address = (TextView) findViewById(R.id.addressOfBranch);
        phone =  (TextView) findViewById(R.id.phoneNumberOfBranch);

        editHours = (Button) findViewById(R.id.buttonEditWorkingHours);
        editAddress = (Button) findViewById(R.id.buttonEditAddress);
        editPhone = (Button) findViewById(R.id.buttonEditPhoneNumber);

        viewRequests = (Button) findViewById(R.id.buttonViewRequests);

        branchName.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.name).toString());

        address.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.address).toString());
        phone.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.phoneNumber).toString());

        String[] openHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.openingHours).toString().split(",");
        String[] closeHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.closingHours).toString().split(",");

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)%7;
        Date date = Calendar.getInstance().getTime();

        workingHours.setText("Working hours for " + new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()) + ": " + openHours[day] + " to " + closeHours[day]);


        viewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchEmployeeActivity.this, BranchEmployeeViewRequestsActivity.class);
                startActivity(intent);
            }
        });

        editHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateWorkingHours();
            }
        });
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateAddress();
                address.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.address).toString());
            }
        });
        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePhoneNumber();
                phone.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.phoneNumber).toString());
            }
        });
    }

    private void showUpdateAddress() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_addressorphone, null);
        dialogBuilder.setView(dialogView);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
        String username = variables.getUsername();
        String password = variables.getPassword();
        final int branchID = Integer.parseInt(databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.branchID).toString());

        final EditText newString = (EditText) dialogView.findViewById(R.id.updateAddressText);
        final Button update = (Button) dialogView.findViewById(R.id.buttonUpdateAddress);
        final Button back = (Button) dialogView.findViewById(R.id.buttonBackAddress);

        newString.setHint("Please enter the address for the branch");

        dialogBuilder.setTitle("Update Address");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newString.getText().toString().equals("")) {
                    Toast.makeText(BranchEmployeeActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                } else {
                    branchDatabase.updateAddress(branchID, newString.getText().toString());
                    address.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.address).toString());
                    b.dismiss();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

    }

    private void showUpdatePhoneNumber() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_addressorphone, null);
        dialogBuilder.setView(dialogView);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
        String username = variables.getUsername();
        String password = variables.getPassword();
        final int branchID = Integer.parseInt(databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.branchID).toString());

        final EditText newString = (EditText) dialogView.findViewById(R.id.updateAddressText);
        final Button update = (Button) dialogView.findViewById(R.id.buttonUpdateAddress);
        final Button back = (Button) dialogView.findViewById(R.id.buttonBackAddress);

        newString.setHint("Please enter the phone number for the branch");

        dialogBuilder.setTitle("Update Phone Number");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newString.getText().toString().equals("")) {
                    Toast.makeText(BranchEmployeeActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                } else {
                    branchDatabase.updatePhoneNumber(branchID, newString.getText().toString());
                    phone.setText(branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.phoneNumber).toString());
                    b.dismiss();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

    }

    private void showUpdateWorkingHours() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_workinghours, null);
        dialogBuilder.setView(dialogView);

        GlobalVariables variables = (GlobalVariables) getApplicationContext();
        String username = variables.getUsername();
        String password = variables.getPassword();
        final int branchID = Integer.parseInt(databaseAccessor.getDatabaseData(username, password, SQLiteUserDatabase.DATABASE_DATA.branchID).toString());

        String[] openHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.openingHours).toString().split(",");
        String[] closeHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.closingHours).toString().split(",");

        final Spinner SundayOpen = (Spinner) dialogView.findViewById(R.id.SundayOpen);
        final Spinner SundayClose = (Spinner) dialogView.findViewById(R.id.SundayClose);

        final Spinner MondayOpen = (Spinner) dialogView.findViewById(R.id.MondayOpen);
        final Spinner MondayClose = (Spinner) dialogView.findViewById(R.id.MondayClose);

        final Spinner TuesdayOpen = (Spinner) dialogView.findViewById(R.id.TuesdayOpen);
        final Spinner TuesdayClose = (Spinner) dialogView.findViewById(R.id.TuesdayClose);

        final Spinner WednesdayOpen = (Spinner) dialogView.findViewById(R.id.WednesdayOpen);
        final Spinner WednesdayClose = (Spinner) dialogView.findViewById(R.id.WednesdayClose);

        final Spinner ThursdayOpen = (Spinner) dialogView.findViewById(R.id.ThursdayOpen);
        final Spinner ThursdayClose = (Spinner) dialogView.findViewById(R.id.ThursdayClose);

        final Spinner FridayOpen = (Spinner) dialogView.findViewById(R.id.FridayOpen);
        final Spinner FridayClose = (Spinner) dialogView.findViewById(R.id.FridayClose);

        final Spinner SaturdayOpen = (Spinner) dialogView.findViewById(R.id.SaturdayOpen);
        final Spinner SaturdayClose = (Spinner) dialogView.findViewById(R.id.SaturdayClose);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(BranchEmployeeActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hours));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SundayOpen.setAdapter(adapter);
        SundayClose.setAdapter(adapter);
        MondayOpen.setAdapter(adapter);
        MondayClose.setAdapter(adapter);
        TuesdayOpen.setAdapter(adapter);
        TuesdayClose.setAdapter(adapter);
        WednesdayOpen.setAdapter(adapter);
        WednesdayClose.setAdapter(adapter);
        ThursdayOpen.setAdapter(adapter);
        ThursdayClose.setAdapter(adapter);
        FridayOpen.setAdapter(adapter);
        FridayClose.setAdapter(adapter);
        SaturdayOpen.setAdapter(adapter);
        SaturdayClose.setAdapter(adapter);

        SundayOpen.setSelection(adapter.getPosition(openHours[0]));
        MondayOpen.setSelection(adapter.getPosition(openHours[1]));
        TuesdayOpen.setSelection(adapter.getPosition(openHours[2]));
        WednesdayOpen.setSelection(adapter.getPosition(openHours[3]));
        ThursdayOpen.setSelection(adapter.getPosition(openHours[4]));
        FridayOpen.setSelection(adapter.getPosition(openHours[5]));
        SaturdayOpen.setSelection(adapter.getPosition(openHours[6]));

        SundayClose.setSelection(adapter.getPosition(closeHours[0]));
        MondayClose.setSelection(adapter.getPosition(closeHours[1]));
        TuesdayClose.setSelection(adapter.getPosition(closeHours[2]));
        WednesdayClose.setSelection(adapter.getPosition(closeHours[3]));
        ThursdayClose.setSelection(adapter.getPosition(closeHours[4]));
        FridayClose.setSelection(adapter.getPosition(closeHours[5]));
        SaturdayClose.setSelection(adapter.getPosition(closeHours[6]));

        final Button updateButton = (Button) dialogView.findViewById(R.id.WorkingHourUpdate);

        dialogBuilder.setTitle("Update Working Hours");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] newOpenHours = new String[] {
                        SundayOpen.getSelectedItem().toString(),
                        MondayOpen.getSelectedItem().toString(),
                        TuesdayOpen.getSelectedItem().toString(),
                        WednesdayOpen.getSelectedItem().toString(),
                        ThursdayOpen.getSelectedItem().toString(),
                        FridayOpen.getSelectedItem().toString(),
                        SaturdayOpen.getSelectedItem().toString()
                };
                final String[] newCloseHours = new String[] {
                        SundayClose.getSelectedItem().toString(),
                        MondayClose.getSelectedItem().toString(),
                        TuesdayClose.getSelectedItem().toString(),
                        WednesdayClose.getSelectedItem().toString(),
                        ThursdayClose.getSelectedItem().toString(),
                        FridayClose.getSelectedItem().toString(),
                        SaturdayClose.getSelectedItem().toString()
                };
                branchDatabase.updateWorkingHours(branchID, newOpenHours, newCloseHours);

                String[] openHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.openingHours).toString().split(",");
                String[] closeHours = branchDatabase.getDatabaseData(branchID, SQLiteBranchDatabase.DATABASE_DATA.closingHours).toString().split(",");

                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)%7;
                Date date = Calendar.getInstance().getTime();

                workingHours.setText("Working hours for " + new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()) + ": " + openHours[day] + " to " + closeHours[day]);

                b.dismiss();
            }
        });
    }

}