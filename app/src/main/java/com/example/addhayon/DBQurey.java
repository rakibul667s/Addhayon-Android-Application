package com.example.addhayon;

import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.addhayon.Models.CategoryModel;
import com.example.addhayon.Models.ProfileModel;
import com.example.addhayon.Models.QuestionModel;
import com.example.addhayon.Models.RankModel;
import com.example.addhayon.Models.TestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public static int g_selected_cat_index =0;
    public static List<TestModel> g_testList = new ArrayList<>();
    public static int g_selected_test_index = 0;
    public static  List<QuestionModel> g_quesList = new ArrayList<>();
    public static ProfileModel myProfile = new ProfileModel("NA", "null");
    public static RankModel myPerformance = new RankModel(0,-1);

    public static final int NOT_VIDITED = 0;
    public static final int UNANSWERED = 1;
    public static final int ANSWERED = 2;
    public static final int REVIEW = 3;




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

    //------------------------Save Result-----------------------------
    public static void saveResult(int score, MyCompleteListener completeListener){
            WriteBatch batch = g_firestore.batch();
            DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid());
            batch.update(userDoc, "TOTAL_SCORE", score);

            if(score > g_testList.get(g_selected_test_index).getTopScore()){
                DocumentReference scoreDoc = userDoc.collection("USER_DATA").document("MY_SCORE");
                batch.update(scoreDoc,g_testList.get(g_selected_test_index).getTextID(),score);

            }
            batch.commit()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if(score > g_testList.get(g_selected_test_index).getTopScore()){
                                g_testList.get(g_selected_test_index).setTopScore(score);

                                myPerformance.setScore(score);
                                completeListener.onSuccess();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            completeListener.onFailure();

                        }
                    });

    }

    //--------------------load Exam Categories from firebase-------------------
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
                        long catCount = catListDooc.getLong("COUNT");
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

    //--------------------load Test Categories from firebase-------------------
    public static void loadTestData(MyCompleteListener completeListener){
        g_testList.clear();
        g_firestore.collection("QUIZ").document(g_catList.get(g_selected_cat_index).getDocID())
                .collection("TESTS_LIST").document("TESTS_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int noOfTests = g_catList.get(g_selected_cat_index).getNoOfTests();
                        for(int i=1; i<=noOfTests; i++){
                            g_testList.add(new TestModel(
                                    documentSnapshot.getString("TEST"+ String.valueOf(i) + "_ID"),
                                    0,
                                    documentSnapshot.getLong("TEST" + String.valueOf(i)+ "_TIME").intValue()
                            ));
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


    //------------------Load Question-----------------------------------------
    public static void loadquestions(MyCompleteListener completeListener){
        g_firestore.collection("Questions")
                .whereEqualTo("CATEGORY", g_catList.get(g_selected_cat_index).getDocID())
                .whereEqualTo("TEST",g_testList.get(g_selected_test_index).getTextID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots){
                            g_quesList.add(new QuestionModel(
                                    doc.getString("QUESTION"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue(),
                                    -1,
                                    NOT_VIDITED

                            ));
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

    //------------------------Profile data load--------------------------------
    public static void getUserData(final MyCompleteListener completeListener){
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));
                        myPerformance.setScore(documentSnapshot.getLong("TOTAL_SCORE").intValue());
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

    public static void loadData(MyCompleteListener completeListener){
        loadCategories(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                getUserData(completeListener);
            }
            @Override
            public void onFailure(){
                completeListener.onFailure();

            }
        });
    }

}
