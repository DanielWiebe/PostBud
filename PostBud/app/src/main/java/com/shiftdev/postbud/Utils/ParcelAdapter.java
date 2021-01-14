package com.shiftdev.postbud.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.shiftdev.postbud.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParcelAdapter extends FirestoreRecyclerAdapter<Parcel, ParcelAdapter.ParcelHolder> {
     private onParcelClickListener listener;

     public ParcelAdapter(@NonNull FirestoreRecyclerOptions<Parcel> options) {
          super(options);
     }

     @Override
     protected void onBindViewHolder(@NonNull ParcelHolder holder, int position, @NonNull Parcel model) {
          holder.priorityTV.setText(String.valueOf(model.getPriority()));
          holder.orderedbyTV.setText(String.valueOf(model.getOrderedBy()));
          holder.handledbyTV.setText(String.valueOf(model.getHandledBy()));
          holder.descTV.setText(String.valueOf(model.getDescription()));
          holder.fromTV.setText(String.valueOf(model.getOrigin()));
          holder.statusTV.setText(String.valueOf(model.getStatus()));
          holder.destTV.setText(String.valueOf(model.getDestination()));
          holder.weightTV.setText(String.valueOf(model.getWeight()));
     }

     @NonNull
     @Override
     public ParcelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcel_item_view,
                  parent, false);
          return new ParcelHolder(v);
     }


     void deleteItem(int position) {
          getSnapshots().getSnapshot(position).getReference().delete();
     }

     void setOnItemClickListener(onParcelClickListener listener) {
          this.listener = listener;
     }

     public interface onParcelClickListener {
          void onParcelClick(DocumentSnapshot documentSnapshot, int position);
     }

     class ParcelHolder extends RecyclerView.ViewHolder {
          @BindView(R.id.tv_priority)
          TextView priorityTV;
          @BindView(R.id.tv_ordered_by)
          TextView orderedbyTV;
          @BindView(R.id.tv_handled_by)
          TextView handledbyTV;
          @BindView(R.id.tv_description)
          TextView descTV;
          @BindView(R.id.tv_from)
          TextView fromTV;
          @BindView(R.id.tv_status)
          TextView statusTV;
          @BindView(R.id.tv_dest)
          TextView destTV;
          @BindView(R.id.tv_weight)
          TextView weightTV;

          @BindView(R.id.cv_container)
          CardView container;

          public ParcelHolder(View itemView) {
               super(itemView);
               ButterKnife.bind(this, itemView);
               container.setOnClickListener((View.OnClickListener) view -> {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && listener != null) {
                         listener.onParcelClick(getSnapshots().getSnapshot(pos), pos);
                    }
               });
          }
     }
}