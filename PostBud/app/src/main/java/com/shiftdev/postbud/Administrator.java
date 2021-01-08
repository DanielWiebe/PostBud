package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Administrator extends Account {
    // Constant
    private final static String TAG = "Administrator";

    // Constructor
    public Administrator() {}

    public Administrator(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
        createAdministratorInFirebaseAuth(this);
    }

    // Private methods
    /**
     * Registering an administrator use in FirebaseAuth as a user, and adding the user information into the Firebase database for future reference and data storage.
     *
     * @param administrator the registering Administrator user.
     */
    private void createAdministratorInFirebaseAuth(Administrator administrator) {
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
                            //TODO Handle with Toast on the UI
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
    // TODO Read from Firestore into a new Administrator object.
//    public Administrator readFromDatabase() {
//        return null;
//    }
//
//    public Administrator(String uid) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection(FirebaseNav.ADMINISTRATORS.getValue()).whereEqualTo(FirebaseNav.UID.getValue(), uid)
//                .get().addOnSuccessListener(queryDocumentSnapshots -> {
//                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
//                        System.out.println(snapshot);
//                    }
//        });
//
//    }

    /**
     * Create a new employee, add him/her to the database, and return an Employee object.
     *
     * @param email the email of the employee.
     * @param password the password of the employee.
     * @param employeeId employeeId for the employee.
     * @param firstName employee's first name.
     * @param lastName employee's lastName.
     * @return the Employee object.
     */
    public Employee newEmployee(String email, String password, String employeeId, String firstName, String lastName) {
        Employee newEmployee = new Employee(email, password, employeeId, firstName, lastName);
        return newEmployee;
    }


}
