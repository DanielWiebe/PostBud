package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Administrator extends Account {
    // Constant
    private final static String TAG = "Administrator";

    // Constructor
    public Administrator() {}

    public Administrator(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
        createAdministratorInFirebaseAuth(this);
    }

    public Administrator(String uid) {

    }

    /**
     * Registering an administrator use in FirebaseAuth as a user, and adding the user information into the Firebase database for future reference and data storage.
     *
     * @param administrator the registering Administrator user.
     */
    public void createAdministratorInFirebaseAuth(Administrator administrator) {
        Activity context = PostBudAppContext.getActivity();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(administrator.getEmail().toLowerCase(), administrator.getPassword())
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        if (user != null) {
                            administrator.setUid(user.getUid());
                        } else {
                            mAuth.signOut();
                            Log.e(TAG, "User is NULL");
                            return;
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        /** Uploading the administrator account to the database. */
                        db.collection(FirebaseNav.ADMINISTRATORS.getValue()).add(administrator)
                                .addOnSuccessListener(documentReference -> {
                                    administrator.setDocumentId(documentReference.getId());

                                    /** After the database creates the document ID, updating the document's field
                                     * "documentId" for future reference of the document from the application. */
                                    db.collection(FirebaseNav.ADMINISTRATORS.getValue())
                                            .document(administrator.getDocumentId())
                                            .update(FirebaseNav.DOCUMENT_ID.getValue(), administrator.getDocumentId());
                                });
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getLocalizedMessage()));
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
