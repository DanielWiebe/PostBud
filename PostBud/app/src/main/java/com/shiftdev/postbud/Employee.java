package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Employee extends Account {
    // Constants
    private final String TAG = "Employee";

    // Variables
    private String employeeId;

    // Constructors
    public Employee() {super();}

    public Employee(String email, String password, String employeeId, String firstName, String lastName) {
        super(email, password, firstName, lastName);
        this.employeeId = employeeId;
        createEmployeeUser(this);
    }

    public Employee(String uid) {

    }

    // Public methods

    @Override
    public String toString() {
        return "Employee{" +
                "TAG='" + TAG + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }

    // Private Methods
    /**
     * Registering an employee use in FirebaseAuth as a user, and adding the user information into the Firebase database for future reference and data storage.
     *
     * @param employee the registering Employee user.
     */
    private void createEmployeeUser(Employee employee) {
        Activity context = PostBudAppContext.getActivity();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(employee.getEmail().toLowerCase(), employee.getPassword())
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        if (user != null) {
                            employee.setUid(user.getUid());
                        } else {
                            mAuth.signOut();
                            Log.e(TAG, "User is NULL");
                            return;
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        /* Uploading the administrator account to the database. */
                        db.collection(FirebaseNav.EMPLOYEES.getValue()).add(employee)
                                .addOnSuccessListener(documentReference -> {
                                    employee.setDocumentId(documentReference.getId());

                                    /* After the database creates the document ID, updating the document's field "documentId" for future reference of the document from the application. */
                                    db.collection(FirebaseNav.EMPLOYEES.getValue())
                                            .document(employee.getDocumentId())
                                            .update(FirebaseNav.DOCUMENT_ID.getValue(), employee.getDocumentId());
                                });
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getLocalizedMessage()));
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
