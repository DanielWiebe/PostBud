package com.shiftdev.postbud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class ParcelDetailActivity extends AppCompatActivity {
     @BindView(R.id.tv_priority)
     TextView priorityTV;
     @BindView(R.id.tv_ordered_by)
     TextView orderedbyTV;
     @BindView(R.id.tv_handled_by)
     TextView handledbyTV;
     @BindView(R.id.tv_description)
     TextView descTV;
     @BindView(R.id.tv_origin)
     TextView origTV;
     @BindView(R.id.tv_status)
     TextView statusTV;
     @BindView(R.id.tv_dest)
     TextView destTV;
     @BindView(R.id.tv_weight)
     TextView weightTV;
     DocumentSnapshot snapshot;
     Intent args;
     String selectedDocumentID;
     Toolbar toolbar;
     CollapsingToolbarLayout toolBarLayout;
     private FirebaseFirestore db = FirebaseFirestore.getInstance();
     private CollectionReference parcelDB = db.collection("parcels");

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_parcel_detail);
          ButterKnife.bind(this);

          toolbar = (Toolbar) findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);
          toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

          FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
          fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditParcelActivity.class);
                    Timber.e("Sending ID: " + selectedDocumentID);
                    intent.putExtra("snapshot_ref", selectedDocumentID);
                    startActivity(intent);
               }
          });

          args = getIntent();
          selectedDocumentID = args.getStringExtra("snapshot_ref");
          Timber.e("snapshot ID " + selectedDocumentID);
          DocumentReference documentReference = parcelDB.document(selectedDocumentID);
          documentReference.get().addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
               if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                         Map<String, Object> parcelMap = document.getData();
                         Timber.e(parcelMap.toString());
                         setTextOfAllViewsFromParcelMap(parcelMap);
                    } else {
                         Timber.e("No such document");
                    }
               } else {
                    Timber.e("get failed with %s", task.getException());
               }
          });

     }

     private void setTextOfAllViewsFromParcelMap(Map<String, Object> parcelMapFromSnapshotResult) {
          toolBarLayout.setTitle("Currently in: " + parcelMapFromSnapshotResult.get("currentLocation").toString());
          descTV.setText(parcelMapFromSnapshotResult.get("description").toString());
          Long priority = (Long) parcelMapFromSnapshotResult.get("priority");
          if (priority == 0) {
               priorityTV.setText("Lowest");
          } else if (priority == 1) {
               priorityTV.setText("Low");
               priorityTV.setTextColor(Color.BLUE);
          } else if (priority == 2) {
               priorityTV.setText("Medium");
               priorityTV.setTextColor(Color.MAGENTA);
          } else if (priority == 3) {
               priorityTV.setText("High");
               priorityTV.setTextColor(Color.RED);
          } else if (priority == 4 || priority > 4) {
               priorityTV.setText("Deliver NOW");
               priorityTV.setTextColor(Color.GREEN);
               priorityTV.setBackgroundColor(Color.GRAY);
          }
          orderedbyTV.setText(parcelMapFromSnapshotResult.get("orderedBy").toString());
          handledbyTV.setText(parcelMapFromSnapshotResult.get("handledBy").toString());
          origTV.setText(parcelMapFromSnapshotResult.get("origin").toString());
          statusTV.setText(parcelMapFromSnapshotResult.get("status").toString());
          weightTV.setText(parcelMapFromSnapshotResult.get("weight").toString());
          weightTV.append(" Lbs");
          destTV.setText(parcelMapFromSnapshotResult.get("destination").toString());
     }
}