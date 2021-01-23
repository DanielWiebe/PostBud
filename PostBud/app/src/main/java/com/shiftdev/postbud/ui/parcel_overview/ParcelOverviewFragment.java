package com.shiftdev.postbud.ui.parcel_overview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.shiftdev.postbud.AddParcelActivity;
import com.shiftdev.postbud.ParcelDetailActivity;
import com.shiftdev.postbud.R;
import com.shiftdev.postbud.Utils.Parcel;
import com.shiftdev.postbud.Utils.ParcelAdapter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ParcelOverviewFragment extends Fragment {
     //getting the layout reference for the recyclerview to display
     @BindView(R.id.rv_parcel)
     RecyclerView recyclerView;

     //firebase references
     private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("parcels");

     //view adapter instantiation
     private ParcelAdapter adapter;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
          View root = inflater.inflate(R.layout.fragment_parcel_overview, container, false);
          //tying the annotated Butterknife references to this fragment layout
          ButterKnife.bind(this, root);
          setHasOptionsMenu(true);
          Timber.plant(new Timber.DebugTree());
          //calling recyclerview build method
          setUpRecyclerView();
          return root;
     }

     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          menu.clear();
          inflater.inflate(R.menu.menu_parcel_overview_fragment_in_activity, menu);

          //SearchManager searchManager = (SearchManager) getSystemService(getContext(), );
          SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                  .getActionView();

          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), "Feature Not Quite Done", Toast.LENGTH_SHORT).show();
                    adapter = new ParcelAdapter(buildSearchQuery(query));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    return false;
               }

               @Override
               public boolean onQueryTextChange(String query) {
                    adapter = new ParcelAdapter(buildSearchQuery(query));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    return false;
               }
          });
          super.onCreateOptionsMenu(menu, inflater);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
               case R.id.action_settings:
                    Toast.makeText(getActivity(), "Coming Soon...", Toast.LENGTH_SHORT).show();
               case R.id.action_add_parcel:
                    goToNewParcel();
               case R.id.action_filter_ascending:
                    buildPriorityAscendingQuery();
               case R.id.action_filter_descending:
                    buildPriorityDescendingQuery();
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     private void goToNewParcel() {
          Intent intent = new Intent(getActivity(), AddParcelActivity.class);
          startActivity(intent);
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
          FirestoreRecyclerOptions<Parcel> options = buildPriorityDescendingQuery();

          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
          adapter = new ParcelAdapter(options);
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

     @NotNull
     private FirestoreRecyclerOptions<Parcel> buildPriorityDescendingQuery() {
          Toast.makeText(getContext(), "Feature Not Quite Done", Toast.LENGTH_SHORT).show();
          Query query = collectionReference.orderBy("priority", Query.Direction.DESCENDING);
          //whereEqualTo(FirebaseNav.DESCRIPTION.getValue(getActivity()), "test desc");
          return new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();
     }

     @NotNull
     private FirestoreRecyclerOptions<Parcel> buildSearchQuery(String searchText) {
          Toast.makeText(getContext(), "Feature Not Quite Done", Toast.LENGTH_SHORT).show();
          Query query = collectionReference.orderBy("description", Query.Direction.ASCENDING).startAt(searchText).endAt(searchText + "\uf8ff");
          //.where(FirebaseNav.DESCRIPTION.getValue(getActivity()), "test desc");
          return new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();
     }

     @NotNull
     private FirestoreRecyclerOptions<Parcel> buildPriorityAscendingQuery() {
          Toast.makeText(getContext(), "Feature Not Quite Done", Toast.LENGTH_SHORT).show();
          Query query = collectionReference.orderBy("priority", Query.Direction.ASCENDING);
          //whereEqualTo(FirebaseNav.DESCRIPTION.getValue(getActivity()), "test desc");
          return new FirestoreRecyclerOptions.Builder<Parcel>()
                  .setQuery(query, Parcel.class)
                  .build();
     }

     @Override
     public void onStart() {
          super.onStart();
          setUpRecyclerView();
          adapter.startListening();
     }

     @Override
     public void onStop() {
          super.onStop();
          adapter.stopListening();
     }
}