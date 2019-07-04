package com.example.foodyappversion2.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.foodyappversion2.view.fragment.FragmentFood;
import com.example.foodyappversion2.view.fragment.FragmentPlaces;


public class AdapterViewPagerTrangChu extends FragmentStatePagerAdapter {
    FragmentFood fragmentFood;
    FragmentPlaces fragmentPlaces;
    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        fragmentFood = new FragmentFood();
        fragmentPlaces = new FragmentPlaces();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return fragmentPlaces;
            case 1:
                return fragmentFood;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
