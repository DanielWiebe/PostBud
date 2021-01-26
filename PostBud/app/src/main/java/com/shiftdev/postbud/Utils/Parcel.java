package com.shiftdev.postbud.Utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class Parcel {
    // Constants
    private final String TAG = "Parcel";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
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
    private Timestamp dateCreated;

    // Constructors
    public Parcel() {
    }

    /* Parcel made with a custom uid. Made for Administrator use. */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp dateCreated, int priority, String status, String accountUid) {
        this.parcelId = parcelId;
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.weight = weight;
        this.dateCreated = dateCreated;
        this.status = status;
        this.priority = priority;
        this.handledBy = accountUid;
    }

    /* Parcel made by the current user with his/her uid. */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, double weight, Timestamp dateCreated, int priority, String status) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.weight = weight;
        this.dateCreated = dateCreated;
        this.status = status;
        this.priority = priority;
        this.handledBy = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();  // Getting the current user's UID to know who handled it.
    }

    // Getters & Setters
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

    public Timestamp getDateCreated() {
        return dateCreated;
    }
     public void setDateCreated(Timestamp dateCreated) {
          this.dateCreated = dateCreated;
     }



    // Public Methods
    @Override
    public String toString() {
        return "Parcel{" +
                "parcelId='" + parcelId + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", orderedBy='" + orderedBy + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", handledBy='" + handledBy + '\'' +
                ", weight=" + weight +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
