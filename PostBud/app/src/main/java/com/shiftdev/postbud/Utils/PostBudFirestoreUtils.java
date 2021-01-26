package com.shiftdev.postbud.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shiftdev.postbud.R;

/**
 * Utility to upload data from PostBud to the connected Firestore database.
 */
public class PostBudFirestoreUtils {

    // PARCELS

    /**
     * Get from firestore a parcel by the documentId.
     *
     * @param context  Activity context to retrieve the resources.
     * @param parcelID the document of the parcel inside the database.
     * @return the task of the parcel request.
     */
    public static Task<DocumentSnapshot> getParcelByDocumentId(Context context, String parcelID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(FirebaseNav.PARCELS.getValue(context))
                .document(parcelID)
                .get()
                .addOnCompleteListener(task -> Log.i(context.getClass().getName(), "SUCCESSFULLY got parcel with id: " + parcelID))
                .addOnFailureListener(e -> Log.e(context.getClass().getName(), e.getLocalizedMessage()));
    }

    /**
     * Update parcel's status to SENT and update the dateSent to the passed date.
     *
     * @param context  Activity context to retrieve the resources.
     * @param parcelID the document of the parcel inside the database.
     * @param date     the date the parcel was sent.
     */
    public static void sendParcel(Context context, String parcelID, Timestamp date) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference parcelDocument = db.collection(FirebaseNav.PARCELS.getValue(context))
                .document(parcelID);

        parcelDocument.update(FirebaseNav.DATE_SENT.getValue(context), date)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Successfully updated parcel's shipped date to " + date, Toast.LENGTH_LONG).show();
                        parcelDocument.update(FirebaseNav.STATUS.getValue(context), context.getResources().getString(R.array.status_array))     // TODO: Check with Daniel about statuses.
                                .addOnCompleteListener(statusUpdateRequest -> {
                                    if (statusUpdateRequest.isSuccessful())
                                        Toast.makeText(context, "Successfully updated parcel's status to SENT", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(context, "Failed to updated parcel's status to sent" + date, Toast.LENGTH_LONG).show();
                                });
                    } else {
                        Toast.makeText(context, "Failed to updated parcel's status to SENT", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Upload a Parcel object into the firebase database and update it's documentId that is created by Firebase after the upload for an easy reference in the future.
     *
     * @param context The Activity the method is being called from.
     * @param parcel  The Parcel object that is being uploaded to firebase.
     * @return the reference to the document that is being uploaded.
     */
    public static void uploadParcel(Context context, Parcel parcel) {
        CollectionReference ref = FirebaseFirestore.getInstance()
                .collection(FirebaseNav.PARCELS.getValue(context));
        ref.whereEqualTo(FirebaseNav.PARCEL_ID.getValue(context), parcel.getParcelId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            ref.add(parcel)
                                    .addOnSuccessListener(documentReference -> {
                                        parcel.setParcelId(documentReference.getId());
                                        documentReference.update(FirebaseNav.PARCEL_ID.getValue(context), documentReference.getId());
                                    });
                        } else
                            Log.e(context.getClass().getSimpleName(), parcel.getParcelId() + " parcel id is taken or item already exists in the database.");
                    } else {
                        Log.e("TEST PARCEL UPLOAD", "Error getting documents: ", task.getException());
                    }
                });
    }

    /**
     * Upload a new parcel checkpoint of a parcel.
     *
     * @param context    The Activity the method is being called from.
     * @param parcelId   the parcel to add the checkpoint to.
     * @param checkpoint the checkpoint to add to the parcel.
     */
    public static void addParcelCheckpoint(Context context, String parcelId, Parcel.Checkpoint checkpoint) {
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection(FirebaseNav.PARCELS.getValue(context))
                .document(parcelId);

    }

    // ACCOUNTS

    /**
     * Create an account in Firebase using FirebaseAuth.
     *
     * @param context Activity context to retrieve the resources.
     * @param account Account data to store inside the database related to the user.
     * @return the task of new user creation.
     */
    public static Task<AuthResult> createAccountAndUploadToFirestore(Context context, Account account) {
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

    /**
     * Upload the account data to the FirebaseFirestore database to store the related user data.
     *
     * @param context Activity context to retrieve the resources.
     * @param account Account data to store inside the database related to the user.
     * @param result  The task from createAccountAndUploadToFirestore or any other account creation task to store the related data in the FirebaseFirestore database.
     */
    public static void uploadAccountToFirestore(Context context, Account account, AuthResult result) {
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

    // EMPLOYEES

    /**
     * Retrieve an employee by its user id and return the task of the query.
     *
     * @param context Activity context to retrieve the resources.
     * @param uid     the user id of the employee that is being retrieved.
     * @return the task of the query to get the employee.
     */
    public static Task<DocumentSnapshot> getEmployee(Context context, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(FirebaseNav.EMPLOYEES.getValue(context)).document(uid).get();
    }

    // ADMINISTRATORS

    /**
     * Retrieve an administrator by its user id and return the task of the query.
     *
     * @param context Activity context to retrieve the resources.
     * @param uid     the user id of the administrator that is being retrieved.
     * @returnthe task of the query to get the administrator.
     */
    public static Task<DocumentSnapshot> getAdministrator(Context context, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(FirebaseNav.ADMINISTRATORS.getValue(context)).document(uid).get();
    }


}
