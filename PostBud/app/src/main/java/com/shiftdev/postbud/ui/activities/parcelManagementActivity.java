package com.shiftdev.postbud.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftdev.postbud.ParcelListActivity;
import com.shiftdev.postbud.R;

public class parcelManagementActivity extends AppCompatActivity {
    private Button editParcelButton;
    private Button viewParcelsButton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the user interface layout(.xml) for this activity
        setContentView(R.layout.parcel_management);
        //Edit a parcel (button)
        editParcelButton = findViewById(R.id.editParcelButton);
        editParcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(parcelManagementActivity.this, ParcelListActivity.class);
                startActivity(intent);
            }
        });
        //The view parcels button
        viewParcelsButton = findViewById(R.id.viewParcelsButton);
        viewParcelsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(parcelManagementActivity.this, ParcelListActivity.class);
                startActivity(intent);
            }
        });

    }
}
