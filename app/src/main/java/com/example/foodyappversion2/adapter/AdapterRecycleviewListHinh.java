package com.example.foodyappversion2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodyappversion2.R;
import com.example.foodyappversion2.model.HinhDuocChonModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecycleviewListHinh extends RecyclerView.Adapter<AdapterRecycleviewListHinh.ViewHolder> {
    Context context;
    List<HinhDuocChonModel> hinhDuocChonModels;
    public AdapterRecycleviewListHinh(ArrayList<HinhDuocChonModel> list,Context context) {
        hinhDuocChonModels = list;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterRecycleviewListHinh.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_listhinh,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleviewListHinh.ViewHolder holder, final int position) {
        String hinh = hinhDuocChonModels.get(position).getLinhHinh();
        File file = new File(hinh);
        Glide.with(context).load(file).placeholder(R.drawable.error_loading).into(holder.imgHinh);
        if(hinhDuocChonModels.get(position).isCheck())
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                HinhDuocChonModel hinhDuocChonModel = hinhDuocChonModels.get(position);
                if(checkBox.isChecked())
                    hinhDuocChonModel.setCheck(true);
                else
                    hinhDuocChonModel.setCheck(false);
            }
        });
    }
    @Override
    public int getItemCount() {
        return hinhDuocChonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.image_hinh);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
