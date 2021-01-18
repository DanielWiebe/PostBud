package com.shiftdev.postbud.Utils;

import android.app.Activity;

import com.shiftdev.postbud.R;

/**
 * Firebase navigation through
 */
public enum FirebaseNav {
     // Collections
     ADMINISTRATORS(R.string.firebase_navigation_administrators),
     PARCELS(R.string.firebase_navigation_parcels),
     EMPLOYEES(R.string.firebase_navigation_employees),

     // Parcel fields
     DOCUMENT_ID(R.string.firebase_navigation_document_id),
     PARCEL_ID(R.string.firebase_navigation_parcel_id),
     CURRENT_LOCATION(R.string.firebase_navigation_current_location),
     ORIGIN(R.string.firebase_navigation_origin),
     DESTINATION(R.string.firebase_navigation_destination),
     ORDERED_BY(R.string.firebase_navigation_ordered_by),
     DESCRIPTION(R.string.firebase_navigation_description),
     STATUS(R.string.firebase_navigation_status),
     PRIORITY(R.string.firebase_navigation_priority),
     HANDLED_BY(R.string.firebase_navigation_handled_by),
     WEIGHT(R.string.firebase_navigation_weight),
     DATE(R.string.firebase_navigation_date),

     // Account fields
     UID(R.string.firebase_navigation_uid),
     EMAIL(R.string.firebase_navigation_email);

     private final int directory;

     FirebaseNav(int directory) {
          this.directory = directory;
     }

     public String getValue(Activity context) {
          return context.getResources().getString(directory);
     }
}
