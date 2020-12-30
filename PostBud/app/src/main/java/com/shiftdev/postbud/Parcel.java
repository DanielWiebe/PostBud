package com.shiftdev.postbud;

public class Parcel {
    // Variables
    private String currentLocation;
    private String orign;
    private String destination;
    private String orderedBy;
    private String description;
    private Status status;
    private int priority;
    private Employee handledBy;

    // Statuses of the parcel
    enum Status {
        IN_TRANSIT(String.valueOf(R.string.parcel_status_in_transit));


        private String statusString;

        Status(String statusString){
            this.statusString = statusString;
        }
    }


}
