package com.shiftdev.postbud;

import android.app.Activity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Parcel {
    // Constants
    private final String TAG = "Parcel";

    // Variables
    private String currentLocation;
    private String origin;
    private String destination;
    private String orderedBy;
    private String description;
    private Status status;
    private Priority priority;
    private String handledBy;
    private double weight;  // Kilograms
    private Timestamp date;

    // Constructors
    /* Parcel made by an Administrator */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp date, Priority priority, Parcel.Status status, String accountId) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.weight = weight;
        this.date = date;
        this.status = status;
        this.priority = priority;
        this.handledBy = accountId;
        uploadToFirestore(this);
    }

    /* Parcel made by an Employee */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp date, Priority priority, Parcel.Status status) {
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
        uploadToFirestore(this);
    }

    // Private Methods
    private void uploadToFirestore(Parcel parcel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirebaseNav.PARCELS.getValue()).add(parcel);
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
    /* A list for Parcel to set from for the status of the parcel/delivery. */
    enum Status {
        PENDING(R.string.parcel_status_pending),
        IN_TRANSIT(R.string.parcel_status_in_transit),
        AWATING_PICKUP(R.string.parcel_status_awaiting_pickup),
        SHIPPED(R.string.parcel_status_shipped),
        AWAITING_PAYMENT(R.string.parcel_status_awaiting_payment),
        CANCELED(R.string.parcel_status_in_canceled);

        // Variables
        private final int statusValue;

        // Constructor
        Status(int statusValue) { this.statusValue = statusValue; }

        /* Returns the status string value */
        public String getValue(Activity context) {
            return context.getResources().getString(statusValue);
        }
    }

    /* Priority strings and values for parcel/delivery. */
    enum Priority {
        HIGH(R.string.parcel_priority_high),
        MEDIUM(R.string.parcel_priority_medium),
        LOW(R.string.parcel_priority_low);

        // Variables
        private final int priorityString;
        private final int priorityValue;

        // Constructor
        Priority(int priorityString) {
            this.priorityString = priorityString;

            // Setting priority numeric value automatically based on the priority value (priorityString).
            if (priorityString == R.string.parcel_priority_high) {
                priorityValue = 1;
            } else if (priorityString == R.string.parcel_priority_medium) {
                priorityValue = 2;
            } else {
                priorityValue = 3;
            }
        }

        /* Returns priority string value */
        public String getValue(Activity context) {
            return context.getResources().getString(priorityString);
        }

        /* Returns priority numeric value. Used in PriorityComparator class or to compare the priority. */
        public int getNumericValue() { return priorityValue; }
    }
}
