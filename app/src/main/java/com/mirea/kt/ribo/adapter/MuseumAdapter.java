package com.mirea.kt.ribo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.activities.MainPage;
import com.mirea.kt.ribo.models.Museum;
import com.mirea.kt.ribo.activities.MuseumDetailPage;
import com.mirea.kt.ribo.models.MuseumDetail;

import java.util.ArrayList;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Museum> museums;
    private final RecyclerViewInterface recyclerViewInterface;

    public MuseumAdapter(Context context, ArrayList<Museum> museums, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.museums = museums;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.museums_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Museum museum = museums.get(position);
        holder.museumName.setText(museum.getName());
        holder.museumImage.setImageResource(museum.getImage());

        holder.itemView.setOnClickListener(v -> {
            MuseumDetail museumDetail = ((MainPage) context).getMuseumDetail(museum.getName());

            Intent intent = new Intent(context, MuseumDetailPage.class);
            intent.putExtra("museum_name", museumDetail.getName());
            intent.putExtra("museum_image", museumDetail.getImage());
            intent.putExtra("address", museumDetail.getAddress());
            intent.putExtra("phone", museumDetail.getPhone());
            intent.putExtra("website", museumDetail.getWebsite());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return museums.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView museumName;
        ImageView museumImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            museumName = itemView.findViewById(R.id.name);
            museumImage = itemView.findViewById(R.id.imageView);
        }
    }
}