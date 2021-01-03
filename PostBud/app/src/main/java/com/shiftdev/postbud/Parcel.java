package com.shiftdev.postbud;

import android.app.Activity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;

import java.util.Objects;

public class Parcel {

    // Variables
    private String currentLocation;
    private String origin;
    private String destination;
    private String orderedBy;
    private String description;
    private Status status;
    private Priority priority;
    private String handledBy;
    private double weight;
    private Timestamp date;


    // Constructors

    /** New Order made by an employee */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, Priority priority, String accountId, Activity context) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.status = Status.PENDING;
        this.priority = priority;
        this.handledBy = accountId;
    }

    /** New/existing order with a custom status */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, Status status, Priority priority) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.handledBy = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();  // Getting the current user's UID to know who handled it.

    }

    // Getters & Setters
    public String getCurrentLocation() { return currentLocation; }

    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }

    public void setDestination(String destination) { this.destination = destination; }

    public String getOrderedBy() { return orderedBy; }

    public void setOrderedBy(String orderedBy) { this.orderedBy = orderedBy; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public Priority getPriority() { return priority; }

    public void setPriority(Priority priority) { this.priority = priority; }

    public String getHandledBy() { return handledBy; }

    public void setHandledBy(String handledBy) { this.handledBy = handledBy; }

    public double getWeight() { return weight; }

    public void setWeight(double weight) { this.weight = weight; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }

    // Enums
    /** A list for Parcel to set from for the status of the parcel/delivery. */
    enum Status {
        PENDING(R.string.parcel_status_pending),
        IN_TRANSIT(R.string.parcel_status_in_transit),
        AWATING_PICKUP(R.string.parcel_status_awaiting_pickup),
        SHIPPED(R.string.parcel_status_shipped),
        AWAITING_PAYMENT(R.string.parcel_status_awaiting_payment),
        CANCELED(R.string.parcel_status_in_canceled);

        private final int statusValue;

        Status(int statusValue) { this.statusValue = statusValue; }

        public String getValue(Activity context) {
            return context.getResources().getString(statusValue);
        }
    }

    /** Priority strings and values for parcel/delivery. */
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

        public int getNumericValue() { return priorityValue; }
    }
}
