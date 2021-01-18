package com.shiftdev.postbud.ui.parcel_overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.Parcel;
import com.shiftdev.postbud.Utils.ParcelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static timber.log.Timber.e;
import static timber.log.Timber.w;

public class ParcelOverviewFragment extends Fragment {
     //getting the layout reference for the recyclerview to display
     @BindView(R.id.rv_parcel)
     RecyclerView recyclerView;

     //firebase references
     private CollectionReference parcelRef = FirebaseFirestore.getInstance().collection("parcels");

     //view adapter instantiation
     private ParcelAdapter adapter;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

          View root = inflater.inflate(R.layout.fragment_parcel_overview, container, false);
          //tying the annotated Butterknife references to this fragment layout
          ButterKnife.bind(this, root);
          //calling recyclerview build method
          setUpRecyclerView();
          return root;
     }

     private void setUpRecyclerView() {
          e("Setting up recyclerview");
          // TODO this is to implement the proper firebase authentication instance with the handled_by field
          //  that only allows parcels that were added by the user to see them
          Query query = parcelRef.orderBy("priority", Query.Direction.DESCENDING).
                  whereEqualTo("description", "test desc");
          FirestoreRecyclerOptions<Parcel> options = new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();
          adapter = new ParcelAdapter(options);

          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
          recyclerView.setAdapter(adapter);

          //handles on swipe to delete events
          new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                  ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
               @Override
               public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
               }

               @Override
               public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    adapter.deleteItem(viewHolder.getAdapterPosition());
                    recyclerView.setAdapter(adapter);
               }
          }).attachToRecyclerView(recyclerView);


          //TODO This is the method that handles what happens when the item gets clicked. we have
          // the object that gets
          adapter.setOnItemClickListener((documentSnapshot, position) -> {
               Parcel parcel = documentSnapshot.toObject(Parcel.class);
               String id = documentSnapshot.getId();
               //String path = documentSnapshot.getReference().getPath();
               assert parcel != null;
               w(parcel.toString());
               Toast.makeText(ParcelOverviewFragment.this.getActivity(), "Position: " + position + " ID: " + id, Toast.LENGTH_LONG).show();
          });
     }

     @Override
     public void onStart() {
          super.onStart();
          adapter.startListening();
     }

     @Override
     public void onStop() {
          super.onStop();
          adapter.stopListening();
     }
}