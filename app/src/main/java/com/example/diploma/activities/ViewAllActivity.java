package com.example.diploma.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diploma.R;
import com.example.diploma.adapters.ViewAllAdapter;
import com.example.diploma.databinding.ActivityMainBinding;
import com.example.diploma.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private ViewAllAdapter viewAllAdapter;
    private List<ViewAllModel> viewAllModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.view_rec);


        String type = getIntent().getStringExtra("type");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(viewAllModelList, this);
        recyclerView.setAdapter(viewAllAdapter);

        //Getting breakfasts
        if (type != null && type.equalsIgnoreCase("vegetable")) {
            firestore.collection("AllProducts").whereEqualTo("type", "breakfast")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });

        }

//        if(type != null && type.equalsIgnoreCase("phone")){
//            firestore.collection("AllProducts").whereEqualTo("type","phone")
//                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
//                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
//                                viewAllModelList.add(viewAllModel);
//                                viewAllAdapter.notifyDataSetChanged();
//                                recyclerView.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    });
//          }
    }
}
