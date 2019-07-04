package com.example.foodyappversion2.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodyappversion2.control.Interface.ITienIch;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TienIchModel {
    String tenHinh;
    String tenTienIch;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    public TienIchModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("quanlytienichs");
        storageReference = FirebaseStorage.getInstance().getReference().child("hinhtienich");
    }

    public TienIchModel(String keyTienIch, String tenHinh, String tenTienIch) {

        this.tenHinh = tenHinh;
        this.tenTienIch = tenTienIch;
    }


    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public String getTenTienIch() {
        return tenTienIch;
    }

    public void setTenTienIch(String tenTienIch) {
        this.tenTienIch = tenTienIch;
    }
    public void getHinhAnhTienIch(List<String> linkHinhs, final ITienIch iTienIch)
    {

        for(String hinh : linkHinhs)
        {
            databaseReference.child(hinh).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String hinh = (String) dataSnapshot.child("hinhtienich").getValue();
                    long ONE_MEGABYTE = 1024*1024;
                    storageReference.child(hinh).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            iTienIch.setHinhAnh(bitmap);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
