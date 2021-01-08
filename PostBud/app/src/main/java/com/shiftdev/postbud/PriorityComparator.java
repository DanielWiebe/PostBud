package com.shiftdev.postbud;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Parcel> {

    @Override
    public int compare(Parcel parcel, Parcel other) {
        if (parcel.getPriority().equals(other.getPriority())) {
            return parcel.getDate().compareTo(other.getDate());
        }
        if (parcel == null) {
            return -1;
        }
        if (other == null) {
            return 1;
        } else {
            return parcel.compareTo(other);
        }
    }
}
