package com.example.foodyappversion2.view;

import android.os.Bundle;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterViewPagerTrangChu;
import com.google.firebase.auth.FirebaseAuth;


public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    FirebaseAuth firebaseAuth;
    ViewPager viewPager;
    RadioButton rdPlace;
    RadioButton rdFood;
    RadioGroup rdGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu_activity);
        viewPager = findViewById(R.id.view_papger);
        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        rdPlace = findViewById(R.id.rdb_place);
        rdFood = findViewById(R.id.rdb_food);
        rdGroup = findViewById(R.id.rd_group);
        rdGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(adapterViewPagerTrangChu);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if(i == 0)
            rdPlace.setChecked(true);
        else
            rdFood.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId ==  R.id.rdb_place)
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(1);
    }
}
