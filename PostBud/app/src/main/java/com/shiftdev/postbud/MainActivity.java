package com.shiftdev.postbud;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Timber.plant();
        // Testing Firestore
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        PostBudAppContext.setContext(this);

        // Testing accounts
        CurrentUserSingleton.getInstance();
        String email = "art4321@gmail.com";
        String password = "123456";

        mAuth.signInWithEmailAndPassword(email, password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        testUploadParcel(this);
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
