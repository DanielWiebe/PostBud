package com.shiftdev.postbud;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Parcel> {

    @Override
    public int compare(Parcel parcel, Parcel other) {
        if (parcel.getPriority() == other.getPriority()) {
            return parcel.getDate().compareTo(other.getDate());
        } else {
            return -(parcel.getPriority().getNumericValue() - other.getPriority().getNumericValue());
        }
    }
}
