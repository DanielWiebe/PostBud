package com.shiftdev.postbud;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String PARCELS = "parcels";
    private static final String TAG = "MAIN";

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
        // Accounts
        CurrentUserSingleton.getInstance();
        String artEmail = "art202042069@gmail.com";
        String artPassword = "password";

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Parcel testParcel = new Parcel("Toronto", "Seoul", "Winnipeg", "Artyom", "BTS Merch", 5.58, Timestamp.now(), Parcel.Priority.MEDIUM, Parcel.Status.PENDING);

        mAuth.signInWithEmailAndPassword(artEmail, artPassword).addOnSuccessListener(authResult -> {
            Query query = db.collection(FirebaseNav.ADMINISTRATORS.getValue(this))
                    .whereEqualTo(FirebaseNav.UID.getValue(this), Objects.requireNonNull(authResult.getUser()).getUid());

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Administrator result = document.toObject(Administrator.class);
                        CurrentUserSingleton.getInstance().setCurrentUser(result);
                        Log.e(TAG, CurrentUserSingleton.getInstance().getCurrentUser().getEmail());
                    }
                    CurrentUserSingleton.getInstance().getCurrentUser();
//                                 .newParcel("Winnipeg", "Las Vegas","BC", "Andrey", "Ryzen 9 5900X", 1);
                }
            });
        });

        Account admin = CurrentUserSingleton.getInstance().getCurrentUser();

        Log.e(TAG, FirebaseNav.ADMINISTRATORS.getValue(this));
//          Log.e(TAG, String.valueOf(R.string.firebase_navigation_parcels));

        db.collection(FirebaseNav.PARCELS.getValue(this)).add(testParcel);

//          Log.e(TAG, "Singleton: " + CurrentUserSingleton.getInstance().getCurrentUser().getEmail());
//          Log.e(TAG, "Admin: " + admin.getEmail());
//          Account admin = new Administrator("art202042069@gmail.com","password", "Artyom", "Kim", this);

//          Parcel newParcel = new Parcel("Vancouver", "Toronto", "Winnipeg", "Colt", "Sport Wheels - R8", 1, "kjG4GHj5JNhb53");
//          db.collection(PARCELS).add(newParcel);
    }

}