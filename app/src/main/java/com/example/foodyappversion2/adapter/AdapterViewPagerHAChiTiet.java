package com.example.foodyappversion2.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.foodyappversion2.view.fragment.FragmentHinhAnh;


public class AdapterViewPagerHAChiTiet extends FragmentPagerAdapter {
    String arrHinhs[];
    public static final String tenHinh = "hinh";
    public AdapterViewPagerHAChiTiet(FragmentManager fm,String arr[]) {
        super(fm);
        arrHinhs = arr;
    }

    @Override
    public Fragment getItem(int position) {
        String hinh = arrHinhs[position];
        Bundle bundle = new Bundle();
        bundle.putString(tenHinh,hinh);
        FragmentHinhAnh fragmentHinhAnh = new FragmentHinhAnh();
        fragmentHinhAnh.setArguments(bundle);
        return fragmentHinhAnh;
    }

    @Override
    public int getCount() {
        return arrHinhs.length;
    }
}
