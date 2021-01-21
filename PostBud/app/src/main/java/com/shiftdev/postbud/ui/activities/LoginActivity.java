package com.shiftdev.postbud.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.FirebaseNav;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private boolean isAdministrator = false;
    private boolean isUserNotInDatabase = false;
    private String uid;
    private String userName;
    private String userPassword;
    private Button logInButton;
    private CheckBox staySignedInCheckBox;
    private boolean staySignedInBoolean;
    private TextView loginErrorTextView;
    private EditText loginUserName;
    private EditText loginPassword;
    private Intent intent;
    // Testing purpose
    /* Employee */
    String emailEmployee = "artEmployee@gmail.com";
    String passwordEmployee = "password2";

    private void TestLoginEmployee() {
        this.userName = emailEmployee;
        this.userPassword = passwordEmployee;
    }

    /* Admin */
    String emailAdmin = "artAdmin@gmail.com";
    String passwordAdmin = "password1";

    private void TestLoginAdmin() {
        this.userName = emailAdmin;
        this.userPassword = passwordAdmin;
    }
    /*-----------------*/

    private Task<DocumentSnapshot> checkIsUserEmployee(FirebaseFirestore db) {
        return db.collection(FirebaseNav.EMPLOYEES.getValue(context))
                .document(uid)
                .get();
    }

    private Task<DocumentSnapshot> checkIsUserAdministrator(FirebaseFirestore db) {
        return db.collection(FirebaseNav.ADMINISTRATORS.getValue(context))
                .document(uid)
                .get();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Local Variables
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //set the user interface layout(.xml) for this activity

        setContentView(R.layout.login_page);
        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);
        staySignedInCheckBox = findViewById(R.id.staySignedInCheckBox);
        logInButton = findViewById(R.id.logInButton);
        loginErrorTextView = findViewById(R.id.loginErrorText);
        Timber.plant(new Timber.DebugTree());
        logInButton.setOnClickListener(arg0 -> {
            userName = loginUserName.getText().toString().trim().toLowerCase();
            userPassword = loginPassword.getText().toString();
            context = this;
//            TestLoginEmployee();
            TestLoginAdmin();
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Successfully logged into " + userName, Toast.LENGTH_LONG).show();
                            uid = task.getResult().getUser().getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            checkIsUserEmployee(db).addOnCompleteListener(employeeQuery -> {
                                if (employeeQuery.getResult().exists()) {
                                    Timber.e("Successfully loggedin as Employee");
                                    intent = new Intent(LoginActivity.this, employeeMenuActivity.class);
                                    startActivity(intent);
                                } else {
                                    checkIsUserAdministrator(db).addOnCompleteListener(administratorQuery -> {
                                        if (administratorQuery.getResult().exists()) {
                                            Timber.e("Successfully loggedin as Administrator");
                                            isAdministrator = true;
                                            intent = new Intent(LoginActivity.this, administratorMenuActivity.class);
                                            startActivity(intent);
                                        } else {
                                            isUserNotInDatabase = true;
                                            Timber.e("User NOT FOUND");
                                            Toast.makeText(context, "Your problem is NOT our problem, get some real credentials, mate. (NO USER IN DB)", Toast.LENGTH_LONG);
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(getBaseContext(), "Failed to login: wrong password/email", Toast.LENGTH_LONG).show();
                        }
                    });

            /*layoutPages must be loaded depending on whether login and password is correct and whether the user is an admin or just a regular employee*/
            //The if/else statements conditions below are just a sketch of the implementation of the login process
            // if(/*login==success && userNameStatus == administrator*/){
//                        Intent intent = new Intent(LoginActivity.this, administratorMenuActivity.class);
//                        startActivity(intent);
            //this "if" clause is for checking if the user wants to stay signed in or not. the "then" clause is just in pseudocode for now
            //if(staySignedInCheckBox.isChecked()==true) //some staySignedIn boolean
            //}
//                else if (/*login==success && userNameStatus == employee*/){

            //this "if" clause is for checking if the user wants to stay signed in or not. the "then" clause is just in pseudocode for now
            //if(staySignedInCheckBox.isChecked()==true) //some staySignedIn boolean
//                }
            //This message should if the user's credentials
            //else loginErrorTextView.setText("Incorrect username or/and password");
        });
    }
//    public void onStart(){
//        super.onStart();
//
//    }

}
