package com.shiftdev.postbud.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shiftdev.postbud.AddParcelActivity;
import com.shiftdev.postbud.MainActivity;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.Administrator;
import com.shiftdev.postbud.Utils.FirebaseNav;
import com.shiftdev.postbud.Utils.PostBudFirestoreUtils;

import java.util.Map;

import timber.log.Timber;

public class administratorMenuActivity extends AppCompatActivity {
    private Context context;
    private Administrator admin;
    private Button employeeManagement;
    private Button parcelManagement;
    private Button newParcel;
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_menu);
        context = this;
        //linking up the buttons
        employeeManagement = findViewById(R.id.addEmployee);
        parcelManagement = findViewById(R.id.parcelManagementButton);
        newParcel = findViewById(R.id.newParcelButton);
        logOut= findViewById(R.id.logOutButton);
        // Fetching admin from database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        PostBudFirestoreUtils.getAdministrator(context, user.getUid())
                .addOnCompleteListener(administratorQuery -> {
                    if (administratorQuery.isSuccessful()) {
                        admin = administratorQuery.getResult().toObject(Administrator.class);
                        Map<String, Object> parcelMap = administratorQuery.getResult().getData();    // TODO: Delete credentials after testing.
                        Timber.e(parcelMap.toString());
                    }
                });

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
