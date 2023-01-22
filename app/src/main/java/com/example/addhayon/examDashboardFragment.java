package com.example.addhayon;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class examDashboardFragment extends Fragment {


    public examDashboardFragment() {
        // Required empty public constructor
    }
    private GridView catView;
    private List<CategoryModel> catList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam_dashboard, container, false);

        catView = view.findViewById(R.id.cat_grid);
        loadCategories();
        CategoryAdapter adapter = new CategoryAdapter(catList);
        catView.setAdapter(adapter);

        return view;
    }

    private void loadCategories(){
        catList.clear();
        catList.add(new CategoryModel("1","GK", 20));
        catList.add(new CategoryModel("2","HISTORY", 30));
        catList.add(new CategoryModel("3","ENGLISH", 10));
        catList.add(new CategoryModel("4","MATH", 25));
        catList.add(new CategoryModel("5","ICT", 10));


    }
}