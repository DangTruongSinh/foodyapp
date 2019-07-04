package com.example.foodyappversion2.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.adapter.AdapterRecycleViewTrangChu;
import com.example.foodyappversion2.control.Interface.PlaceInterface;
import com.example.foodyappversion2.model.QuanAnModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ControlerQuanAn {
    public static final int MAX = 5;
    QuanAnModel quanAnModel;
    ArrayList<QuanAnModel> arrayList;
    private int vitriHienTai = 0,vitriTiepTheo=MAX;
    public ControlerQuanAn() {
        quanAnModel = new QuanAnModel();
        arrayList = new ArrayList<>();

    }

    public void getDataQuanAn(RecyclerView recyclerView, Location location, Context context, final ProgressBar progressBar, NestedScrollView nestedScrollView)
    {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        final AdapterRecycleViewTrangChu adapterRecycleViewTrangChu = new AdapterRecycleViewTrangChu(arrayList,location,context);
        recyclerView.setAdapter(adapterRecycleViewTrangChu);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final PlaceInterface placeInterface = new PlaceInterface() {
            @Override
            public void setData(final QuanAnModel quanAnModel) {
                // get hinh anh quan an
                final NotifyDataChange notifyDataChange = new NotifyDataChange();
                final List<Bitmap> bitmapList = new ArrayList<>();
                long ONE_MEGABYTE = 1024 * 1024;
                StorageReference storageHinhAnhQuanAn = storageReference.child("hinhquanan").child(quanAnModel.getHinhanhlist().get(0));
                storageHinhAnhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bitmapList.add(bitmap);
                        quanAnModel.setBitmapList(bitmapList);
                        notifyDataChange.tangValue(quanAnModel,arrayList, adapterRecycleViewTrangChu,progressBar);
                    }
                    });

                // get image of USer1 first
                if(quanAnModel.getBinhLuanModels().size() > 0)
                {
                    // load hinh user 1
                    StorageReference storageHinhUser1 = storageReference.child("hinhanhuser").child(quanAnModel.getBinhLuanModels().get(0).getThanhVienModel().getHinhanh());
                    storageHinhUser1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            quanAnModel.getBinhLuanModels().get(0).getThanhVienModel().setBitmapHinhAnhUser(bitmap);
                            notifyDataChange.tangValue(quanAnModel,arrayList, adapterRecycleViewTrangChu,progressBar);
                        }
                    });
                    if(quanAnModel.getBinhLuanModels().size() >= 2)
                    {
                        // load image user2
                        StorageReference storageHinhUser2 = storageReference.child("hinhanhuser").child(quanAnModel.getBinhLuanModels().get(1).getThanhVienModel().getHinhanh());
                        storageHinhUser2.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                quanAnModel.getBinhLuanModels().get(1).getThanhVienModel().setBitmapHinhAnhUser(bitmap);
                                notifyDataChange.tangValue(quanAnModel,arrayList, adapterRecycleViewTrangChu,progressBar);
                            }
                        });
                    }
                }

            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if(v.getChildAt(v.getChildCount() -1 )!=null)
                    {
                        if(scrollY >= v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight() && oldScrollY  < scrollY)
                        {
                            vitriTiepTheo +=MAX;
                            vitriHienTai += MAX;
                            if(vitriHienTai < quanAnModel.getSoluongQuanAn())
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                quanAnModel.getDataQuanAn(placeInterface,progressBar,vitriHienTai,vitriTiepTheo);
                            }
                    }
                }
            }
        });
        quanAnModel.getDataQuanAn(placeInterface,progressBar,vitriHienTai,vitriTiepTheo);
    }

    public class NotifyDataChange{
        private int i = 0;
        public synchronized void tangValue(QuanAnModel quanAnModel, ArrayList<QuanAnModel> anModels, AdapterRecycleViewTrangChu adapterRecycleViewTrangChu,
                                           ProgressBar progressBar)
        {
            i = i+1;
            if(quanAnModel.getBinhLuanModels().size() == 0 || (quanAnModel.getBinhLuanModels().size() == 1 && i == 2) || i == 3)
            {
                arrayList.add(quanAnModel);
                adapterRecycleViewTrangChu.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        }
    }
}

