package com.shiftdev.postbud.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftdev.postbud.R;

public class parcelStatusActivity extends AppCompatActivity {
    private TextView trackingNumber;
    private TextView currentStatus;
    private TextView destination;
    private TextView origin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the user interface layout(.xml) for this activity
        setContentView(R.layout.parcel_status);
        //The parcel management button
        trackingNumber = findViewById(R.id.trackingNumberText);
        currentStatus = findViewById(R.id.currentStatusText);
        destination = findViewById(R.id.destination);
        origin = findViewById(R.id.origin);

    }
}
