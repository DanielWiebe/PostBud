package com.shiftdev.postbud.ui.parcel_overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shiftdev.postbud.Parcel;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.ParcelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParcelOverviewFragment extends Fragment {
     //getting the layout reference for the recyclerview to display
     @BindView(R.id.rv_parcel)
     RecyclerView recyclerView;

     //firebase references
     private FirebaseFirestore db = FirebaseFirestore.getInstance();
     private CollectionReference parcelRef = db.collection("parcels");

     //view adapter instantiation
     private ParcelAdapter adapter;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

          //boilerplate code for making the overview layout and working with the ParcelOverviewViewModel architecture
          //    ParcelOverviewViewModel parcelOverviewViewModel = new ViewModelProvider(this).get(ParcelOverviewViewModel.class);
          View root = inflater.inflate(R.layout.fragment_parcel_overview, container, false);
//          final TextView textView = root.findViewById(R.id.text_dashboard);
          //tying the annotated Butterknife references to this fragment layout
          ButterKnife.bind(this, root);
//          parcelOverviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//               @Override
//               public void onChanged(@Nullable String s) {
//                    textView.setText(s);
//               }
//          });


          //calling recyclerview build method

          initViews();
          setUpRecyclerView();
          return root;
     }

     private void initViews() {
          RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
          recyclerView.setLayoutManager(layoutManager);
          setUpRecyclerView();
     }

     private void setUpRecyclerView() {
          Query query = parcelRef.orderBy("priority", Query.Direction.DESCENDING);
          FirestoreRecyclerOptions<Parcel> options = new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();

          adapter = new ParcelAdapter(options);
          recyclerView.setAdapter(adapter);
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