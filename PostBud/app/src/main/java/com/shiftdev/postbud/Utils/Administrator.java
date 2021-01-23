package com.shiftdev.postbud.Utils;

import android.content.Context;
import android.widget.Toast;

import timber.log.Timber;

public class Administrator extends Account {
    // Constant
    private final static String TAG = "Administrator";

    // Constructor
    public Administrator() {
    }

    public Administrator(String email, String password, String employeeId, String firstName, String lastName) {
        super(email, password, employeeId, firstName, lastName);
        super.setAccountType(AccountType.ADMINISTRATOR);
    }

    // Public Methods

    /**
     * Create a new employee, add him/her to the database, and return an Employee object.
     *
     * @param email      the email of the employee.
     * @param password   the password of the employee.
     * @param employeeId employeeId for the employee.
     * @param firstName  employee's first name.
     * @param lastName   employee's lastName.
     */
    public void newEmployee(Context context, String email, String password, String employeeId, String firstName, String lastName) {
        Employee newEmployee = new Employee(email, password, employeeId, firstName, lastName);
        Timber.plant(new Timber.DebugTree());             // TODO: Check if this line is needed.
        PostBudFirestoreUtils.createAccountAndUploadToFirestore(context, newEmployee)
                .addOnSuccessListener(result -> Toast.makeText(context, "Successfully created new employee account with email: " + email, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to create new employee account with the email: " + email, Toast.LENGTH_SHORT).show();
                    Timber.e(e.getLocalizedMessage());      // TODO: Ask Daniel to implement Timber properly.
                });

    }

    // Public methods
    @Override
    public String toString() {
        return super.toString();
    }
}
