package com.example.diploma.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.activities.ViewAllActivity;
import com.example.diploma.models.DiscountModel;
import com.example.diploma.ui.cart.UserCartFragment;
import com.example.diploma.ui.profile.ProfileFragment;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    private List<DiscountModel> list;
    private Context context;

    public DiscountAdapter(Context context,List<DiscountModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_discount_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.percent.setText(list.get(position).getPercent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserCartFragment fragment = new UserCartFragment();
                Bundle args = new Bundle();
                args.putString("name", list.get(position).getPercent());
                args.putString("percent", list.get(position).getPercent());
                fragment.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, percent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.discount_name);
            percent = itemView.findViewById(R.id.discount_percent);
        }
    }
}
