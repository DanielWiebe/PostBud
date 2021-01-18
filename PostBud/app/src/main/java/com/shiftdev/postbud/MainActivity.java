package com.shiftdev.postbud;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shiftdev.postbud.Utils.Employee;
import com.shiftdev.postbud.Utils.FirebaseNav;
import com.shiftdev.postbud.Utils.Parcel;
import com.shiftdev.postbud.Utils.PostBudFirestoreUtils;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
     // TAG
     private static final String TAG = "MAIN";

     public static void testUploadParcel(Activity context) {
          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0123456789", "Baghdad", "Minsk", "Winnipeg",
                  "Daniel Wiebe", "BTS Merch", 5.58, Timestamp.now(), 2, "IN TRANSIT"));
          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0000000001", "Ireland", "Germany", "Egypt",
                  "Lil'Machty", "BMW 750li", 4000, Timestamp.now(), 1, "AWAITING PAYMENT"));
          PostBudFirestoreUtils.uploadParcel(context, new Parcel("PB0000000002", "Serbia", "China", "Nebraska",
                  "Andrey Tokarski", "Fidget Spinner", 0.3, Timestamp.now(), 3, "SHIPPED"));
     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          BottomNavigationView navView = findViewById(R.id.nav_view);
          // Passing each menu ID as a set of Ids because each
          // menu should be considered as top level destinations.
          AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                  R.id.navigation_home, R.id.navigation_list, R.id.navigation_notifications)
                  .build();
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
          NavigationUI.setupWithNavController(navView, navController);
          Timber.plant(new Timber.DebugTree());
          // Testing Firestore
          FirebaseAuth mAuth = FirebaseAuth.getInstance();

          // Testing accounts
          String adminEmail = "artAdmin@gmail.com";
          String adminPassword = "password1";
          String adminId = "admin1";
          String adminFirstName = "Art";
          String adminLastName = "Kim";

          String employeeEmail = "artEmployee@gmail.com";
          String employeePassword = "password2";
          String employeeId = "employee1";
          String employeeFirstName = "Art";
          String employeeLastName = "Park";

//        PostBudFirestoreUtils.createAccountAndUploadToFirestore(this, new Administrator(adminEmail, adminPassword, adminId, adminFirstName, adminLastName));
//        PostBudFirestoreUtils.createAccountAndUploadToFirestore(this, new Employee(employeeEmail, employeePassword, employeeId, employeeFirstName, employeeLastName));

          mAuth.signInWithEmailAndPassword(employeeEmail, employeePassword);

          FirebaseFirestore db = FirebaseFirestore.getInstance();

          testUploadParcel(this);

          CollectionReference administratorsRef = db.collection(FirebaseNav.ADMINISTRATORS.getValue(this));
          Query query = administratorsRef.whereEqualTo(FirebaseNav.EMAIL.getValue(this), adminEmail);
          query.get().addOnSuccessListener(queryDocumentSnapshots -> {
               for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Employee emp = snapshot.toObject(Employee.class);
//                Log.e(TAG, emp.getEmail());
//                Log.e(TAG, emp.toString());
               }
          });
     }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {

          return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {

          return super.onOptionsItemSelected(item);
     }
}