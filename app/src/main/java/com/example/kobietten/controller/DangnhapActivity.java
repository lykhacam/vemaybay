package com.example.kobietten.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kobietten.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangnhapActivity extends AppCompatActivity {

    private EditText  edtEmail, edtMatkhau;
    private Button btnDangnhap;
    private FirebaseAuth firebaseAuth;
    CheckBox cbnhomk;
    private TextView txtDangky, txtquenmk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        edtEmail = findViewById(R.id.edt_email);
        edtMatkhau = findViewById(R.id.edt_matkhau);
        btnDangnhap = findViewById(R.id.btn_dangnhap);
        firebaseAuth = FirebaseAuth.getInstance();
        txtDangky = findViewById(R.id.txt_dangky);
        txtquenmk = findViewById(R.id.txt_quenmk);

        txtDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, DangkiActivity.class);
                startActivity(intent);
            }
        });

        txtquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, QuenmkActivity.class);
                startActivity(intent);
            }
        });


        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtMatkhau.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(DangnhapActivity.this,"Nhập Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(DangnhapActivity.this,"Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DangnhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangnhapActivity.this, ManhinhchinhActivity.class);
                            intent.putExtra("EXTRA_EMAIL", email);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(DangnhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}