package com.example.foodyappversion2.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodyappversion2.R;

import java.io.File;
import java.util.ArrayList;

public class AdapterRecycleResultSelectedImage extends RecyclerView.Adapter<AdapterRecycleResultSelectedImage.ViewHolder> {

    ArrayList<Object> arrayList;
    Context context;
    public AdapterRecycleResultSelectedImage(ArrayList<Object> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecycleResultSelectedImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_result_chonhinh,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleResultSelectedImage.ViewHolder holder, final int position) {
        Glide.with(context).load( arrayList.get(position)).into(holder.imgHinh);
        holder.imgCanCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        ImageView imgCanCel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imageView);
            imgCanCel = itemView.findViewById(R.id.imageView2);
        }
    }
}
