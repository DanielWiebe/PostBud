package com.shiftdev.postbud.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftdev.postbud.AddParcelActivity;
import com.shiftdev.postbud.MainActivity;
import com.shiftdev.postbud.R;

public class administratorMenuActivity extends AppCompatActivity {
    private Button employeeManagement;
    private Button parcelManagement;
    private Button newParcel;
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_menu);
        //linking up the buttons
        employeeManagement = findViewById(R.id.addEmployee);
        parcelManagement = findViewById(R.id.parcelManagementButton);
        newParcel = findViewById(R.id.newParcelButton);
        logOut= findViewById(R.id.logOutButton);
        //The button actions
        employeeManagement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(administratorMenuActivity.this, addEmployeeActivity.class);
                startActivity(intent);
            }
        });
        parcelManagement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(administratorMenuActivity.this, parcelManagementActivity.class);
                startActivity(intent);
            }
        });
        newParcel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(administratorMenuActivity.this, AddParcelActivity.class);
                startActivity(intent);
            }
        });
        //The log out button
        logOut = findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //Here, the login status should be switched to "logged out" or something like that

                Intent intent = new Intent(administratorMenuActivity.this, MainActivity.class);
                startActivity(intent);
                administratorMenuActivity.this.finish();
            }
        });
    }
}
