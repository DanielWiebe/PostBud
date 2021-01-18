package com.shiftdev.postbud.ui.activities;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import com.shiftdev.postbud.R;

public class LoginActivity extends AppCompatActivity {
    private String userName;
    private String userPassword;
    private Button logInButton;
    private CheckBox staySignedInCheckBox;
    private boolean staySignedInBoolean;
    private TextView loginErrorTextView;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //set the user interface layout(.xml) for this activity
        setContentView(R.layout.login_page);
        staySignedInCheckBox = findViewById(R.id.staySignedInCheckBox);
        logInButton = findViewById(R.id.logInButton);
        loginErrorTextView = findViewById(R.id.loginErrorText);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*layoutPages must be loaded depending on whether login and password is correct and whether the user is an admin or just a regular employee*/
                //The if/else statements conditions below are just a sketch of the implementation of the login process
               // if(/*login==success && userNameStatus == administrator*/){
//                        Intent intent = new Intent(LoginActivity.this, administratorMenuActivity.class);
//                        startActivity(intent);
                            //this "if" clause is for checking if the user wants to stay signed in or not. the "then" clause is just in pseudocode for now
                            //if(staySignedInCheckBox.isChecked()==true) //some staySignedIn boolean
                //}
//                else if (/*login==success && userNameStatus == employee*/){
                    Intent intent = new Intent(LoginActivity.this, employeeMenuActivity.class);
                    startActivity(intent);
                //this "if" clause is for checking if the user wants to stay signed in or not. the "then" clause is just in pseudocode for now
                //if(staySignedInCheckBox.isChecked()==true) //some staySignedIn boolean
//                }
                //This message should if the user's credentials
                //else loginErrorTextView.setText("Incorrect username or/and password");
            }
        });
    }
//    public void onStart(){
//        super.onStart();
//
//    }

}
