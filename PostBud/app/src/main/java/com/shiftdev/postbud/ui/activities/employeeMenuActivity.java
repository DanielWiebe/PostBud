package com.shiftdev.postbud.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftdev.postbud.AddParcelActivity;
import com.shiftdev.postbud.MainActivity;
import com.shiftdev.postbud.ParcelListActivity;
import com.shiftdev.postbud.R;

public class employeeMenuActivity extends AppCompatActivity {
        private Button parcelManagementButton;
        private Button newParcelButton;
        private Button logOutButton;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the user interface layout(.xml) for this activity
            setContentView(R.layout.employee_menu);
            //The parcel management button
            parcelManagementButton = findViewById(R.id.parcelManagementButton);
            parcelManagementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(employeeMenuActivity.this, ParcelListActivity.class);
                    startActivity(intent);
                }
            });
            //The add new parcel button
            newParcelButton = findViewById(R.id.newParcelButton);
            newParcelButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(employeeMenuActivity.this, AddParcelActivity.class);
                    startActivity(intent);
                }
            });
            //The log out button
            logOutButton = findViewById(R.id.logOutButton);
            logOutButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    //Here, the login status should be switched to "logged out" or something like that

                    Intent intent = new Intent(employeeMenuActivity.this, MainActivity.class);
                    startActivity(intent);
                    employeeMenuActivity.this.finish();
                }
            });
        }
}