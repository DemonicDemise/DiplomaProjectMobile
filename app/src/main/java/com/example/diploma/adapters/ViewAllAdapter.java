package com.example.diploma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    private List<ViewAllModel> viewAllList;
    private Context context;

    public ViewAllAdapter(List<ViewAllModel> viewAllList, Context context) {
        this.viewAllList = viewAllList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(viewAllList.get(position).getImg_url()).into(holder.viewImage);
        holder.name.setText(viewAllList.get(position).getName());
        holder.description.setText(viewAllList.get(position).getDescription());
        holder.rating.setText(viewAllList.get(position).getRating());
        holder.price.setText(viewAllList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return viewAllList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView viewImage;
        private TextView name, description, rating, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImage = itemView.findViewById(R.id.view_all_img);
            name = itemView.findViewById(R.id.view_all_name);
            description = itemView.findViewById(R.id.view_all_description);
            rating = itemView.findViewById(R.id.view_all_rating);
            price = itemView.findViewById(R.id.view_all_price);
        }
    }
}
