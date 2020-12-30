package com.shiftdev.postbud.ui.parcel_overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shiftdev.postbud.R;

public class ParcelOverviewFragment extends Fragment {

     Query query = FirebaseFirestore.getInstance()
             .collection("parcels")
             .orderBy("timestamp")
             .limit(50);
     private ParcelOverviewViewModel parcelOverviewViewModel;

     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
          parcelOverviewViewModel =
                  new ViewModelProvider(this).get(ParcelOverviewViewModel.class);
          View root = inflater.inflate(R.layout.fragment_parcel_overview, container, false);
          return root;
     }
}