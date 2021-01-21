package com.shiftdev.postbud.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shiftdev.postbud.R;

public class AccountFragment extends Fragment {


     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
          View root = inflater.inflate(R.layout.login_page, container, false);

          setHasOptionsMenu(true);
          return root;

     }


     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          inflater.inflate(R.menu.menu_account_fragment_in_activity, menu);
          menu.clear();
          super.onCreateOptionsMenu(menu, inflater);
     }
}