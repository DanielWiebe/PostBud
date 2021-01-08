package com.shiftdev.postbud;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddParcelActivity extends AppCompatActivity {
     private static final String KEY_CURRENT_LOCATION = "current_location";
     private static final String KEY_DESCRIPTION = "description";
     private static final String KEY_DESTINATION = "destination";
     private static final String KEY_HANDLED_BY = "handled_by";
     private static final String KEY_ORDERED_BY = "ordered_by";
     private static final String KEY_ORIGIN = "origin";
     private static final String KEY_PRIORITY = "priority";
     private static final String KEY_STATUS = "status";
     private static final String KEY_WEIGHT = "weight";
     //Firestore reference to the database
     private final FirebaseFirestore db = FirebaseFirestore.getInstance();
     private final DocumentReference parcelReference = db.document("parcels/My First Parcel");
     @BindView(R.id.et_description)
     EditText etDesc;
     @BindView(R.id.et_location)
     EditText etLocation;
     @BindView(R.id.et_ordered_by)
     EditText etOrderedBy;
     @BindView(R.id.et_priority)
     EditText etPriority;
     @BindView(R.id.et_status)
     EditText etStatus;
     @BindView(R.id.et_destination)
     EditText etDest;
     @BindView(R.id.et_origin)
     EditText etOrigin;
     @BindView(R.id.et_weight)
     EditText etWeight;
     @BindView(R.id.bt_save)
     Button saveButton;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_add_parcel);
          ButterKnife.bind(this);

     }


     public void saveParcel(View view) {
          Parcel parcelToGo = new Parcel(etLocation.getText().toString().trim(),
                  etOrigin.getText().toString().trim(),
                  etDest.getText().toString().trim(),
                  etOrderedBy.getText().toString().trim(),
                  etDesc.getText().toString().trim(),
                  etStatus.getText().toString().trim(),
                  etPriority.getText().toString().trim(),
                  Double.parseDouble(String.valueOf(etWeight.getText())));

          //TODO THIS NEEDS TO BE HANDLED AND AUTO FILLED USING THE ID FROM FIREBASE AUTHENTICATION SOMEHOW
          String handled_by = "TODO";

          Map<String, Object> parcel = new HashMap<>();
          parcel.put(KEY_CURRENT_LOCATION, parcelToGo.getCurrentLocation());
          parcel.put(KEY_DESCRIPTION, parcelToGo.getDescription());
          parcel.put(KEY_DESTINATION, parcelToGo.getDestination());
          parcel.put(KEY_HANDLED_BY, parcelToGo.getHandledBy());
          parcel.put(KEY_ORDERED_BY, parcelToGo.getOrderedBy());
          parcel.put(KEY_ORIGIN, parcelToGo.getOrigin());
          parcel.put(KEY_PRIORITY, parcelToGo.getPriority());
          parcel.put(KEY_STATUS, parcelToGo.getStatus());
          parcel.put(KEY_WEIGHT, parcelToGo.getWeight());
          parcelReference.set(parcel)
                  .addOnSuccessListener(aVoid -> Toast.makeText(AddParcelActivity.this, "Parcel saved to Firebase", Toast.LENGTH_SHORT).show())
                  .addOnFailureListener(e -> {
                       Toast.makeText(AddParcelActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                       Log.d("AddParcelActivity", e.toString());
                  });


     }
}