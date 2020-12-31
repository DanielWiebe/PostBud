package com.shiftdev.postbud;

public class Employee extends Account {
    // Variables
    private String employeeId;

    // Constructors
    public Employee() {super();}

    public Employee(String email, String password, String employeeId, String firstName, String lastName) {
        super(email, password, firstName, lastName);
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
