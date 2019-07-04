package com.example.foodyappversion2.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterRecycleResultSelectedImage;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;



public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtTenQuanAn;
    Button btnLoadImage;
    final int request_code = 123;
    RecyclerView recyclerView;
    EditText edtNoiDung;
    Button btnTakeAPhoto;
    String currentPhotoPath;
    EditText edtTitle;
    ArrayList<Object> arrayList = new ArrayList<>();
    AdapterRecycleResultSelectedImage adapterRecycleResultSelectedImage = new AdapterRecycleResultSelectedImage(arrayList, this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        Toolbar toolbar = findViewById(R.id.toolbar_binhluan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String tenquan = getIntent().getStringExtra("quanan");
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtTenQuanAn.setText(tenquan);
        btnLoadImage = findViewById(R.id.btnLoadImage);
        btnLoadImage.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycle_view);
        edtNoiDung = findViewById(R.id.edtNoiDung);
        btnTakeAPhoto = findViewById(R.id.btnTakeAPhoto);
        edtTitle = findViewById(R.id.edtTitle);
        btnTakeAPhoto.setOnClickListener(this);
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(isOpen)
                    recyclerView.setVisibility(View.GONE);
                else
                    recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ListImageActivity.RESULT_CODE && requestCode == request_code) {

            arrayList.addAll(data.getStringArrayListExtra(ListImageActivity.RESULT_DATA));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            adapterRecycleResultSelectedImage.notifyDataSetChanged();
            recyclerView.setAdapter(adapterRecycleResultSelectedImage);

        } else if (resultCode == RESULT_OK && requestCode == 1) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            arrayList.add(bitmap);
            adapterRecycleResultSelectedImage.notifyDataSetChanged();

        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoadImage:
                Intent intent = new Intent(CommentActivity.this, ListImageActivity.class);
                startActivityForResult(intent, request_code);
                break;
            case R.id.btnTakeAPhoto:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
                break;

        }


    }


}