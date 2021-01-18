package com.shiftdev.postbud.Utils;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Utility to upload data from PostBud to the connected Firestore database.
 */
public class PostBudFirestoreUtils {

    /**
     * Get from firestore a parcel by the documentId
     *
     * @param context
     * @param documentId
     * @return
     */
    public static Task<DocumentSnapshot> getParcelByDocumentId(Activity context, String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(FirebaseNav.PARCELS.getValue(context))
                .document(documentId)
                .get()
                .addOnCompleteListener(task -> Log.i(context.getClass().getName(), "SUCCESSFULLY got parcel with id: " + documentId))
                .addOnFailureListener(e -> Log.e(context.getClass().getName(), e.getLocalizedMessage()));
    }

    /**
     * Upload a Parcel object into the firebase database and update it's documentId that is created by Firebase after the upload for an easy reference in the future.
     *
     * @param context The Activity the method is being called from.
     * @param parcel  The Parcel object that is being uploaded to firebase.
     * @return the reference to the document that is being uploaded.
     */
    public static void uploadParcel(Activity context, Parcel parcel) {
        CollectionReference ref = FirebaseFirestore.getInstance().collection(FirebaseNav.PARCELS.getValue(context));
        ref.whereEqualTo(FirebaseNav.PARCEL_ID.getValue(context), parcel.getParcelId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            ref.add(parcel)
                                .addOnSuccessListener(documentReference -> {
                                    parcel.setDocumentId(documentReference.getId());
                                    documentReference.update(FirebaseNav.DOCUMENT_ID.getValue(context), documentReference.getId());
                                });
                        } else
                            Log.e(context.getClass().getSimpleName(), parcel.getParcelId() + " parcel id is taken or item already exists in the database.");
                    } else {
                        Log.e("TEST PARCEL UPLOAD", "Error getting documents: ", task.getException());
                    }
                });
    }

    public static Task<AuthResult> createAccountAndUploadToFirestore(Activity context, Account account) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {
                        account.setUid(authResult.getUser().getUid());
                        uploadAccountToFirestore(context, account, authResult);
                    } else
                        Log.e(context.getClass().getName(), "ERROR: Failed to create user with email and password; authResult is NULL");
                })
                .addOnFailureListener(e -> Log.e(context.getClass().getName(), e.getLocalizedMessage()));
    }

    public static void uploadAccountToFirestore(Activity context, Account account, AuthResult result) {
        if (result.getUser() != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference ref;
            if (account.getClass().equals(Administrator.class))
                ref = db.collection(FirebaseNav.ADMINISTRATORS.getValue(context));
            else
                ref = db.collection(FirebaseNav.EMPLOYEES.getValue(context));

            ref.document(account.getUid()).get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            Log.e(context.getClass().getSimpleName(), "ERROR: Existing user already exists in database.");
                        } else {
                            ref.document(account.getUid()).set(account);
                        }
                    });
        }
    }

}
