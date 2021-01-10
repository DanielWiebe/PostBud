package com.shiftdev.postbud;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    // TAG
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

//        Parcel testParcel = new Parcel("Toronto2", "Seou2l", "Winnipeg2", "Artyom", "BTS Merch", 5.58, Timestamp.now(), Parcel.Priority.MEDIUM, Parcel.Status.PENDING);

        CollectionReference administratorsRef = db.collection(FirebaseNav.EMPLOYEES.getValue());
        Query query = administratorsRef.whereEqualTo(FirebaseNav.EMAIL.getValue(), email);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                Employee emp = snapshot.toObject(Employee.class);
                Log.e(TAG, emp.getEmail());
                Log.e(TAG, emp.toString());
            }
        });

        // Testing
//        Account admin = new Administrator("art1234@gmail.com","123456", "Art", "K", this);

//        Account employee = new Employee("art4321@gmail.com","123456", "RussianKoreanIsraeliCanadianSpy", "Art", "K");

//        mAuth.signInWithEmailAndPassword(artEmail, artPassword).addOnSuccessListener(authResult -> {
//            // Testing reading accounts into CurrentUserSingleton
//
//
//            // Testing placing new parcels
//            Query query = db.collection(FirebaseNav.ADMINISTRATORS.getValue(this))
//                    .whereEqualTo(FirebaseNav.UID.getValue(this), Objects.requireNonNull(authResult.getUser()).getUid());
//
//            query.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Administrator result = document.toObject(Administrator.class);
//                        CurrentUserSingleton.getInstance().setCurrentUser(result);
//                        Log.e(TAG, CurrentUserSingleton.getInstance().getCurrentUser().getEmail());
//                    }
//                    CurrentUserSingleton.getInstance().getCurrentUser();
//                                 .newParcel("Winnipeg", "Las Vegas","BC", "Andrey", "Ryzen 9 5900X", 1);
//                }
//            });
//        });

//        Account admin = CurrentUserSingleton.getInstance().getCurrentUser();

    }

}