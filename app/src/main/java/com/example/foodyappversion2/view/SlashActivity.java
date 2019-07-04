package com.example.foodyappversion2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import com.example.foodyappversion2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SlashActivity extends AppCompatActivity {
    final int REQUEST_PERMISSION_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(SlashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Truy cap vi tri");
            builder.setMessage("Ung dung can lay vi tri cua ban de tinh khoang cach den quan an");
            builder.setPositiveButton("Xac nhan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(SlashActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_PERMISSION_LOCATION);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else
            getLocation();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_LOCATION)
        {
            if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                getLocation();
            else
            {
                finish();
            }


        }
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("toado",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitue",location.getLatitude()+"");
                    editor.putString("longtitue",location.getLongitude()+"");
                    editor.commit();
                    try {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo("com.truongsinh.foodyapp",0);
                        TextView txtVersion = findViewById(R.id.txtVersion);
                        txtVersion.setText("Version "+ packageInfo.versionName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SlashActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },1000);
                }
            }
        });
    }
}
