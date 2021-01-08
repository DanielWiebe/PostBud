package com.shiftdev.postbud;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Objects;

public class Parcel implements Comparable<Parcel> {

     // Variables
     private String currentLocation;
     private String origin;
     private String destination;
     private String orderedBy;
     private String description;
     private String status;
     private String priority;
     private String handledBy;
     private double weight;
     private Date date;


     // Constructors

     /**
      * New Order made by an employee
      */
     public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, String priority, double weight, String accountId, Activity context) {
          this.currentLocation = currentLocation;
          this.origin = origin;
          this.destination = destination;
          this.orderedBy = orderedBy;
          this.description = description;
          this.status = String.valueOf(Status.PENDING);
          this.weight = weight;
          this.priority = priority;
          this.handledBy = accountId;
     }

     public Parcel() {
     }

     /**
      * New/existing order with a custom status
      */
     public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, String status, String priority, double weight) {
          this.currentLocation = currentLocation;
          this.origin = origin;
          this.destination = destination;
          this.orderedBy = orderedBy;
          this.description = description;
          this.status = status;
          this.weight = weight;
          this.priority = priority;
          this.handledBy = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();  // Getting the current user's UID to know who handled it.

     }

     // Getters & Setters
     public String getCurrentLocation() {
          return currentLocation;
     }

     public void setCurrentLocation(String currentLocation) {
          this.currentLocation = currentLocation;
     }

     public String getOrigin() {
          return origin;
     }

     public void setOrigin(String origin) {
          this.origin = origin;
     }

     public String getDestination() {
          return destination;
     }

     public void setDestination(String destination) {
          this.destination = destination;
     }

     public String getOrderedBy() {
          return orderedBy;
     }

     public void setOrderedBy(String orderedBy) {
          this.orderedBy = orderedBy;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public String getStatus() {
          return status;
     }

     public void setStatus(String status) {
          this.status = status;
     }

     public String getPriority() {
          return priority;
     }

     public void setPriority(String priority) {
          this.priority = priority;
     }

     public String getHandledBy() {
          return handledBy;
     }

     public void setHandledBy(String handledBy) {
          this.handledBy = handledBy;
     }

     public double getWeight() {
          return weight;
     }

     public void setWeight(double weight) {
          this.weight = weight;
     }

     public Date getDate() {
          return date;
     }

     public void setDate(Date date) {
          this.date = date;
     }

     @Override
     public int compareTo(Parcel parcel) {
          Log.e("ParcelJava", "compareTo called and we don't know what this does.");
          return 0;
     }


     // Enums

     /**
      * A list for Parcel to set from for the status of the parcel/delivery.
      */
     enum Status {
          AWAITING_PAYMENT(R.string.parcel_status_awaiting_payment),
          AWATING_PICKUP(R.string.parcel_status_awaiting_pickup),
          CANCELED(R.string.parcel_status_canceled),
          IN_TRANSIT(R.string.parcel_status_in_transit),
          PENDING(R.string.parcel_status_pending),
          SHIPPED(R.string.parcel_status_shipped);

          private final int statusValue;

          Status(int statusValue) {
               this.statusValue = statusValue;
          }

          public String getValue(Activity context) {
               return context.getResources().getString(statusValue);
          }
     }

     public enum code {
          HIGH, MEDIUM, LOW
     }

     /**
      * Priority strings and values for parcel/delivery.
      */
     enum Priority {
          //        URGENT("URGENT"),
          HIGH(R.string.parcel_priority_high),
          MEDIUM(R.string.parcel_priority_medium),
          LOW(R.string.parcel_priority_low);

          private final int priorityString;
          private final int priorityValue;

          Priority(int priorityString) {
               this.priorityString = priorityString;
               if (priorityString == R.string.parcel_priority_high) {
                    priorityValue = 1;
               } else if (priorityString == R.string.parcel_priority_medium) {
                    priorityValue = 2;
               } else {
                    priorityValue = 3;
               }
          }

          public String getValue(Activity context) {
               return context.getResources().getString(priorityString);
          }

          public int getNumericValue() {
               return priorityValue;
          }
     }
}
