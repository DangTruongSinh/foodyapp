package com.example.foodyappversion2.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterViewPagerHAChiTiet;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FragmentHinhAnh extends Fragment {
    View view;
    ImageView imageView;
    Bitmap bitmap;
    ProgressBar progressBar;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhquanan");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
        {
            view = inflater.inflate(R.layout.layout_fragment_hinhanhchitiet,container,false);
            imageView = view.findViewById(R.id.imgHinhChiTiet);
            progressBar = view.findViewById(R.id.progress_bar);
            String tenHinh = getArguments().getString(AdapterViewPagerHAChiTiet.tenHinh);
            long ONE_MB = 1024*1024;
            storageReference.child(tenHinh).getBytes(ONE_MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        return view;
    }

}
