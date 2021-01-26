package com.shiftdev.postbud.Utils;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Parcel> {

    /**
     * Comparing 2 parcel's priorities. Lowest number get the highest priority.
     *
     * @param parcel main parcel.
     * @param other  comparing parcel.
     * @return the parcels priority
     */
    @Override
    public int compare(Parcel parcel, Parcel other) {
        // 2-level comparison to compare same-priority levels parcels.
        if (parcel.getPriority() == other.getPriority()) {
            return parcel.getDateCreated().compareTo(other.getDateCreated());
        } else {
            return 0;
//            -(parcel.getPriority().getNumericValue() - other.getPriority().getNumericValue());
        }
    }
}
