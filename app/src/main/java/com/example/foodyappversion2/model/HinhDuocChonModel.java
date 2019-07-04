package com.example.foodyappversion2.model;

public class HinhDuocChonModel {
    String linhHinh;
    boolean isCheck = false;

    public HinhDuocChonModel(String linhHinh) {
        this.linhHinh = linhHinh;
    }

    public String getLinhHinh() {
        return linhHinh;
    }

    public void setLinhHinh(String linhHinh) {
        this.linhHinh = linhHinh;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
