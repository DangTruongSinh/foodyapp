package com.example.foodyappversion2.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhVienModel implements Parcelable {
    String hinhanh;
    String hoten;
    private String maThanhVien;
    private Bitmap bitmapHinhAnhUser;
    private DatabaseReference databaseReference;
    public ThanhVienModel(String name,String hinhanh,String uid)
    {
        this.hoten = name;
        this.hinhanh = hinhanh;
        this.maThanhVien = uid;
    }
    public ThanhVienModel()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    protected ThanhVienModel(Parcel in) {
        hinhanh = in.readString();
        hoten = in.readString();
        maThanhVien = in.readString();

    }

    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getHoten() {
        return hoten;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public Bitmap getBitmapHinhAnhUser() {
        return bitmapHinhAnhUser;
    }

    public void setBitmapHinhAnhUser(Bitmap bitmapHinhAnhUser) {
        this.bitmapHinhAnhUser = bitmapHinhAnhUser;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public void setThemThanhVienModel(ThanhVienModel thanhVienModel)
    {
        databaseReference.child("thanhviens").child(thanhVienModel.getMaThanhVien()).setValue(thanhVienModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hinhanh);
        parcel.writeString(hoten);
        parcel.writeString(maThanhVien);
    }
}
