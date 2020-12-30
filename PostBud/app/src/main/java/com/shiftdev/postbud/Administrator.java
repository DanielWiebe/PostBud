package com.shiftdev.postbud;

import android.app.Activity;

public class Administrator extends Account {
    // Variables
    private String userName;

    // Constructor
    public Administrator(String email, String userName, String password, Activity conext) {
        super(email, password, conext);
        this.userName = userName;
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
