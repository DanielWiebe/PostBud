package com.shiftdev.postbud.ui.parcel_overview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shiftdev.postbud.ParcelDetailActivity;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.Parcel;
import com.shiftdev.postbud.Utils.ParcelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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
        Timber.plant(new Timber.DebugTree());
        //calling recyclerview build method
        setUpRecyclerView();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpRecyclerView() {
        // TODO this is to implement the proper firebase authentication instance with the handled_by field
        //  that only allows parcels that were added by the user to see them
        Query query = parcelRef.orderBy("priority", Query.Direction.DESCENDING);
        //whereEqualTo(FirebaseNav.DESCRIPTION.getValue(getActivity()), "test desc");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete: Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        }).attachToRecyclerView(recyclerView);

        //TODO This is the method that handles what happens when the item gets clicked. we have
        // the object that gets
        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            Timber.e("Adapter Item Selected " + position);
            Timber.e(documentSnapshot.getId());
            Intent intent = new Intent(getActivity(), ParcelDetailActivity.class);
            intent.putExtra("snapshot_ref", documentSnapshot.getId());
            startActivity(intent);


            //               ParcelOverviewFragmentDirections.ActionNavigationListToParcelDetailActivity action = ParcelOverviewFragmentDirections.actionNavigationListToParcelDetailActivity();
//               action.setParcelDocumentIdArgs(documentSnapshot.getId());
//               Navigation.findNavController(getView()).navigate(action);

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