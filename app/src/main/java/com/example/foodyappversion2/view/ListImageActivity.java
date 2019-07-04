package com.example.foodyappversion2.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterRecycleviewListHinh;
import com.example.foodyappversion2.model.HinhDuocChonModel;

import java.util.ArrayList;
import java.util.List;

public class ListImageActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int RESULT_CODE = 321;
    public final static String RESULT_DATA = "DATA";
    RecyclerView recyclerView;
    AdapterRecycleviewListHinh adapterRecycleviewListHinh;
    ArrayList<HinhDuocChonModel> hinhDuocChonModelArrayList = new ArrayList<>();
    TextView txtXong;
    Cursor cursor;
    int visibleThreadhold = 14;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listhinh);
        recyclerView = findViewById(R.id.recycle_view);
        txtXong = findViewById(R.id.txtXong);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        txtXong.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
        else
        {
            setAllImageToRecycleView();
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(hinhDuocChonModelArrayList.size() - 1 == gridLayoutManager.findLastCompletelyVisibleItemPosition())
                {
                    loadMoreLinkHinhs();
                    adapterRecycleviewListHinh.notifyDataSetChanged();
                }
            }
        });

    }

    private void loadMoreLinkHinhs() {
        int icount = 0;
        while(cursor.isAfterLast() == false && icount < visibleThreadhold)
        {
            hinhDuocChonModelArrayList.add(new HinhDuocChonModel(cursor.getString(1)));
            icount++;
            cursor.moveToPrevious();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123 && grantResults.length>0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                setAllImageToRecycleView();
            }
        }
    }
    private void setAllImageToRecycleView()
    {
        loadFirstLinkHinhs();
        adapterRecycleviewListHinh = new AdapterRecycleviewListHinh(hinhDuocChonModelArrayList,this);
        recyclerView.setAdapter(adapterRecycleviewListHinh);
    }
    private void loadFirstLinkHinhs() {
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns._ID ,MediaStore.MediaColumns.DATA};
        cursor = getContentResolver().query(uri, projection, null,
                null, null);
        cursor.moveToLast();
        loadMoreLinkHinhs();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        ArrayList<String> arr = new ArrayList<>();
        for(int i = 0; i < hinhDuocChonModelArrayList.size();i++)
        {
            if(hinhDuocChonModelArrayList.get(i).isCheck())
                arr.add(hinhDuocChonModelArrayList.get(i).getLinhHinh());
        }

        intent.putStringArrayListExtra(RESULT_DATA,  arr);
        setResult(RESULT_CODE,intent);
        finish();
    }
}
