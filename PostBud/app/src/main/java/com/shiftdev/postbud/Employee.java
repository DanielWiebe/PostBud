package com.shiftdev.postbud;

public class Employee extends Account{
    // Variables
    private String employeeId;

    // Constructor
    public Employee(String employeeId, String userName, String password) {
        super(userName, password);
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    // Public methods

}
