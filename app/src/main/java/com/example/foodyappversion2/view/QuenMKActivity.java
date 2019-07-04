package com.example.foodyappversion2.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyappversion2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMKActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail;
    Button btnXacNhan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmk_activity);
        edtEmail = findViewById(R.id.edtEmailKhoiPhuc);
        btnXacNhan = findViewById(R.id.btnXacNhanEmailKhoiPhuc);
        btnXacNhan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(edtEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.getException().toString().contains("FirebaseAuthInvalidUserException"))
                    Toast.makeText(QuenMKActivity.this, "Email have not register!", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(QuenMKActivity.this, "Please check email to reset password!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}
