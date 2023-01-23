package com.example.addhayon;

import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBQurey {
    public static FirebaseFirestore g_firestore;
    public static List<CategoryModel> g_catList = new ArrayList<>();


    //------------------------Data set-----------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createUserData(String email, String name, final MyCompleteListener completeListener){
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("EMAIL_ID",email);
        userData.put("NAME",name);
        userData.put("TOTAL_SCORE", 0);

        DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        WriteBatch batch = g_firestore.batch();

        batch.set(userDoc,userData);

        DocumentReference countDoc = g_firestore.collection("USERS").document("TOTAL_USERS");
        batch.update(countDoc,"COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        completeListener.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }


    //--------------------load Categories from firebase-------------------
    public static void loadCategories(final MyCompleteListener completeListener){
        g_catList.clear();
        g_firestore.collection("QUIZ").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            docList.put(doc.getId(),doc);
                        }
                        QueryDocumentSnapshot catListDooc = docList.get("Categories");
                        long catCount = catListDooc.getLong("Count");
                        for(int i=1; i<=catCount; i++){
                            String catID =catListDooc.getString("CAT"+String.valueOf(i) + "_ID");
                            QueryDocumentSnapshot catDoc = docList.get(catID);
                            int noOfTest = catDoc.getLong("NO_OF_TESTS").intValue();

                            String catName = catDoc.getString("NAME");
                            g_catList.add(new CategoryModel(catID, catName, noOfTest));
                        }
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });

    }

}
