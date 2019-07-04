package com.example.foodyappversion2.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodyappversion2.R;
import com.example.foodyappversion2.adapter.AdapterRecycleViewChiTietQA;
import com.example.foodyappversion2.control.TienIchControler;
import com.example.foodyappversion2.model.BinhLuanModel;
import com.example.foodyappversion2.model.ChiNhanhQuanAnModel;
import com.example.foodyappversion2.model.QuanAnModel;
import com.example.foodyappversion2.model.ThanhVienModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    public static String INTENT_QUANAN = "quanan";
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    TextView txtTitle;
    TextView txtTenQuanAn;
    LinearLayout linearChiNhanhQuanAn;
    TextView txtSoBinhLuan;
    TextView txtGio;
    TextView txtTrangThai;
    TextView txtLoadBinhLuan;
    RecyclerView recyclerViewChiTietQA;
    NestedScrollView nestedScrollView;
    AdapterRecycleViewChiTietQA adapterRecycleViewChiTietQA;
    LinearLayout linearLayout;
    View viewRaoChan;
    TextView txtDiaChi,txtGia;
    Button btnBinhLuan;
    List<BinhLuanModel> binhLuanModelList;
    int vitri;
    QuanAnModel quanAnModel;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietquanan_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewFlipper = findViewById(R.id.view_fliper);
        txtTitle = findViewById(R.id.txtTitleChiTietQuanAn);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        linearChiNhanhQuanAn = findViewById(R.id.chinhanhquanans);
        txtSoBinhLuan = findViewById(R.id.txtSoBinhLuan);
        txtGio = findViewById(R.id.txtGio);
        txtTrangThai = findViewById(R.id.txtTrangThai);
        recyclerViewChiTietQA = findViewById(R.id.recycle_viewChiTietQuanAn);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        viewRaoChan = findViewById(R.id.view_raochan);
        txtLoadBinhLuan = findViewById(R.id.txtLoadBinhLuan);
        view = findViewById(R.id.view_bando);
        linearLayout = findViewById(R.id.linear_tienich);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGia = findViewById(R.id.txtGia);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewChiTietQA.setLayoutManager(layoutManager);

        //google map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        //
        quanAnModel =  getIntent().getParcelableExtra(ChiTietQuanAnActivity.INTENT_QUANAN);
        txtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(quanAnModel.getVitriQuanAnGanNhat()).getDiachi());
        if(quanAnModel.getGiamin() == 0 || quanAnModel.getGiamax() == 0)
            txtGia.setVisibility(View.GONE);
        else
            txtGia.setText(quanAnModel.getGiamin()+" - " + quanAnModel.getGiamax());
        // add utilities to view
        TienIchControler tienIchControler = new TienIchControler();
        if(quanAnModel.getTienich() != null)
            tienIchControler.getAllTienIchModel(quanAnModel.getTienich(),linearLayout,getApplicationContext());
        setDataToLayout();
        getAllHinhAnhQuanAn();
        // bind data to view. Load 3 item first to view. if don't have item then give view gone
        if(quanAnModel.getBinhLuanModels().size() > 0)
           load3FirstItem();
        else
            txtLoadBinhLuan.setVisibility(View.GONE);
        // handle event click load more Comment. This funciton will load more 3 item .
        txtLoadBinhLuan.setOnClickListener(this);
        viewFlipper.setOnClickListener(this);
        view.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
    }

    private void handleShowImageToViewFliper() {
        List<String> hinhanhs = quanAnModel.getHinhanhlist();
        String arr[] = new String[hinhanhs.size()];
        hinhanhs.toArray(arr);
        Intent intent = new Intent(ChiTietQuanAnActivity.this,HinhAnhChiTietActivity.class);
        intent.putExtra(HinhAnhChiTietActivity.INTENT_LIST,arr);
        startActivity(intent);
    }

    private void handleLoadMoreBinhLuan() {
        for(int i = 0;i < 3 && vitri < quanAnModel.getBinhLuanModels().size();i++)
        {
            binhLuanModelList.add(quanAnModel.getBinhLuanModels().get(vitri));
            loadHinhAnhUSer(vitri);
            loadHinhAnhBinhLuan(vitri);
            vitri++;
        }
        if(vitri == quanAnModel.getBinhLuanModels().size())
            txtLoadBinhLuan.setVisibility(View.GONE);
        else
            adapterRecycleViewChiTietQA.notifyDataSetChanged();
    }

    private void load3FirstItem() {
        binhLuanModelList = new ArrayList<>();
        for(vitri = 0;vitri < 3;vitri++)
        {
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModels().get(vitri);
            binhLuanModelList.add(binhLuanModel);
            loadHinhAnhUSer(vitri);
            loadHinhAnhBinhLuan(vitri);
        }
    }

    private void loadHinhAnhUSer(int i)
    {
        final ThanhVienModel thanhVienModel = quanAnModel.getBinhLuanModels().get(i).getThanhVienModel();
        long ONE_MB = 1024*1024;
        storageReference.child("hinhanhuser").child(thanhVienModel.getHinhanh()).getBytes(ONE_MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                thanhVienModel.setBitmapHinhAnhUser(bitmap);
                if(adapterRecycleViewChiTietQA != null)
                    adapterRecycleViewChiTietQA.notifyDataSetChanged();
            }
        });
    }
    private void loadHinhAnhBinhLuan(int position)
    {

        final BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModels().get(position);
        if(binhLuanModel.getHinhanhBinhLuans() != null)
        {
            long ONE_MB = 1024*1024;
            final ArrayList<Bitmap> arrayList = new ArrayList<>();
            int max = 4;
            Log.d("soluong",binhLuanModel.getHinhanhBinhLuans().size()+"");
            if(binhLuanModel.getHinhanhBinhLuans().size() < 4)
                max = binhLuanModel.getHinhanhBinhLuans().size();
            for(int i = 0; i < max;i++)
            {
                String linkHinh = binhLuanModel.getHinhanhBinhLuans().get(i);
                final int finalMax = max;
                storageReference.child("hinhquanan").child(linkHinh).getBytes(ONE_MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        arrayList.add(bitmap);

                        if(arrayList.size() == finalMax)
                        {
                            binhLuanModel.setBitmapBinhLuans(arrayList);
                            adapterRecycleViewChiTietQA.notifyDataSetChanged();
                        }
                    }
                });
            }
        }

    }
    private void setDataToLayout() {
        txtTitle.setText(quanAnModel.getTenquanan());
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        for(ChiNhanhQuanAnModel chiNhanhQuanAnModel: quanAnModel.getChiNhanhQuanAnModelList())
        {
            TextView textView = new TextView(this);
            textView.setTextSize(11);
            textView.setText("- "+chiNhanhQuanAnModel.getDiachi());
            linearChiNhanhQuanAn.addView(textView);
        }
        txtSoBinhLuan.setText(quanAnModel.getBinhLuanModels().size()+"");
        txtGio.setText(quanAnModel.getGiomocua()+"-"+quanAnModel.getGiodongcua());
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        try {
           Date gioMoCua = format.parse(quanAnModel.getGiomocua());
           Date gioDongCua = format.parse(quanAnModel.getGiodongcua());
           Calendar calendar = Calendar.getInstance();
           Date gio = calendar.getTime();
           Date dateHienTai = format.parse(format.format(gio));
           if(dateHienTai.after(gioMoCua) && dateHienTai.before(gioDongCua))
               txtTrangThai.setText("Đang Mở Cửa");
           else
               txtTrangThai.setText("Đã Đóng Cửa");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(quanAnModel.getBinhLuanModels().size()==0)
            viewRaoChan.setVisibility(View.GONE);

    }

    public void getAllHinhAnhQuanAn()
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhquanan");
        long ONE_MEGABYTE = 1024 * 1024;
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
        quanAnModel.setBitmapList(bitmapArrayList);

        for(String hinhanh : quanAnModel.getHinhanhlist())
        {

            storageReference.child(hinhanh).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    quanAnModel.getBitmapList().add(bitmap);
                    if(quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhlist().size()) {

                        for (Bitmap bitmap1 : quanAnModel.getBitmapList()) {
                            ImageView imageView = new ImageView(ChiTietQuanAnActivity.this);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setImageBitmap(bitmap1);

                            viewFlipper.addView(imageView);
                            if (quanAnModel.getHinhanhlist().size() != 1) {
                                viewFlipper.setFlipInterval(3000);
                                viewFlipper.startFlipping();
                            }

                            //animation
                            viewFlipper.setInAnimation(ChiTietQuanAnActivity.this, android.R.anim.fade_in);
                            viewFlipper.setOutAnimation(ChiTietQuanAnActivity.this, android.R.anim.fade_out);
                        }
                        adapterRecycleViewChiTietQA = new AdapterRecycleViewChiTietQA(binhLuanModelList,ChiTietQuanAnActivity.this);
                        recyclerViewChiTietQA.setAdapter(adapterRecycleViewChiTietQA);
                        recyclerViewChiTietQA.smoothScrollToPosition(0);
                    }

                }
            });
        }


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // get latitude and longitude
        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(quanAnModel.getVitriQuanAnGanNhat()).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(quanAnModel.getVitriQuanAnGanNhat()).getLongitude();
        LatLng latLng = new LatLng(latitude,longitude);
        // add marker to google map
        googleMap.addMarker(new MarkerOptions().position(latLng).title(quanAnModel.getTenquanan()));
        // move camera to location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.view:
                startActivityMap();
                break;
            case R.id.txtLoadBinhLuan:
                handleLoadMoreBinhLuan();
                break;
            case R.id.view_fliper:
                handleShowImageToViewFliper();
                break;
            case R.id.btnBinhLuan:
                startActivityComment();
                break;
        }

    }

    private void startActivityComment() {
        Intent intent = new Intent(ChiTietQuanAnActivity.this,CommentActivity.class);
        intent.putExtra("quanan",quanAnModel.getTenquanan());
        startActivity(intent);
    }

    private void startActivityMap()
    {
        ChiNhanhQuanAnModel chiNhanhQuanAnModel = quanAnModel.getChiNhanhQuanAnModelList().get(quanAnModel.getVitriQuanAnGanNhat());
        Intent intent = new Intent(this,MapActivity.class);
        intent.putExtra("latitude",chiNhanhQuanAnModel.getLatitude());
        intent.putExtra("longitude",chiNhanhQuanAnModel.getLongitude());

        startActivity(intent);
    }
}
