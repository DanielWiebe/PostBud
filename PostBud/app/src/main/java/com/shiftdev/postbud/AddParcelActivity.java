package com.shiftdev.postbud;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shiftdev.postbud.Utils.Parcel;

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
        Parcel parcelToGo = new Parcel("Some Parcel ID",
                etLocation.getText().toString().trim(),
                etOrigin.getText().toString().trim(),
                etDest.getText().toString().trim(),
                etOrderedBy.getText().toString().trim(),
                etDesc.getText().toString().trim(),
                Double.parseDouble(String.valueOf(etWeight.getText())),
                Timestamp.now(),
                Parcel.Priority.valueOf(etPriority.getText().toString().trim()),
                Parcel.Status.valueOf(etStatus.getText().toString().trim()));

     public void saveParcel(View view) {
          Parcel parcelToGo = new Parcel(etLocation.getText().toString().trim(),
                  etOrigin.getText().toString().trim(),
                  etDest.getText().toString().trim(),
                  etOrderedBy.getText().toString().trim(),
                  etDesc.getText().toString().trim(),
                  Double.parseDouble(String.valueOf(etWeight.getText())),
                  Timestamp.now(),
                  Parcel.Priority.valueOf(etPriority.getText().toString().trim()),
                  Parcel.Status.valueOf(etStatus.getText().toString().trim()));
          //TODO THIS NEEDS TO BE HANDLED AND AUTO FILLED USING THE ID FROM FIREBASE AUTHENTICATION SOMEHOW


    }
}
