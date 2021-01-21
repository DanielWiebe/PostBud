package com.shiftdev.postbud;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import timber.log.Timber;

import static timber.log.Timber.e;


public class MainActivity extends AppCompatActivity {
     RelativeLayout relativeLayout;

//     public static void testUploadParcel(Activity context) {
//          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0123456789", "Baghdad", "Minsk", "Winnipeg",
//                  "Daniel Wiebe", "BTS Merch", 5.58, Timestamp.now(), 2, "IN TRANSIT"));
//          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0000000001", "Ireland", "Germany", "Egypt",
//                  "Lil'Machty", "BMW 750li", 4000, Timestamp.now(), 1, "AWAITING PAYMENT"));
//          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0000000002", "Serbia", "China", "Nebraska",
//                  "Andrey Tokarski", "Fidget Spinner", 0.3, Timestamp.now(), 3, "SHIPPED"));
//     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          BottomNavigationView navView = findViewById(R.id.nav_view);
          // Passing each menu ID as a set of Ids because each
          // menu should be considered as top level destinations.
          AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                  R.id.navigation_home, R.id.navigation_list, R.id.navigation_account)
                  .build();
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
          NavigationUI.setupWithNavController(navView, navController);
          Timber.plant(new Timber.DebugTree());
          e("Test Message");
          // Testing Firestore

          relativeLayout = findViewById(R.id.container);
          networkDisplayCheck(relativeLayout);

          FirebaseAuth mAuth = FirebaseAuth.getInstance();

          // Testing accounts
          String email = "art4321@gmail.com";
          String password = "123456";

          mAuth.signInWithEmailAndPassword(email, password);

          FirebaseFirestore db = FirebaseFirestore.getInstance();

          // testUploadParcel(this);
     }

     private void networkDisplayCheck(RelativeLayout relativeLayout) {
          if (networkDisconnected()) {
               e("network disconnected");
               Snackbar.make(relativeLayout, "connection lost", Snackbar.LENGTH_LONG)
                       .setAction("Go To Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                 startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                       }).show();
          } else if (!networkDisconnected()) {
               Snackbar.make(relativeLayout, "connected with Wi-Fi", Snackbar.LENGTH_SHORT).show();
               //.setAction(startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))){}
          }
     }

     public boolean networkDisconnected() {
          e("started network check");
          boolean hasConnection = true;
          ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
          NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
          for (NetworkInfo info : networkInfo) {
               e("checking each info");
               if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                    if (info.isConnected()) {
                         e("WIFI CONNECTED");
                         hasConnection = false;
                    }
               }
               if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                    if (info.isConnected()) {
                         Timber.e("DATA CONNECTED");
                         hasConnection = false;
                    }
               }
          }
          return hasConnection;
     }

     private void goToNewParcel() {
          Intent intent = new Intent(this, AddParcelActivity.class);
          startActivity(intent);
     }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater menuInflater = getMenuInflater();
          menuInflater.inflate(R.menu.menu_main_activity, menu);
          return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
               case R.id.addParcel:
                    goToNewParcel();
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     @Override
     protected void onResume() {
          e("onresume");
          networkDisplayCheck(relativeLayout);
          super.onResume();
     }

     @Override
     protected void onStart() {
          e("onstart");
          networkDisplayCheck(relativeLayout);
          super.onStart();
     }
}
