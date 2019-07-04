package com.example.foodyappversion2.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.foodyappversion2.control.Interface.PlaceInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class QuanAnModel implements Parcelable {
    long giamin,giamax;
    boolean giaohang;
    String giodongcua;
    String giomocua;
    long luotthich;
    String tenquanan;
    List<String> tienich;
    String videogioithieu;
    private int vitriQuanAnGanNhat;
    private List<Bitmap> bitmapList;
    private int soHinhAnhOfBinhLuan;
    private List<String> hinhanhlist;
    private String maQuanAn;
    private List<BinhLuanModel> binhLuanModels;
    private DatabaseReference databaseReference;
    private List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    private  DataSnapshot dataSnapshotOld;
    private static int soluongQuanAn = 0;
    public QuanAnModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    protected QuanAnModel(Parcel in) {
        giamin = in.readLong();
        giamax = in.readLong();
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        luotthich = in.readLong();
        tenquanan = in.readString();
        tienich = in.createStringArrayList();
        videogioithieu = in.readString();
        vitriQuanAnGanNhat = in.readInt();
        soHinhAnhOfBinhLuan = in.readInt();
        hinhanhlist = in.createStringArrayList();
        maQuanAn = in.readString();
        binhLuanModels = in.createTypedArrayList(BinhLuanModel.CREATOR);
        chiNhanhQuanAnModelList = in.createTypedArrayList(ChiNhanhQuanAnModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public long getGiamin() {
        return giamin;
    }

    public void setGiamin(long giamin) {
        this.giamin = giamin;
    }

    public long getGiamax() {
        return giamax;
    }

    public void setGiamax(long giamax) {
        this.giamax = giamax;
    }

    public int getSoHinhAnhOfBinhLuan() {
        return soHinhAnhOfBinhLuan;
    }

    public void setSoHinhAnhOfBinhLuan(int soHinhAnhOfBinhLuan) {
        this.soHinhAnhOfBinhLuan = soHinhAnhOfBinhLuan;
    }

    public int getVitriQuanAnGanNhat() {
        return vitriQuanAnGanNhat;
    }

    public void setVitriQuanAnGanNhat(int vitriQuanAnGanNhat) {
        this.vitriQuanAnGanNhat = vitriQuanAnGanNhat;
    }

    public String getMaQuanAn() {
        return maQuanAn;
    }

    public void setMaQuanAn(String maQuanAn) {
        this.maQuanAn = maQuanAn;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public void setBinhLuanModels(List<BinhLuanModel> binhLuanModels) {
        this.binhLuanModels = binhLuanModels;
    }

    public List<BinhLuanModel> getBinhLuanModels() {
        return binhLuanModels;
    }

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<String> getHinhanhlist() {
        return hinhanhlist;
    }

    public void setHinhanhlist(List<String> hinhanhlist) {
        this.hinhanhlist = hinhanhlist;
    }



    public int getSoluongQuanAn() {
        return soluongQuanAn;
    }

    public void setSoluongQuanAn(int soluongQuanAn) {
        this.soluongQuanAn = soluongQuanAn;
    }

    public void getDataQuanAn(final PlaceInterface placeInterface, final ProgressBar progressBar, final int vitriHienTai, final int vitriLast)
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dataSnapshotOld = dataSnapshot;
                    for(DataSnapshot x : dataSnapshot.child("quanans").getChildren())
                        QuanAnModel.soluongQuanAn += 1;
                    loadmore(dataSnapshot,vitriHienTai,vitriLast,placeInterface);


                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if(dataSnapshotOld == null)
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        else
            loadmore(dataSnapshotOld,vitriHienTai,vitriLast,placeInterface);
    }

    private void loadmore(DataSnapshot dataSnapshot, int vitriHienTai, int vitriLast, PlaceInterface placeInterface) {
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
        DataSnapshot dataSnapshotHinhAnh = dataSnapshot.child("hinhanhquanans");
        DataSnapshot dataSnapshotBinhLuan = dataSnapshot.child("binhluans");
        DataSnapshot dataSnapshotHinhAnhOfBinhLuan = dataSnapshot.child("hinhanhbinhluans");
        DataSnapshot dataSnapshotThanhVien = dataSnapshot.child("thanhviens");
        DataSnapshot dataSnapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans");

        int i = 0;
        for(DataSnapshot valueQuanAn: dataSnapshotQuanAn.getChildren())
        {
            if( i == vitriLast)
                break;
            else if(i < vitriHienTai)
            {
                i++;
                continue;
            }
            i++;
            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            ArrayList<String> hinh = new ArrayList<>();
            quanAnModel.setMaQuanAn(valueQuanAn.getKey());
            // get hinh anh quan an
            for(DataSnapshot hinhanh: dataSnapshotHinhAnh.child(valueQuanAn.getKey()).getChildren())
            {
                hinh.add(hinhanh.getValue(String.class));
            }
            quanAnModel.setHinhanhlist(hinh);
            // get tung binh luan
            List<BinhLuanModel> binhLuanModels1 = new ArrayList<>();

            for(DataSnapshot binhluan :dataSnapshotBinhLuan.child(valueQuanAn.getKey()).getChildren())
            {
                // get hinh anh cua tung binh luan
                List<String> hinhanhOfBinhLuan = new ArrayList<>();
                for(DataSnapshot hinhanh : dataSnapshotHinhAnhOfBinhLuan.child(binhluan.getKey()).getChildren())
                {
                    hinhanhOfBinhLuan.add(hinhanh.getValue().toString());
                }
                quanAnModel.soHinhAnhOfBinhLuan = quanAnModel.soHinhAnhOfBinhLuan + hinhanhOfBinhLuan.size();
                BinhLuanModel binhLuanModel = binhluan.getValue(BinhLuanModel.class);

                ThanhVienModel thanhVienModel = dataSnapshotThanhVien.child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);
                binhLuanModel.setHinhanhBinhLuans(hinhanhOfBinhLuan);
                binhLuanModels1.add(binhLuanModel);
            }
            //get all branch storage
            List<ChiNhanhQuanAnModel> listChiNhanhQuanAn = new ArrayList<>();
            for(DataSnapshot chinhanh : dataSnapshotChiNhanhQuanAn.child(valueQuanAn.getKey()).getChildren())
            {
                ChiNhanhQuanAnModel x = chinhanh.getValue(ChiNhanhQuanAnModel.class);
                x.setMaquanan(chinhanh.getKey());
                listChiNhanhQuanAn.add(x);
            }
            quanAnModel.setChiNhanhQuanAnModelList(listChiNhanhQuanAn);
            quanAnModel.setBinhLuanModels(binhLuanModels1);
            placeInterface.setData(quanAnModel);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(giamin);
        parcel.writeLong(giamax);
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeLong(luotthich);
        parcel.writeString(tenquanan);
        parcel.writeStringList(tienich);
        parcel.writeString(videogioithieu);
        parcel.writeInt(vitriQuanAnGanNhat);
        parcel.writeInt(soHinhAnhOfBinhLuan);
        parcel.writeStringList(hinhanhlist);
        parcel.writeString(maQuanAn);
        parcel.writeTypedList(binhLuanModels);
        parcel.writeTypedList(chiNhanhQuanAnModelList);
    }
}
