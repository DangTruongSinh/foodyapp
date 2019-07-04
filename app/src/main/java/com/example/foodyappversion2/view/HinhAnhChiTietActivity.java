package com.example.foodyappversion2.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterViewPagerHAChiTiet;

public class HinhAnhChiTietActivity extends AppCompatActivity {
    public static final String INTENT_LIST = "list";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hinhanhchitiet);
        String arr[] = getIntent().getStringArrayExtra(INTENT_LIST);
        ViewPager viewPager = findViewById(R.id.view_papger);
        AdapterViewPagerHAChiTiet viewPagerHAChiTiet = new AdapterViewPagerHAChiTiet(getSupportFragmentManager(),arr);
        viewPager.setAdapter(viewPagerHAChiTiet);

    }
}
