package com.example.addhayon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.addhayon.Models.CategoryModel;
import com.example.addhayon.Models.ProfileImageModel;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBQurey {

    public static String userId, pUrl;
    public static Bitmap bitmap;
    private static StorageReference storageReference;
    public static FirebaseFirestore g_firestore;
    public static List<CategoryModel> g_catList = new ArrayList<>();
    public static int g_selected_cat_index = 0;
    public static List<TestModel> g_testList = new ArrayList<>();
    public static List<RankModel> g_usersList = new ArrayList<>();
    public static List<String> g_bmIdList = new ArrayList<>();
    public static List<QuestionModel> g_bookmarkList = new ArrayList<>();
    public static int g_userCount = 0;
    public static boolean isMeOnTopList = false;
    public static int g_selected_test_index = 0;
    public static  List<QuestionModel> g_quesList = new ArrayList<>();
    public static ProfileModel myProfile = new ProfileModel("Addhayon", "null","null","null","null","null","null",0,"null","null", "null","null","null","null","null","null","null","null","null");

    public static ProfileImageModel myImage = new ProfileImageModel("p","c");
    public static RankModel myPerformance = new RankModel("NULL",0,-1,"NULL","NULL","NULL","NULL","NULL","NULL","NULL","NULL","NULL");
    public static final int NOT_VIDITED = 0;
    public static final int UNANSWERED = 1;
    public static final int ANSWERED = 2;
    public static final int REVIEW = 3;
    static int temp;




    //------------------------Data set-----------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createUserData(String email, String name, final MyCompleteListener completeListener){
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("EMAIL_ID",email);
        userData.put("NAME",name);
        userData.put("TOTAL_SCORE", 0);
        userData.put("BIO", "");
        userData.put("ADDRESS", "");
        userData.put("DATE_OF_BIRTH", "");
        userData.put("SCL_CLG", "");
        userData.put("PHONE", "");
        userData.put("BOOKMARKS",0);
        userData.put("PROFILE_IMG","");
        userData.put("COVER_IMG","");
        userData.put("LANGUAGE","");
        userData.put("ID","");



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
    public  static void loadMyScore(MyCompleteListener completeListener){
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_SCORE")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        for (int i=0; i<g_testList.size(); i++){
                            int top = 0;
                            if(documentSnapshot.get(g_testList.get(i).getTextID()) != null){
                                top = documentSnapshot.getLong(g_testList.get(i).getTextID()).intValue();
                            }
                            g_testList.get(i).setTopScore(top);
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

    //------------------------Save Result-----------------------------
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void saveResult(int score, MyCompleteListener completeListener){
            WriteBatch batch = g_firestore.batch();

            Map<String , Object> bmData = new ArrayMap<>();
            for(int i=0; i<g_bmIdList.size();i++){
                bmData.put("BM" + String.valueOf(i+1)+ "_ID",g_bmIdList.get(i));
            }
        DocumentReference bmDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("BOOKMARKS");
        batch.set(bmDoc, bmData);


            DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid());
            Map<String, Object> userData = new ArrayMap<>();
            userData.put("TOTAL_SCORE",score);
            userData.put("BOOKMARKS",g_bmIdList.size());
            batch.update(userDoc, userData);


            if(score > g_testList.get(g_selected_test_index).getTopScore()){
                DocumentReference scoreDoc = userDoc.collection("USER_DATA").document("MY_SCORE");
                Map<String , Object> testData = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    testData = new ArrayMap<>();
                    testData.put(g_testList.get(g_selected_test_index).getTextID(),score);
                    batch.set(scoreDoc,testData, SetOptions.merge());

                }


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
        g_quesList.clear();
        g_firestore.collection("Questions")
                .whereEqualTo("CATEGORY", g_catList.get(g_selected_cat_index).getDocID())
                .whereEqualTo("TEST",g_testList.get(g_selected_test_index).getTextID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots){
                            boolean isBookMark = false;
                            if(g_bmIdList.contains(doc.getId())){
                                isBookMark = true;
                            }
                            g_quesList.add(new QuestionModel(
                                    doc.getId(),
                                    doc.getString("QUESTION"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue(),
                                    -1,
                                    NOT_VIDITED,
                                    isBookMark

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
                        myProfile.setBio(documentSnapshot.getString("BIO"));
                        myProfile.setAddress(documentSnapshot.getString("ADDRESS"));
                        myProfile.setDateofBirth(documentSnapshot.getString("DATE_OF_BIRTH"));
                        myProfile.setSclClg(documentSnapshot.getString("SCL_CLG"));
                        myProfile.setPhn(documentSnapshot.getString("PHONE"));
                        myProfile.setProfileImg(documentSnapshot.getString("PROFILE_IMG"));
                        myProfile.setCoverImg(documentSnapshot.getString("COVER_IMG"));
                        myProfile.setLanguage(documentSnapshot.getString("LANGUAGE"));
                        myProfile.setId(documentSnapshot.getString("ID"));
                        g_firestore.collection("EVENTS").document("EVENT")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        myProfile.setEvent(documentSnapshot.getString("DAYCHECK"));
                                        myProfile.setImg1(documentSnapshot.getString("IMG1"));
                                        myProfile.setImg2(documentSnapshot.getString("IMG2"));
                                        myProfile.setImg3(documentSnapshot.getString("IMG3"));
                                        myProfile.setImg4(documentSnapshot.getString("IMG4"));
                                        myProfile.setImg5(documentSnapshot.getString("IMG5"));
                                        myProfile.setBg(documentSnapshot.getString("BG"));
                                    }
                                });

                        if(documentSnapshot.get("BOOKMARKS") != null){
                            myProfile.setBookmarksCount(documentSnapshot.getLong("BOOKMARKS").intValue());
                        }
                        myPerformance.setScore(documentSnapshot.getLong("TOTAL_SCORE").intValue());
                        myPerformance.setName(documentSnapshot.getString("NAME"));
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

    //-------------Profile Image load--------------------------------
    public static void getProfileImage(final MyCompleteListener completeListener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("allUsers/"+userId+"/image_profile.jpg");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                myImage.setProfileImg(url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                completeListener.onFailure();
            }
        });
    }
    public static void loadData(MyCompleteListener completeListener){
        loadCategories(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                getUserData(new MyCompleteListener(){
                    @Override
                    public void onSuccess(){
                        getUserCount(new MyCompleteListener(){
                            @Override
                            public void onSuccess(){
                                loadBmIds(completeListener);
                            }
                            @Override
                            public void onFailure(){
                                completeListener.onFailure();
                            }

                            });
                    }
                    @Override
                    public void onFailure(){

                        completeListener.onFailure();
                    }
                });
            }
            @Override
            public void onFailure(){
                completeListener.onFailure();

            }
        });
    }


    //---------------------------------
    public static void getTopUsers(MyCompleteListener completeListener){
        String myUserID = FirebaseAuth.getInstance().getUid();
        g_usersList.clear();
        g_firestore.collection("USERS")
                .whereGreaterThan("TOTAL_SCORE",0)
                .orderBy("TOTAL_SCORE", Query.Direction.DESCENDING)
                .limit(100)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int rank = 1;
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            g_usersList.add(new RankModel(
                                   doc.getString("NAME"),
                                    doc.getLong("TOTAL_SCORE").intValue(),
                                    rank,
                                    doc.getString("PROFILE_IMG"),
                                    doc.getString("ID"),
                                    doc.getString("COVER_IMG"),
                                    doc.getString("BIO"),
                                    doc.getString("SCL_CLG"),
                                    doc.getString("ADDRESS"),
                                    doc.getString("DATE_OF_BIRTH"),
                                    doc.getString("EMAIL_ID"),
                                    doc.getString("PHONE")
                            ));
                            if(myUserID.compareTo(doc.getId() ) == 0){
                                isMeOnTopList = true;
                                myPerformance.setRank(rank);
                            }
                            rank++;
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

    public static void getUserCount(MyCompleteListener completeListener){
        g_firestore.collection("USERS").document("TOTAL_USERS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        g_userCount = documentSnapshot.getLong("COUNT").intValue();
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

    public static void loadBmIds(MyCompleteListener completeListener){
        g_bmIdList.clear();
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("BOOKMARKS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int count = myProfile.getBookmarksCount();
                        for(int i =0; i<count; i++){
                            String bmID = documentSnapshot.getString("BM" + String.valueOf(i+1)+"_ID");
                            g_bmIdList.add(bmID);
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


    public static void loadBookmarks(MyCompleteListener completeListener){
        g_bookmarkList.clear();
       temp = 0;
       if(g_bmIdList.size() == 0){
           completeListener.onSuccess();
       }
        for(int i=0; i<g_bmIdList.size(); i++){
            String docID = g_bmIdList.get(i);
            g_firestore.collection("Questions").document(docID)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                g_bookmarkList.add(new QuestionModel(
                                        documentSnapshot.getId(),
                                        documentSnapshot.getString("QUESTION"),
                                        documentSnapshot.getString("A"),
                                        documentSnapshot.getString("B"),
                                        documentSnapshot.getString("C"),
                                        documentSnapshot.getString("D"),
                                        documentSnapshot.getLong("ANSWER").intValue(),
                                        0,
                                        -1,
                                        false

                                ));
                            }
                            temp++;
                            if(temp ==g_bmIdList.size()){
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

    }


}
