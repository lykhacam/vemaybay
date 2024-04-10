package com.example.kobietten.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kobietten.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenmkActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnQuenmk;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmk); // Set the correct layout name

        edtEmail = findViewById(R.id.edt_email);
        btnQuenmk = findViewById(R.id.btn_qmk);
        auth = FirebaseAuth.getInstance();

        btnQuenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    edtEmail.setError("Email không được để trống");
                    return;
                }

                // Send password reset email
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(QuenmkActivity.this, "Chúng tôi đã gửi hướng dẫn đặt lại mật khẩu vào email của bạn.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(QuenmkActivity.this, "Không thể gửi email đặt lại mật khẩu.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
