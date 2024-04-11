package com.example.kobietten.controller;


import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.widget.Toolbar;
import com.example.kobietten.model.KhachHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.kobietten.R;

public class ThanhToan_activity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView tvTongtien;
    //AppCompaButton btnThanhToan;
    long tongtien;
    int totalItem;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        initView();
        //countItem();
        //initControl();
    }

//    private void countItem(){
//        totalItem = 0;
//        for (int i=0; i < Utils.chuyenbay.size(); i++){
//            totalItem = totalItem + Utils.chuyenbay.get(i).getSoluong();
//        }
//    }

    private void initView(){
        //toolbar = findViewById(R.id.toolbar);
    }


}