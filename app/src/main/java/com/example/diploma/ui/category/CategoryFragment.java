package com.example.diploma.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diploma.R;
import com.example.diploma.activities.NavCategoryActivity;
import com.example.diploma.adapters.NavCategoryAdapter;
import com.example.diploma.adapters.NavCategoryDetailAdapter;
import com.example.diploma.adapters.UserCartAdapter;
import com.example.diploma.databinding.FragmentCategoryBinding;
import com.example.diploma.models.NavCategoryDetailModel;
import com.example.diploma.models.NavCategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<NavCategoryModel> categoryModelList;

    private List<NavCategoryDetailModel> categoryDetailModelsList;

    private NavCategoryDetailAdapter navCategoryDetailAdapter;
    private NavCategoryAdapter navCategoryAdapter;

    private FirebaseFirestore db;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container,false);
        db = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.cat_rec);
        recyclerView.setVisibility(View.GONE);

        progressBar = root.findViewById(R.id.category_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Category Items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        categoryModelList = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(getActivity(), categoryModelList);
        recyclerView.setAdapter(navCategoryAdapter);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
                                categoryModelList.add(navCategoryModel);
                                navCategoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
//                                Intent intent = new Intent(getContext(), NavCategoryActivity.class);
//                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


//        categoryDetailModelsList = new ArrayList<>();
//        navCategoryDetailAdapter = new NavCategoryDetailAdapter(getActivity(), categoryDetailModelsList);
//        recyclerView.setAdapter(navCategoryDetailAdapter);
//
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        return root;
    }
}