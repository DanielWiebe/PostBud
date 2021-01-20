package com.shiftdev.postbud.Utils;

import android.graphics.Color;
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
     private OnItemClickListener listener;

     public ParcelAdapter(@NonNull FirestoreRecyclerOptions<Parcel> options) {
          super(options);
     }

     @Override
     protected void onBindViewHolder(@NonNull ParcelHolder holder, int position, @NonNull Parcel model) {
//          holder.priorityTV.setText(String.valueOf(model.  getPriority()));
//          holder.orderedbyTV.setText(String.valueOf(model  getOrderedBy()));
//          holder.handledbyTV.setText(String.valueOf(model. getHandledBy()));
//          holder.descTV.setText(String.valueOf(model.      getDescription()));
//          holder.origTV.setText(String.valueOf(model.      getOrigin()));
//          holder.statusTV.setText(String.valueOf(model.    getStatus()));
//          holder.destTV.setText(String.valueOf(model.      getDestination()));
//          holder.weightTV.setText(String.valueOf(model.    getWeight()));
          holder.descTV.setText(String.valueOf(model.getDescription()));
          int priority = (int) model.getPriority();
          if (priority == 0) {
               holder.priorityTV.setText("Lowest");
          } else if (priority == 1) {
               holder.priorityTV.setText("Low");
               holder.priorityTV.setTextColor(Color.BLUE);
          } else if (priority == 2) {
               holder.priorityTV.setText("Medium");
               holder.priorityTV.setTextColor(Color.MAGENTA);
          } else if (priority == 3) {
               holder.priorityTV.setText("High");
               holder.priorityTV.setTextColor(Color.RED);
          } else if (priority == 4 || priority > 4) {
               holder.priorityTV.setText("Deliver NOW");
               holder.priorityTV.setTextColor(Color.GREEN);
               holder.priorityTV.setBackgroundColor(Color.GRAY);
          }
          holder.orderedbyTV.setText(String.valueOf(model.getOrderedBy()));
          holder.handledbyTV.setText(String.valueOf(model.getHandledBy()));
          holder.origTV.setText(String.valueOf(model.getOrigin()));
          holder.statusTV.setText(String.valueOf(model.getStatus()));
          holder.weightTV.setText(String.valueOf(model.getWeight()));
          holder.weightTV.append(" Lbs");
          holder.destTV.setText(String.valueOf(model.getDestination()));
     }

     @NonNull
     @Override
     public ParcelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcel_item_view,
                  parent, false);
          return new ParcelHolder(v);
     }

     public void onDataChanged() {
          super.onDataChanged();
     }

     public void deleteItem(int position) {
          getSnapshots().getSnapshot(position).getReference().delete();

     }

     public void setOnItemClickListener(OnItemClickListener listener) {
          this.listener = listener;
     }

     public interface OnItemClickListener {
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
          @BindView(R.id.tv_origin)
          TextView origTV;
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
               itemView.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                         listener.onParcelClick(getSnapshots().getSnapshot(position), position);
                    }
               });
          }
     }
}
