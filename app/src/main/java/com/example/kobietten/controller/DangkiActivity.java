package com.example.kobietten.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kobietten.R;
import com.example.kobietten.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangkiActivity extends AppCompatActivity {

    private EditText edtTen, edtDiaChi, edtDienThoai, edtEmail, edtMatkhau;
    private Button btnDangky;
    private FirebaseAuth firebaseAuth;
    private TextView txtDangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);

        edtTen = findViewById(R.id.edt_ten);
        edtDiaChi = findViewById(R.id.edt_diachi);
        edtDienThoai = findViewById(R.id.edt_dienthoai);
        edtEmail = findViewById(R.id.edt_email);
        edtMatkhau = findViewById(R.id.edt_matkhau);
        btnDangky = findViewById(R.id.btn_dangky);
        firebaseAuth = FirebaseAuth.getInstance();
        txtDangnhap = findViewById(R.id.txt_dangnhap);

        txtDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkiActivity.this, DangnhapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTen.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                String dienThoai = edtDienThoai.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String matkhau = edtMatkhau.getText().toString().trim();

                User newUser = new User(ten, diaChi, dienThoai, email, matkhau);
                if (newUser.isValid()) {
                    registerUser(newUser);
                }
                else {
                    if(!dienThoai.matches("\\d{10}|\\d{11}")){
                        Toast.makeText(DangkiActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(DangkiActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }

                    if(matkhau.length() < 8){
                        Toast.makeText(DangkiActivity.this, "Mật khẩu nhiều hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    }
                    if(ten.isEmpty() || diaChi.isEmpty() || dienThoai.isEmpty() || email.isEmpty() || matkhau.isEmpty()){
                        Toast.makeText(DangkiActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void registerUser(User user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangkiActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangkiActivity.this, DangnhapActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DangkiActivity.this, "Email đã tồn tại, đổi tài khoản email khác " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
