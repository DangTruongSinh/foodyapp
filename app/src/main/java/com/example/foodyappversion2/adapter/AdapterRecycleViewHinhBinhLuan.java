package com.example.foodyappversion2.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.model.BinhLuanModel;
import com.example.foodyappversion2.view.HinhAnhChiTietActivity;

import java.util.List;

public class AdapterRecycleViewHinhBinhLuan extends RecyclerView.Adapter<AdapterRecycleViewHinhBinhLuan.ViewHolder> {
    BinhLuanModel binhLuanModel;
    Context context;
    public AdapterRecycleViewHinhBinhLuan(BinhLuanModel binhLuanModel,Context context) {
        this.binhLuanModel = binhLuanModel;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecycleViewHinhBinhLuan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hinhanh_binhluan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleViewHinhBinhLuan.ViewHolder holder, int position) {
        if(binhLuanModel.getBitmapBinhLuans() != null)
        {
            Bitmap bitmap = binhLuanModel.getBitmapBinhLuans().get(position);
            holder.imgHinhBinhLuan.setImageBitmap(bitmap);
            int soluong = binhLuanModel.getHinhanhBinhLuans().size();
            if(position == 3)
            {
                holder.txtSoHinhConLai.setVisibility(View.VISIBLE);
                holder.txtSoHinhConLai.setText("+"+(soluong - 4));
            }
            holder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HinhAnhChiTietActivity.class);
                    List<String> listHinhAnhBLs = binhLuanModel.getHinhanhBinhLuans();
                    String arr[] = new String[listHinhAnhBLs.size()];
                    listHinhAnhBLs.toArray(arr);
                    intent.putExtra(HinhAnhChiTietActivity.INTENT_LIST,arr);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int max = 4;
        int soluong =  binhLuanModel.getHinhanhBinhLuans().size();
        if(soluong < 4)
            max  = soluong;
        return binhLuanModel == null ? 0:max;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhConLai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhBinhLuan = itemView.findViewById(R.id.imgBinhLuan);
            txtSoHinhConLai = itemView.findViewById(R.id.txtSoLuongImgConLai);
        }
    }
}
