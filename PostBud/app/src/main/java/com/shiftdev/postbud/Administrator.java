package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Administrator extends Account {
    // Constants
    final static String TAG = "Administrator";
    final static String ADMINISTRATORS = "administrators";
    final static String DOCUMENT_ID = "documentId";

    // Variables
    private String documentId;

    // Constructor
    public Administrator() {};

    public Administrator(String email, String password, String firstName, String lastName, Activity context) {
        super(email, password, firstName, lastName);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.toLowerCase(), password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            super.setUid(user.getUid());
                        } else {
                            mAuth.signOut();
                            //TODO Handle with Toast on the UI
                            Log.e(TAG, "User is NULL");
                            return;
                        }
                        Log.i(TAG, "Account successfully created with email: " + email + ", and password: " + password);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(ADMINISTRATORS).add(this)
                            .addOnSuccessListener(documentReference -> {
                                Log.d(TAG, documentReference.getId());
                                setDocumentId(documentReference.getId());
                                db.collection(ADMINISTRATORS).document(getDocumentId()).update(DOCUMENT_ID, getDocumentId());
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
