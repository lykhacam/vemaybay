package com.example.kobietten.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;
import com.example.kobietten.controller.Fragment.HotroActivity;

import java.util.Calendar;

public class ManhinhchinhActivity extends AppCompatActivity {
    TextView tv_menu;
    private Button btnDiemdi;
    private Button btnDiemden;
    private Button btnChonngay, btnTimchuyen;
    private static final int REQUEST_DIEMDI = 1;
    private static final int REQUEST_DIEMDEN = 2;

    private EditText edtSnl, edtSte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchinh);
        tv_menu = findViewById(R.id.tv_menu);
        btnDiemdi = findViewById(R.id.btn_diemdi);
        btnDiemden = findViewById(R.id.btn_diemden);
        btnChonngay = findViewById(R.id.btn_chonngay);
        edtSnl = findViewById(R.id.edt_snguoilon);
        edtSte = findViewById(R.id.edt_streem);
        btnTimchuyen = findViewById(R.id.btn_timchuyen);

        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManhinhchinhActivity.this, HotroActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra("EXTRA_EMAIL");

        // Xử lý nhấn nút Điểm đi
        btnDiemdi.setOnClickListener(v -> openDiemdiActivity());

        // Xử lý nhấn nút Điểm đến
        btnDiemden.setOnClickListener(v -> openDiemdenActivity());

        // Xử lý nhấn nút Chọn ngày
        btnChonngay.setOnClickListener(v -> showDatePickerDialog());

        // Xử lý nhấn nút Tìm kiếm
        btnTimchuyen.setOnClickListener(view -> searchFlights(view));
    }



    private void openDiemdiActivity() {
        Intent intent = new Intent(ManhinhchinhActivity.this, DiemdiActivity.class);
        startActivityForResult(intent, REQUEST_DIEMDI);
    }

    private void openDiemdenActivity() {
        Intent intent = new Intent(ManhinhchinhActivity.this, DiemdenActivity.class);
        startActivityForResult(intent, REQUEST_DIEMDEN);
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại làm giá trị mặc định
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo mới DatePickerDialog và hiển thị nó
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ManhinhchinhActivity.this,
                android.R.style.Theme_Material_Light_Dialog_Alert,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    btnChonngay.setText(selectedDate);
                },
                year,
                month,
                day
        );

        Window window = datePickerDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
            window.setDimAmount(0.5f); // Điều chỉnh giá trị này để tăng giảm độ mờ của nền
        }

        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_DIEMDI) {
                String selectedLocation = data.getStringExtra("selectedLocation");
                btnDiemdi.setText(selectedLocation);
            } else if (requestCode == REQUEST_DIEMDEN) {
                String selectedLocation = data.getStringExtra("selectedLocation");
                btnDiemden.setText(selectedLocation);
            }
        }
    }
    public void searchFlights(View view) {
        // Lấy thông tin từ các button và EditText
        String diemdi = btnDiemdi.getText().toString();
        String diemden = btnDiemden.getText().toString();
        String ngaydi = btnChonngay.getText().toString();
        int nguoilon = Integer.parseInt(edtSnl.getText().toString()); // Lấy số người lớn từ EditText
        int treem = Integer.parseInt(edtSte.getText().toString()); // Lấy số trẻ em từ EditText

        // Khởi chạy SearchResultsActivity với dữ liệu tìm kiếm
        Intent searchIntent = new Intent(ManhinhchinhActivity.this, TimchuyenbayActivity.class);
//        them du lieu vao intent de cac layout sau co the su dung
        searchIntent.putExtra("EXTRA_DIEMDI", diemdi);
        searchIntent.putExtra("EXTRA_DIEMDEN", diemden);
        searchIntent.putExtra("EXTRA_NGAYDI", ngaydi);
        searchIntent.putExtra("EXTRA_NGUOILON", nguoilon); // Thêm số lượng người lớn vào intent
        searchIntent.putExtra("EXTRA_TREEM", treem); // Thêm số lượng trẻ em vào intent
        startActivity(searchIntent);
    }
}
