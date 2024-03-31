package com.example.kobietten.controler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtTen, edtDiaChi, edtDienThoai, edtEmail, edtPassword;
    private Button buttonRegister;
    private FirebaseAuth firebaseAuth;
    private TextView txtDangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTen = findViewById(R.id.edt_ten);
        edtDiaChi = findViewById(R.id.edt_diachi);
        edtDienThoai = findViewById(R.id.edt_dienthoai);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        buttonRegister = findViewById(R.id.button_register);
        firebaseAuth = FirebaseAuth.getInstance();
        txtDangnhap = findViewById(R.id.dangnhap);

        txtDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTen.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                String dienThoai = edtDienThoai.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                User newUser = new User(ten, diaChi, dienThoai, email, password);
                if (newUser.isValid()) {
                    registerUser(newUser);
                }
                else {
                    if(!dienThoai.matches("\\d{10}|\\d{11}")){
                        Toast.makeText(RegisterActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }

                    if(password.length() < 8){
                        Toast.makeText(RegisterActivity.this, "Mật khẩu nhiều hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    }
                    if(ten.isEmpty() || diaChi.isEmpty() || dienThoai.isEmpty() || email.isEmpty() || password.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email đã tồn tại, đổi tài khoản email khác " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
