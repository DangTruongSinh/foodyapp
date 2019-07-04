package com.example.foodyappversion2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.model.BinhLuanModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecycleViewChiTietQA extends RecyclerView.Adapter<AdapterRecycleViewChiTietQA.ViewHolder> {
    List<BinhLuanModel>arrayListBinhLuan;
    Context context;

    public AdapterRecycleViewChiTietQA(List<BinhLuanModel> arrayList, Context context) {
        arrayListBinhLuan = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterRecycleViewChiTietQA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_chitietquanan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecycleViewChiTietQA.ViewHolder holder, int position) {
        Log.d("Tag",position+"");
        final BinhLuanModel binhLuanModel = arrayListBinhLuan.get(position);
        Bitmap bitmap = binhLuanModel.getThanhVienModel().getBitmapHinhAnhUser();
        if(bitmap != null)
            holder.circleImageView.setImageBitmap(bitmap);
        holder.txtTieuDe.setText(binhLuanModel.getTieude());
        holder.txtNoiDung.setText(binhLuanModel.getNoidung());
        holder.txtDiem.setText(binhLuanModel.getChamdiem()+"");
        AdapterRecycleViewHinhBinhLuan adapterRecycleViewHinhBinhLuan = new AdapterRecycleViewHinhBinhLuan(binhLuanModel,context);
        holder.recyclerView.setAdapter(adapterRecycleViewHinhBinhLuan);
    }

    @Override
    public int getItemCount() {
        return arrayListBinhLuan == null ? 0 : arrayListBinhLuan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieuDe;
        TextView txtNoiDung;
        TextView txtDiem;
        RecyclerView recyclerView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circle_user);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDeBinhLuanUser);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDungBinhLuanUser);
            txtDiem = itemView.findViewById(R.id.txtDiemBinhLuanUser);
            recyclerView = itemView.findViewById(R.id.recycle_viewHinhBinhLuan);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}
