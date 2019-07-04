package com.example.foodyappversion2.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BinhLuanModel implements Parcelable {
    double chamdiem;
    long luotthich;
    String mauser;
    String noidung;
    String tieude;
    private List<String> hinhanhBinhLuans;
    private List<Bitmap> bitmapBinhLuans;
    private ThanhVienModel thanhVienModel;

    public BinhLuanModel()
    {

    }

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        mauser = in.readString();
        noidung = in.readString();
        tieude = in.readString();
        hinhanhBinhLuans = in.createStringArrayList();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    @Override
    public String toString() {
        return  " -cham diem : " + chamdiem+" luot thich:" +luotthich + " mauser:" +mauser + " noi dung:"+noidung+" goodbye:"+tieude;
    }

    public List<Bitmap> getBitmapBinhLuans() {
        return bitmapBinhLuans;
    }

    public void setBitmapBinhLuans(List<Bitmap> bitmapBinhLuans) {
        this.bitmapBinhLuans = bitmapBinhLuans;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public List<String> getHinhanhBinhLuans() {
        return hinhanhBinhLuans;
    }

    public void setHinhanhBinhLuans(List<String> hinhanhBinhLuans) {
        this.hinhanhBinhLuans = hinhanhBinhLuans;
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
   public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }


    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(chamdiem);
        parcel.writeLong(luotthich);
        parcel.writeString(mauser);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeStringList(hinhanhBinhLuans);
        parcel.writeParcelable(thanhVienModel, i);
    }
}
