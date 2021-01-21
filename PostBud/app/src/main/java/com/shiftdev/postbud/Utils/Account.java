package com.shiftdev.postbud.Utils;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public abstract class Account implements Comparable<Account>, Serializable {
     // Constants
     final static String TAG = "Account";

     // Variables
     private String uid;                     // User ID created by Firebase Authentication.
     private String employeeId;
     private String email;
     private String password;
     private String firstName;
     private String lastName;
     private Timestamp dateCreated;
     private AccountType accountType;

     // Constructors
     public Account() {
     }

     public Account(String email, String password, String employeeId, String firstName, String lastName) {
          this.email = email;
          this.password = password;
          this.firstName = firstName;
          this.lastName = lastName;
          this.employeeId = employeeId;
          dateCreated = Timestamp.now();
     }

     // Public methods
     public Parcel registerNewParcelByAdminis(Context context, String parcelId, String currentLocation, String origin, String destination, String orderedBy,
                                              String description, double weight, Timestamp date, int priority, String status) {
          Parcel parcel = new Parcel(parcelId, currentLocation, origin, destination, orderedBy, description, weight, date, priority, status);
          PostBudFirestoreUtils.uploadParcel(context, parcel);
          return parcel;
     }

     public Parcel newParcel(Context context, String parcelId, String currentLocation, String origin, String destination, String orderedBy,
                             String description, double weight, Timestamp date, int priority, String status, String uid) {
          Parcel parcel = new Parcel(parcelId, currentLocation, origin, destination, orderedBy, description, weight, date, priority, status, uid);
          PostBudFirestoreUtils.uploadParcel(context, parcel);
          return parcel;
     }

     // Comparable implementation
     @Override
     public int compareTo(Account account) {
          return this.email.compareTo(account.email);
     }

     // Getters & Setters
     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getUid() {
          return uid;
     }

     public void setUid(String accountId) {
          this.uid = accountId;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getFirstName() {
          return firstName;
     }

     public void setFirstName(String firstName) {
          this.firstName = firstName;
     }

     public String getLastName() {
          return lastName;
     }

     public void setLastName(String lastName) {
          this.lastName = lastName;
     }

     public Timestamp getDateCreated() {
          return dateCreated;
     }

     public void setDateCreated(Timestamp dateCreated) {
          this.dateCreated = dateCreated;
     }

     public AccountType getAccountType() {
          return accountType;
     }

     public void setAccountType(AccountType accountType) {
          this.accountType = accountType;
     }

     public String getEmployeeId() {
          return employeeId;
     }

     public void setEmployeeId(String employeeId) {
          this.employeeId = employeeId;
     }

     public enum AccountType {
          ADMINISTRATOR,
          EMPLOYEE
     }
}

