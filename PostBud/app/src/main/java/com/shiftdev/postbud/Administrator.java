package com.shiftdev.postbud;

public class Administrator extends Account {
    // Constructor
    public Administrator(String userName, String password) {
        super(userName, password);
    }

    // Public Methods
    /**
     * Create a new employee, add him/her to the database, and return its details.
     * @param employeeId Employee's ID.
     * @param userName Employee's user name.
     * @param password Employee's password.
     * @return return the new created employee.
     */
    public Employee newEmployee(String employeeId, String userName, String password) {
        Employee newEmployee = newEmployee(employeeId, userName, password);
        // TODO: Add new employee to the DB.
        return newEmployee;
    }
}
