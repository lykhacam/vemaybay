package com.example.kobietten.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;

import java.util.Calendar;
public class manhinhchinh extends AppCompatActivity {

    private Button btnDeparture;
    private Button btnDestination;
    private Button btnPickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchinh); // Đảm bảo đây là tên đúng của layout

        btnDeparture = findViewById(R.id.btnDeparture);
        btnDestination = findViewById(R.id.btnDestination);
        btnPickDate = findViewById(R.id.btn_pick_date);

        // Xử lý nhấn nút Điểm đi
        btnDeparture.setOnClickListener(v -> openKhoihanhActivity());

        // Xử lý nhấn nút Điểm đến
        btnDestination.setOnClickListener(v -> openDiemdenActivity());

        // Xử lý nhấn nút Chọn ngày
        btnPickDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void openKhoihanhActivity() {
        Intent intent = new Intent(manhinhchinh.this, KhoihanhActitvity.class);
        startActivity(intent);
    }

    private void openDiemdenActivity() {
        Intent intent = new Intent(manhinhchinh.this, DiemdenActivity.class);
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại làm giá trị mặc định
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo mới DatePickerDialog và hiển thị nó
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                manhinhchinh.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                },
                year,
                month,
                day
        );

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
