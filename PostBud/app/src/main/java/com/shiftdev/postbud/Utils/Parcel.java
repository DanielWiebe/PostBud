package com.shiftdev.postbud.Utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Parcel {
     // Constants
     private final String TAG = "Parcel";
     private final FirebaseFirestore db = FirebaseFirestore.getInstance();
     // Variables
     private String documentId;
     private String parcelId;
     private String currentLocation;
     private String origin;
     private String destination;
     private String orderedBy;
     private String description;
     private String status;
     private int priority;
     private String handledBy;
     private double weight;  // Kilograms
     private Timestamp date;

     // Constructors
     public Parcel() {
     }

     /* Parcel made with a custom uid. Made for Administrator use. */
     public Parcel(String parcelId, String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp date, int priority, String status, String accountUid) {
          this.parcelId = parcelId;
          this.currentLocation = currentLocation;
          this.origin = origin;
          this.destination = destination;
          this.orderedBy = orderedBy;
          this.description = description;
          this.weight = weight;
          this.date = date;
          this.status = status;
          this.priority = priority;
          this.handledBy = accountUid;
     }

     /* Parcel made by the current user with his/her uid. */
     public Parcel(String parcelId, String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp date, int priority, String status) {
          this.parcelId = parcelId;
          this.currentLocation = currentLocation;
          this.origin = origin;
          this.destination = destination;
          this.orderedBy = orderedBy;
          this.description = description;
          this.weight = weight;
          this.date = date;
          this.status = status;
          this.priority = priority;
          this.handledBy = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();  // Getting the current user's UID to know who handled it.
     }

     public String getDocumentId() {
          return documentId;
     }

     public void setDocumentId(String documentId) {
          this.documentId = documentId;
     }

     public String getParcelId() {
          return parcelId;
     }

     public void setParcelId(String parcelId) {
          this.parcelId = parcelId;
     }

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

     public int getPriority() {
          return priority;
     }

     public void setPriority(int priority) {
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

     public Timestamp getDate() {
          return date;
     }

     public void setDate(Timestamp date) {
          this.date = date;
     }

     // Getters & Setters


     // Enums
     /* A list for Parcel to set from for the status of the parcel/delivery. */
//    public enum Status {
//        PENDING(R.string.parcel_status_pending),
//        IN_TRANSIT(R.string.parcel_status_in_transit),
//        AWATING_PICKUP(R.string.parcel_status_awaiting_pickup),
//        SHIPPED(R.string.parcel_status_shipped),
//        AWAITING_PAYMENT(R.string.parcel_status_awaiting_payment),
//        CANCELED(R.string.parcel_status_in_canceled);
//
//        // Variables
//        private final int statusValue;
//
//        // Constructor
//        Status(int statusValue) { this.statusValue = statusValue; }
//
//        /* Returns the status string value */
//        public String getValue(Activity context) {
//            return context.getResources().getString(statusValue);
//        }
//    }
     // Enums
     /* A list for Parcel to set from for the status of the parcel/delivery. */
//     enum Status {
//          PENDING(R.string.parcel_status_pending),
//          IN_TRANSIT(R.string.parcel_status_in_transit),
//          AWATING_PICKUP(R.string.parcel_status_awaiting_pickup),
//          SHIPPED(R.string.parcel_status_shipped),
//          AWAITING_PAYMENT(R.string.parcel_status_awaiting_payment),
//          CANCELED(R.string.parcel_status_in_canceled);
//
//          // Variables
//          private final int statusValue;
//
//          // Constructor
//          Status(int statusValue) {
//               this.statusValue = statusValue;
//          }
//
//          /* Returns the status string value */
//          public String getValue(Activity context) {
//               return context.getResources().getString(statusValue);
//          }
//     }

     /* Priority strings and values for parcel/delivery. */
//     enum Priority {
//          HIGH(R.string.parcel_priority_high),
//          MEDIUM(R.string.parcel_priority_medium),
//          LOW(R.string.parcel_priority_low);
//
//          // Variables
//          private final int priorityString;
//          private final int priorityValue;
//
//          // Constructor
//          Priority(int priorityString) {
//               this.priorityString = priorityString;
//
//               // Setting priority numeric value automatically based on the priority value (priorityString).
//               if (priorityString == R.string.parcel_priority_high) {
//                    priorityValue = 1;
//               } else if (priorityString == R.string.parcel_priority_medium) {
//                    priorityValue = 2;
//               } else {
//                    priorityValue = 3;
//               }
//          }
//
//          /* Returns priority string value */
//          public String getValue(Activity context) {
//               return context.getResources().getString(priorityString);
//          }
//
//          /* Returns priority numeric value. Used in PriorityComparator class or to compare the priority. */
//          public int getNumericValue() {
//               return priorityValue;
//          }
//     }
}
