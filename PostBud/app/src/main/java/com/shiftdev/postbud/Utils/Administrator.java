package com.shiftdev.postbud.Utils;

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
      * @return the Employee object.
      */
     public Employee newEmployee(String email, String password, String employeeId, String firstName, String lastName) { // TODO New employee by an Administrator
          Employee newEmployee = new Employee(email, password, employeeId, firstName, lastName);
          return newEmployee;
     }

     // Public methods
     @Override
     public String toString() {
          return super.toString();
     }
}
