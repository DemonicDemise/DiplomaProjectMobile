package com.example.diploma.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diploma.R;
import com.example.diploma.adapters.NavCategoryDetailAdapter;
import com.example.diploma.models.NavCategoryDetailModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NavCategoryDetailModel> list;
    private NavCategoryDetailAdapter adapter;
    private FirebaseFirestore mDb;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        progressBar = findViewById(R.id.nav_category_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mDb = FirebaseFirestore.getInstance();

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.nav_category_detail);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);

        //Getting vegetable
        if(type != null && type.equalsIgnoreCase("vegetable")){
            mDb.collection("NavCategoryDetailed").whereEqualTo("type","vegetable")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailModel navCategoryDetailModel = documentSnapshot.toObject(NavCategoryDetailModel.class);
                        list.add(navCategoryDetailModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //Getting breakfast
        if(type != null && type.equalsIgnoreCase("breakfast")){
            mDb.collection("NavCategoryDetailed").whereEqualTo("type","breakfast")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailModel navCategoryDetailModel = documentSnapshot.toObject(NavCategoryDetailModel.class);
                        list.add(navCategoryDetailModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
