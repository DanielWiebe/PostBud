package com.shiftdev.postbud.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftdev.postbud.R;

public class addEmployeeActivity extends AppCompatActivity {
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText addressField;
    private Button addButton;

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_employee);
        //linking up the buttons
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        emailField = findViewById(R.id.emailField);
        addressField = findViewById(R.id.addressField);
        addButton = findViewById(R.id.addButton);


        //The button action
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                firstName = firstNameField.getText().toString();
                lastName = lastNameField.getText().toString();
                email = emailField.getText().toString();
                address = addressField.getText().toString();
                //input checking is yet to be implemented

                //Requires implementation: if all fields are filled correctly, the empty "resultText" should let the user know that the new employee has been added, the text should be green.
            }
        });
    }
}
