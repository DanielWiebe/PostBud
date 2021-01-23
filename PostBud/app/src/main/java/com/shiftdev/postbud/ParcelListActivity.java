package com.shiftdev.postbud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shiftdev.postbud.Utils.Parcel;
import com.shiftdev.postbud.Utils.ParcelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParcelListActivity extends AppCompatActivity {

     private final FirebaseFirestore db = FirebaseFirestore.getInstance();
     private final CollectionReference parcelRef = db.collection("parcels");
     @BindView(R.id.rv_parcel)
     RecyclerView recyclerView;
     private ParcelAdapter adapter;
     private Query query;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_parcel_list);
          ButterKnife.bind(this);

          setUpRecyclerView();
     }

     private void setUpRecyclerView() {
          Query query = parcelRef.orderBy("priority", Query.Direction.DESCENDING);
          FirestoreRecyclerOptions<Parcel> options = new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();

          adapter = new ParcelAdapter(options);
          recyclerView.setHasFixedSize(true);
          recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setAdapter(adapter);
          new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                  ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
               @Override
               public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
               }

               @Override
               public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              switch (which) {
                                   case DialogInterface.BUTTON_POSITIVE:
                                        adapter.deleteItem(viewHolder.getAdapterPosition());
                                        recyclerView.setAdapter(adapter);
                                        break;

                                   case DialogInterface.BUTTON_NEGATIVE:
                                        recyclerView.setAdapter(adapter);
                                        break;
                              }
                         }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("Delete: Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


               }
          });
     }


     @Override
     public void onStart() {
          super.onStart();
          adapter.startListening();
     }

     @Override
     public void onPause() {
          super.onPause();
          //save db instance snapshot here
     }


     @Override
     public void onStop() {
          super.onStop();

          adapter.stopListening();
     }
}
