package com.shiftdev.postbud;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

     //Firestore reference to the database
     private FirebaseFirestore db = FirebaseFirestore.getInstance();
     private DocumentReference parcelReference = db.document("parcels/My First Parcel");

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_add_parcel);
          ButterKnife.bind(this);


     }

     public void saveNote(View view) {
          String desc = etDesc.getText().toString().trim();
          String orderedBy = etOrderedBy.getText().toString().trim();
          String loc = etLocation.getText().toString().trim();
          String priority = etPriority.getText().toString().trim();
          String status = etStatus.getText().toString().trim();
          String dest = etDest.getText().toString().trim();
          String orig = etOrigin.getText().toString().trim();
          double weight = Double.parseDouble(String.valueOf(etWeight.getText()));

          //TODO THIS NEEDS TO BE HANDLED AND AUTO FILLED USING THE ID FROM FIREBASE AUTHENTICATION SOMEHOW
          String handled_by = "TODO";

          Map<String, Object> parcel = new HashMap<>();
          parcel.put(KEY_CURRENT_LOCATION, loc);
          parcel.put(KEY_DESCRIPTION, desc);
          parcel.put(KEY_DESTINATION, dest);
          parcel.put(KEY_HANDLED_BY, handled_by);
          parcel.put(KEY_ORDERED_BY, orderedBy);
          parcel.put(KEY_ORIGIN, orig);
          parcel.put(KEY_PRIORITY, priority);
          parcel.put(KEY_STATUS, status);
          parcelReference.set(parcel)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                            Toast.makeText(AddParcelActivity.this, "Parcel saved to Firebase", Toast.LENGTH_SHORT).show();
                       }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddParcelActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("AddParcelActivity", e.toString());
                       }
                  });


     }
}