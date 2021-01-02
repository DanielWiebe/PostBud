package com.shiftdev.postbud;

public class Parcel {
    // Variables
    private String currentLocation;
    private String origin;
    private String destination;
    private String orderedBy;
    private String description;
    private Status status;
    private int priority;
    private String handledBy;

    // Constructors
    /** New Order made by an employee */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, int priority, String accountId) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.status = Status.PENDING;
        this.priority = priority;
        this.handledBy = accountId; // TODO: Singleton account  + get current employee / set employee
    }

    /** New/existing order with a custom status */
    public Parcel(String currentLocation, String origin, String destination, String orderedBy, String description, Status status, int priority, String accountId) {
        this.currentLocation = currentLocation;
        this.origin = origin;
        this.destination = destination;
        this.orderedBy = orderedBy;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.handledBy = accountId; // TODO: Singleton account + get current user / set employee
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

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public String getHandledBy() { return handledBy; }

    public void setHandledBy(String handledBy) { this.handledBy = handledBy; } // TODO: Get the referenced account

    // Enums

    /** A list for Parcel to set from for the status of the parcel/delivery. */
    enum Status {
        PENDING(String.valueOf(R.string.parcel_status_pending)),
        IN_TRANSIT(String.valueOf(R.string.parcel_status_in_transit)),
        AWATING_PICKUP(String.valueOf(R.string.parcel_status_awaiting_pickup)),
        SHIPPED(String.valueOf(R.string.parcel_status_shipped)),
        AWAITING_PAYMENT(String.valueOf(R.string.parcel_status_awaiting_payment)),
        CANCELED(String.valueOf(R.string.parcel_status_in_canceled));

        private final String statusString;

        Status(String statusString) { this.statusString = statusString; }

        public String getStatusString() { return statusString; }
    }
}
