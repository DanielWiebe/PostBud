package com.shiftdev.postbud.Utils;

public class Employee extends Account {
     // Constants
     private final String TAG = "Employee";

     // Constructors
     public Employee() {
     }

     public Employee(String email, String password, String employeeId, String firstName, String lastName) {
          super(email, password, employeeId, firstName, lastName);
          super.setAccountType(AccountType.EMPLOYEE);
     }

     @Override
     public String toString() {
          return "Employee{" +
                  "TAG='" + TAG + '\'' +
                  '}';
     }
}
