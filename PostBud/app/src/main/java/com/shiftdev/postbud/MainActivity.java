package com.shiftdev.postbud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

     public static final String PARCELS = "parcels";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          BottomNavigationView navView = findViewById(R.id.nav_view);
          // Passing each menu ID as a set of Ids because each
          // menu should be considered as top level destinations.
          AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                  R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                  .build();
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
          NavigationUI.setupWithNavController(navView, navController);

          // Testing Firestore
          FirebaseAuth mAuth = FirebaseAuth.getInstance();

          FirebaseFirestore db = FirebaseFirestore.getInstance();

          Account admin = new Administrator("art202042069@gmail.com","Art", "password", this);

          Parcel newParcel = new Parcel("Vancouver", "Toronto", "Winnipeg", "Colt", "Sport Wheels - R8", 1, "kjG4GHj5JNhb53");

          db.collection(PARCELS).add(newParcel);
     }

}