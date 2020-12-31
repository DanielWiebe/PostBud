package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Administrator extends Account {
    final static String TAG = "Administrator";
    final static String ADMINISTRATORS = "administrators";

    private String documentId;

    // Constructor
    public Administrator(String email, String password, String firstName, String lastName, Activity context) {
        super(email, password, firstName, lastName);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.toLowerCase(), password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        super.setUid(user.getUid());
                        Log.i(TAG, "Account successfully created with email: " + email + ", and password: " + password);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(ADMINISTRATORS).add(this)
                            .addOnSuccessListener(documentReference -> {
                                Log.d(TAG, documentReference.getId());
                                setDocumentId(documentReference.getId());
                                db.collection(ADMINISTRATORS).document(getDocumentId()).update("documentId", getDocumentId());
                            });
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getLocalizedMessage()));
    }

    // Getters & Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    // Public Methods

    /**
     * Create a new employee, add him/her to the database, and return its details.
     *
     * @param employeeId Employee's ID.
     * @param userName   Employee's user name.
     * @param password   Employee's password.
     * @return return the new created employee.
     */
//    public Employee newEmployee(String employeeId, String userName, String password) {
//        Employee newEmployee = newEmployee(employeeId, userName, password);
//        // TODO: Add new employee to the DB.
//        return newEmployee;
//    }
}
