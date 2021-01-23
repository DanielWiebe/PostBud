package com.shiftdev.postbud;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shiftdev.postbud.Utils.Parcel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static java.lang.Integer.parseInt;
import static timber.log.Timber.e;

public class EditParcelActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_LOCATION = "current_location";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DESTINATION = "destination";
    private static final String KEY_HANDLED_BY = "handled_by";
    private static final String KEY_ORDERED_BY = "ordered_by";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_STATUS = "status";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DATE = "date";
    //Firestore reference to the database
//     private final
//     private final
    @BindView(R.id.et_description)
    EditText etDesc;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_ordered_by)
    EditText etOrderedBy;
    @BindView(R.id.et_priority)
    EditText etPriority;
    @BindView(R.id.sp_status)
    Spinner spStatus;
    @BindView(R.id.et_destination)
    EditText etDest;
    @BindView(R.id.et_origin)
    EditText etOrigin;
    @BindView(R.id.et_weight)
    EditText etWeight;

    //     ParcelDetailActivityArgs args = ParcelDetailActivityArgs.fromBundle(getIntent().getExtras());
    String selectedDocumentID;
    ArrayAdapter<CharSequence> adapter;
    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("parcels");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel);
        ButterKnife.bind(this);
        setTitle("Edit Parcel");
        adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);

        Intent args = getIntent();
        selectedDocumentID = args.getStringExtra("snapshot_ref");
        Timber.e("Received ID: " + selectedDocumentID);
        DocumentReference documentReference = collectionReference.document(selectedDocumentID);
        GetParcelRefAndAutoFillFields(documentReference);
    }


    private void GetParcelRefAndAutoFillFields(DocumentReference documentReference) {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                etLocation.setText(documentSnapshot.get("currentLocation").toString());
                etDesc.setText(documentSnapshot.get("description").toString());
                etWeight.setText(String.valueOf(documentSnapshot.get("weight")));
                etPriority.setText(String.valueOf(documentSnapshot.get("priority")));
                etOrderedBy.setText(documentSnapshot.get("orderedBy").toString());
                etOrigin.setText(documentSnapshot.get("origin").toString());
                spStatus.setSelection(adapter.getPosition(documentSnapshot.get("status").toString()));
                etWeight.setText(documentSnapshot.get("weight").toString());
                etDest.setText(documentSnapshot.get("destination").toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Problem loading in Values!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveChanges() {
        //Timestamp.now(),
        //TODO THIS NEEDS TO BE HANDLED AND AUTO FILLED USING THE ID FROM FIREBASE AUTHENTICATION SOMEHOW

        Map<String, Object> parcelMap = new HashMap<>();
        parcelMap.put(KEY_CURRENT_LOCATION, etLocation.getText().toString().trim());
        parcelMap.put(KEY_DESCRIPTION, etDesc.getText().toString().trim());
        parcelMap.put(KEY_DESTINATION, etDest.getText().toString().trim());
        parcelMap.put("documentId", null);
        parcelMap.put(KEY_HANDLED_BY, FirebaseAuth.getInstance().getCurrentUser());
        parcelMap.put(KEY_ORDERED_BY, etOrderedBy.getText().toString().trim());
        parcelMap.put(KEY_ORIGIN, etOrigin.getText().toString().trim());
        parcelMap.put(KEY_PRIORITY, parseInt(etPriority.getText().toString()));
        parcelMap.put(KEY_STATUS, spStatus.getSelectedItem().toString().trim());
        parcelMap.put(KEY_WEIGHT, Double.parseDouble(String.valueOf(etWeight.getText())));
        parcelMap.put(KEY_DATE, Timestamp.now().toString());
        collectionReference.document(selectedDocumentID).set(new Parcel(
                        etLocation.getText().toString().trim(),
                        etOrigin.getText().toString().trim(),
                        etDest.getText().toString().trim(),
                        etOrderedBy.getText().toString().trim(),
                        etDesc.getText().toString().trim(),
                        Double.parseDouble(String.valueOf(etWeight.getText())),
                        Timestamp.now(),
                        parseInt(etPriority.getText().toString()),
                        spStatus.getSelectedItem().toString().trim())
//                  etLocation.getText().toString().trim(),
//                  Timestamp.now().toString(),
//                  etDesc.getText().toString().trim(),
//                  etDest.getText().toString().trim(),
//                  "12343",
//                  FirebaseAuth.getInstance().getCurrentUser(),
//                  etOrderedBy.getText().toString().trim(),
//                  etOrigin.getText().toString().trim(),
//                  "1230r9hflashl984h98",
//                  Integer.parseInt(etPriority.getText().toString()),
//                  spStatus.getSelectedItem().toString().trim(),
//                  Double.parseDouble(String.valueOf(etWeight.getText()))

          ).addOnSuccessListener(aVoid -> {
               Toast.makeText(EditParcelActivity.this, "Parcel changes saved to Firebase", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);
          })
                  .addOnFailureListener(e -> {
                       Toast.makeText(EditParcelActivity.this, "Error in saving changes. Please try again!", Toast.LENGTH_SHORT).show();
                       Timber.e("EditParcelActivity");
                  });
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater menuInflater = getMenuInflater();
          menuInflater.inflate(R.menu.menu_edit_parcel, menu);
          return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
               case R.id.save_changes:
                    if (!networkDisconnected()) {
                         saveChanges();
                         Toast.makeText(this, "Saving Parcel", Toast.LENGTH_SHORT).show();
                         return true;
                    } else if (networkDisconnected()) {
                         Toast.makeText(EditParcelActivity.this, "Network Error: Can't Save Parcel Changes Offline.", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(this, ParcelDetailActivity.class);
                         intent.putExtra("snapshot_ref", selectedDocumentID);
                         startActivity(intent);
                    }
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     public boolean networkDisconnected() {
          e("started network check");
          boolean hasConnection = true;
          ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
          NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
          for (NetworkInfo info : networkInfo) {
               e("checking each info");
               if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                    if (info.isConnected()) {
                         e("WIFI CONNECTED");
                         hasConnection = false;
                    }
               }
               if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                    if (info.isConnected()) {
                         Timber.e("DATA CONNECTED");
                         hasConnection = false;
                    }
               }
          }
          return hasConnection;
     }
}
