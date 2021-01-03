package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Administrator extends Account {
    // Constant
    final static String TAG = "Administrator";

    // Variables
    private String documentId;

    // Constructor
    public Administrator() {}

    public Administrator(String email, String password, String firstName, String lastName, Activity context) {
        super(email, password, firstName, lastName);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.toLowerCase(), password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            super.setUid(user.getUid());
                        } else {
                            mAuth.signOut();
                            Log.e(TAG, "User is NULL");
                            return;
                        }
                        Log.i(TAG, "Account successfully created with email: " + email + ", and password: " + password);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        /** Uploading the administrator account to the database. */
                        db.collection(FirebaseNav.ADMINISTRATORS.getValue(context)).add(this)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d(TAG, documentReference.getId());
                                    setDocumentId(documentReference.getId());

                                    /** After the database creates the document ID, updating the document's field
                                     * "documentId" for future reference of the document from the application. */
                                    db.collection(FirebaseNav.ADMINISTRATORS.getValue(context))
                                            .document(getDocumentId())
                                            .update(FirebaseNav.DOCUMENT_ID.getValue(context), getDocumentId());
                                });
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getLocalizedMessage()));
    }

    public Administrator(String uid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

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
