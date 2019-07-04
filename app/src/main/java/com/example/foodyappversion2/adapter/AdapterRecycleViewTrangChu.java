package com.example.foodyappversion2.adapter;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.model.BinhLuanModel;
import com.example.foodyappversion2.model.QuanAnModel;
import com.example.foodyappversion2.view.ChiTietQuanAnActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecycleViewTrangChu extends RecyclerView.Adapter<AdapterRecycleViewTrangChu.ViewHolder>  {
    ArrayList<QuanAnModel>arrayList;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Context context;
    private Location locationHienTai;
    public AdapterRecycleViewTrangChu(ArrayList<QuanAnModel> anModels, Location location, Context context)
    {
        arrayList = anModels;
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        locationHienTai = location;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterRecycleViewTrangChu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecycleViewTrangChu.ViewHolder holder, final int position) {
            holder.linearBinhLuanPlace1.setVisibility(View.VISIBLE);
            holder.linearBinhLuanPlace2.setVisibility(View.VISIBLE);
            final QuanAnModel quanAnModel = arrayList.get(position);
            holder.txtTieuDePlaces.setText(quanAnModel.getTenquanan());
            //tinh khoang cach lua chon quan an co khoang cach gan nhat
            Location location2 = new Location("");
            double min = Double.MAX_VALUE;
            int vitri = 0;
            for (int i = 0; i < quanAnModel.getChiNhanhQuanAnModelList().size(); i++) {
                location2.setLatitude(quanAnModel.getChiNhanhQuanAnModelList().get(i).getLatitude());
                location2.setLongitude(quanAnModel.getChiNhanhQuanAnModelList().get(i).getLongitude());
                if (min > (locationHienTai.distanceTo(location2) / 1000)) {
                    min = locationHienTai.distanceTo(location2) / 1000;
                    vitri = i;
                }
            }
            quanAnModel.setVitriQuanAnGanNhat(vitri);
            holder.txtDiaChiPlaces.setText(quanAnModel.getChiNhanhQuanAnModelList().get(vitri).getDiachi());
            holder.txtSoKm.setText(String.format("%.1f", min) + "km");
            // set hiinh anh quan an
            if (quanAnModel.getBitmapList().size() > 0)
                holder.imagePlaces.setImageBitmap(quanAnModel.getBitmapList().get(0));
            if (quanAnModel.getBinhLuanModels().size() > 0) {
                BinhLuanModel binhLuanModel1 = quanAnModel.getBinhLuanModels().get(0);
                BinhLuanModel binhLuanModel2;
                holder.txtTieuDeBinhLuanPlace1.setText(binhLuanModel1.getTieude());
                holder.txtNoiDungBinhLuanPlace1.setText(binhLuanModel1.getNoidung());
                holder.txtDiemBinhLuanPlace1.setText(String.format("%.1f", binhLuanModel1.getChamdiem()) + " diem");
                holder.imgUser1.setImageBitmap(quanAnModel.getBinhLuanModels().get(0).getThanhVienModel().getBitmapHinhAnhUser());

                if (quanAnModel.getBinhLuanModels().size() >= 2) {
                    binhLuanModel2 = quanAnModel.getBinhLuanModels().get(1);
                    holder.txtTieuDeBinhLuanPlace2.setText(binhLuanModel2.getTieude());
                    holder.txtNoiDungBinhLuanPlace2.setText(binhLuanModel2.getNoidung());
                    holder.txtDiemBinhLuanPlace2.setText(String.format("%.1f", binhLuanModel2.getChamdiem()) + " diem");
                    // load hinh user 2
                    holder.imgUser2.setImageBitmap(quanAnModel.getBinhLuanModels().get(1).getThanhVienModel().getBitmapHinhAnhUser());
                }
                double diemtrungbinh = 0;
                for (BinhLuanModel x : quanAnModel.getBinhLuanModels()) {
                    diemtrungbinh += x.getChamdiem();
                }
                diemtrungbinh /= quanAnModel.getBinhLuanModels().size();
                holder.txtDiemTrungBinhPlace.setText(String.format("%.1f", diemtrungbinh));
            } else {
                holder.linearBinhLuanPlace1.setVisibility(View.GONE);
                holder.linearBinhLuanPlace2.setVisibility(View.GONE);
            }
            holder.txtSoCommentPlace.setText(quanAnModel.getBinhLuanModels().size() + "");
            holder.txtSoHinhAnhBinhLuan.setText(quanAnModel.getSoHinhAnhOfBinhLuan() + "");
            if (!quanAnModel.isGiaohang())
                holder.btnGiaoHang.setVisibility(View.GONE);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietQuanAnActivity.class);
                    intent.putExtra(ChiTietQuanAnActivity.INTENT_QUANAN,quanAnModel);
                    context.startActivity(intent);

                }
            });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDePlaces;
        TextView txtDiaChiPlaces;
        TextView txtSoKm;
        TextView txtTieuDeBinhLuanPlace1,txtTieuDeBinhLuanPlace2;
        TextView txtNoiDungBinhLuanPlace1,txtNoiDungBinhLuanPlace2;
        Button btnGiaoHang;
        ImageView imagePlaces;
        LinearLayout linearBinhLuanPlace1,linearBinhLuanPlace2;
        TextView txtSoCommentPlace,txtSoHinhAnhBinhLuan;
        TextView txtDiemBinhLuanPlace1,txtDiemBinhLuanPlace2;
        TextView txtDiemTrungBinhPlace;
        CircleImageView imgUser1,imgUser2;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDePlaces = itemView.findViewById(R.id.txtTieuDePlaces);
            txtDiaChiPlaces = itemView.findViewById(R.id.txtDiaChiPlaces);
            txtSoKm = itemView.findViewById(R.id.txtSoKmPlace);
            btnGiaoHang = itemView.findViewById(R.id.btnGiaoHang);
            imagePlaces = itemView.findViewById(R.id.image_viewPlaces);
            txtTieuDeBinhLuanPlace1 = itemView.findViewById(R.id.txtTieuDeBinhLuanPlaces1);
            txtTieuDeBinhLuanPlace2 = itemView.findViewById(R.id.txtTieuDeBinhLuanPlaces2);
            txtNoiDungBinhLuanPlace1 = itemView.findViewById(R.id.txtNoiDungBinhLuanPlaces1);
            txtNoiDungBinhLuanPlace2 = itemView.findViewById(R.id.txtNoiDungBinhLuanPlaces2);
            txtSoCommentPlace = itemView.findViewById(R.id.txtSoComment_Place);
            txtSoHinhAnhBinhLuan = itemView.findViewById(R.id.txtSoHinhAnhComment_Places);
            linearBinhLuanPlace1 = itemView.findViewById(R.id.linear_BinhLuanPlace1);
            linearBinhLuanPlace2 = itemView.findViewById(R.id.linear_BinhLuanPlace2);
            txtDiemBinhLuanPlace1 = itemView.findViewById(R.id.txtDiemBinhLuan_Place1);
            txtDiemBinhLuanPlace2 = itemView.findViewById(R.id.txtDiemBinhLuan_Place2);
            txtDiemTrungBinhPlace = itemView.findViewById(R.id.txtDiemTrungBinhPlace);
            imgUser1 = itemView.findViewById(R.id.img_hinhUser1);
            imgUser2 = itemView.findViewById(R.id.img_hinhUser2);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
