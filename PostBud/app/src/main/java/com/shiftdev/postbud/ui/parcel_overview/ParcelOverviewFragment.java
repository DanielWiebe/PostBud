package com.shiftdev.postbud.ui.parcel_overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
          final TextView textView = root.findViewById(R.id.text_dashboard);
          parcelOverviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
               @Override
               public void onChanged(@Nullable String s) {
                    textView.setText(s);
               }
          });
          return root;
     }
}