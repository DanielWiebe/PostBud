package com.shiftdev.postbud.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shiftdev.postbud.ParcelListActivity;
import com.shiftdev.postbud.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {


     @BindView(R.id.bt_go_to_add)
     Button nextScreenBT;

     private HomeViewModel homeViewModel;

     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
          homeViewModel =
                  new ViewModelProvider(this).get(HomeViewModel.class);
          View root = inflater.inflate(R.layout.fragment_home, container, false);
          final TextView textView = root.findViewById(R.id.text_home);
          homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
               @Override
               public void onChanged(@Nullable String s) {
                    textView.setText(s);
               }
          });
          ButterKnife.bind(this, root);


          nextScreenBT.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), ParcelListActivity.class);
                    requireActivity().startActivity(intent);
               }
          });


          return root;
     }


}