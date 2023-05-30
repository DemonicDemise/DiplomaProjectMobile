package com.example.diploma.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.activities.ViewAllActivity;
import com.example.diploma.models.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context context;
    private List<RecommendedModel> recList;


    public RecommendedAdapter(Context context, List<RecommendedModel> recList) {
        this.context = context;
        this.recList = recList;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(recList.get(position).getImg_url()).into(holder.recImg);
        holder.name.setText(recList.get(position).getName());
        holder.description.setText(recList.get(position).getDescription());
        holder.rating.setText(recList.get(position).getRating());
        holder.ratingBar.setRating((int)Double.parseDouble(recList.get(position).getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type", recList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView recImg;
        private TextView name, description, rating;

        private RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recImg = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_desc);
            rating = itemView.findViewById(R.id.rec_rating);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}
