package com.shiftdev.postbud.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shiftdev.postbud.R;
import com.shiftdev.postbud.ui.activities.LoginActivity;
import com.shiftdev.postbud.ui.activities.parcelStatusActivity;
import com.shiftdev.postbud.ui.activities.recentNumbersActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {


     @BindView(R.id.trackButton)
     Button trackButton;

     @BindView(R.id.recentNumberButton)
     Button recentNumberButton;

     @BindView(R.id.signInButton)
     Button signInButton;

     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
          View root;

          root = inflater.inflate(R.layout.fragment_home, container, false);
          ButterKnife.bind(this, root);
          trackButton.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), parcelStatusActivity.class);
                    requireActivity().startActivity(intent);
               }
          });
          recentNumberButton.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), recentNumbersActivity.class);
                    requireActivity().startActivity(intent);
               }


          });
          signInButton.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
               }


          });


          return root;
     }





}