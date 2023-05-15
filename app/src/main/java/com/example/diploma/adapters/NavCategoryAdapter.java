package com.example.diploma.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.activities.NavCategoryActivity;
import com.example.diploma.models.NavCategoryModel;
import com.example.diploma.ui.category.CategoryFragment;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

    private Context context;
    List<NavCategoryModel> navCategoryModelsList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelsList) {
        this.context = context;
        this.navCategoryModelsList = navCategoryModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryModelsList.get(position).getImg_url()).into(holder.catImageView);
        holder.name.setText(navCategoryModelsList.get(position).getName());
        holder.discount.setText(navCategoryModelsList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type", navCategoryModelsList.get(position).getType());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle args = new Bundle();
                args.putString("type", navCategoryModelsList.get(position).getType());
                categoryFragment.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return navCategoryModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImageView;
        TextView name, discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImageView = itemView.findViewById(R.id.cat_nav_img);
            name = itemView.findViewById(R.id.nav_cat_name);
            discount = itemView.findViewById(R.id.nav_cat_discount);
        }
    }
}
