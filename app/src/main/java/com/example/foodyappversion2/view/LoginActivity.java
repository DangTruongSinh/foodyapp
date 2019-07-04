package com.example.foodyappversion2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.control.ThanhVienModelControler;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Button btnDangNhap;
    Button btnEmail;
    EditText edtEmail, edtPassword;
    Button btnGoogle;
    TextView txtDangKy,txtQuenMk;
    ProgressDialog progressDialog;
    CallbackManager callbackManager;
    List<String> arrayList = Arrays.asList("email","public_profile");
    public static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btnEmail = findViewById(R.id.btnDangNhapEmail);
        btnDangNhap = findViewById(R.id.btnDangNhapEmail);
        btnGoogle = findViewById(R.id.btnGoogle);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtQuenMk = findViewById(R.id.txtQuenMk);
        txtDangKy.setOnClickListener(this);
        txtQuenMk.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtQuenMk.setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        Button loginButton = findViewById(R.id.btnloginfb);
        loginButton.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

    }

    private void handleFacebookLogin() {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,arrayList);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog =  ProgressDialog.show(LoginActivity.this,getString(R.string.load),getString(R.string.messageLogin));
                progressDialog.setCanceledOnTouchOutside(false);
                AuthCredential authCredential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            firebaseUser = firebaseAuth.getCurrentUser();
                            if(accessToken == null)
                                ThanhVienModelControler.setThanhVienModel(firebaseUser.getDisplayName(),"user2.png",firebaseUser.getUid());
                            updateUI(firebaseUser);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, getString(R.string.failLogin), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    private void updateUI(FirebaseUser firebaseUser) {
        progressDialog.dismiss();
        Intent intent = new Intent(LoginActivity.this, TrangChuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtQuenMk:
                handleQuenMK();
                break;
            case R.id.btnDangNhapEmail:
                handleDangNhapEmail();
                break;
            case R.id.txtDangKy:
                handleDangKy();
                break;
            case R.id.btnloginfb:
                handleFacebookLogin();
                break;
            case R.id.btnGoogle:
                initloginwithGoogle();
                break;
        }
    }

    private void handleQuenMK() {
        Intent intent = new Intent(LoginActivity.this,QuenMKActivity.class);
        startActivity(intent);
    }

    private void handleDangNhapEmail() {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            progressDialog =  ProgressDialog.show(LoginActivity.this,getString(R.string.load),getString(R.string.messageLogin));
            progressDialog.setCanceledOnTouchOutside(false);
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        if(firebaseUser.isEmailVerified())
                            updateUI(firebaseUser);
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "You have not verify email! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        progressDialog.dismiss();
                        notifyAccountException(task.getException().toString());

                    }
                }
            });

    }

    private void notifyAccountException(String exception) {
        if(exception.contains("FirebaseAuthInvalidUserException") || exception.contains("FirebaseAuthInvalidCredentialsException"))
            Toast.makeText(LoginActivity.this, "Account does not correct!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Connect not success! Please check internet!", Toast.LENGTH_SHORT).show();
    }

    private void handleDangKy() {
            Intent intent = new Intent(LoginActivity.this,DangKyActivity.class);
            startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {

            loginwithGoogle(data);
        }
        else
            callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void loginwithGoogle(Intent data) {

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);

            progressDialog =  ProgressDialog.show(LoginActivity.this,getString(R.string.load),getString(R.string.messageLogin));
            progressDialog.setCanceledOnTouchOutside(false);

            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        firebaseUser = firebaseAuth.getCurrentUser();
                            //ThanhVienModelControler.setThanhVienModel(firebaseUser.getDisplayName(),"user2.png",firebaseUser.getUid());
                        updateUI(firebaseUser);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,getString(R.string.failLogin), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (ApiException e) {
            Log.d("Tag", "exception authen with google signin");
            e.printStackTrace();
        }
    }

    private void initloginwithGoogle() {

        GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        GoogleSignInClient googleSignIn = GoogleSignIn.getClient(this,gsio);
        Intent intent = googleSignIn.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
}

