package com.example.kobietten.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;

import java.util.Calendar;

public class manhinhchinh extends AppCompatActivity {

    private Button btnDeparture;
    private Button btnDestination;
    private Button btnPickDate, btnSearch;
    private static final int REQUEST_DEPARTURE = 1;
    private static final int REQUEST_DESTINATION = 2;

    private EditText etAdults, etChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchinh); // Đảm bảo đây là tên đúng của layout

        btnDeparture = findViewById(R.id.btnDeparture);
        btnDestination = findViewById(R.id.btnDestination);
        btnPickDate = findViewById(R.id.btn_pick_date);
        btnSearch = findViewById(R.id.btn_search_flights);
        etAdults = findViewById(R.id.edt_adult); // Giả sử bạn có EditText này trong layout của mình
        etChildren = findViewById(R.id.edt_child); // Giả sử bạn có EditText này trong layout của mình



        // Xử lý nhấn nút Điểm đi
        btnDeparture.setOnClickListener(v -> openKhoihanhActivity());

        // Xử lý nhấn nút Điểm đến
        btnDestination.setOnClickListener(v -> openDiemdenActivity());

        // Xử lý nhấn nút Chọn ngày
        btnPickDate.setOnClickListener(v -> showDatePickerDialog());

        // Xử lý nhấn nút Tìm kiếm
        btnSearch.setOnClickListener(view -> searchFlights(view));
    }



    private void openKhoihanhActivity() {
        Intent intent = new Intent(manhinhchinh.this, KhoihanhActivity.class);
        startActivityForResult(intent, REQUEST_DEPARTURE);
    }

    private void openDiemdenActivity() {
        Intent intent = new Intent(manhinhchinh.this, DiemdenActivity.class);
        startActivityForResult(intent, REQUEST_DESTINATION);
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
                android.R.style.Theme_Material_Light_Dialog_Alert,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    btnPickDate.setText(selectedDate);
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
            if (requestCode == REQUEST_DEPARTURE) {
                String selectedLocation = data.getStringExtra("selectedLocation");
                btnDeparture.setText(selectedLocation);
            } else if (requestCode == REQUEST_DESTINATION) {
                String selectedLocation = data.getStringExtra("selectedLocation");
                btnDestination.setText(selectedLocation);
            }
        }
    }
    public void searchFlights(View view) {
        // Lấy thông tin từ các button và EditText
        String departureCode = btnDeparture.getText().toString();
        String destinationCode = btnDestination.getText().toString();
        String date = btnPickDate.getText().toString();
        int adults = Integer.parseInt(etAdults.getText().toString()); // Lấy số người lớn từ EditText
        int children = Integer.parseInt(etChildren.getText().toString()); // Lấy số trẻ em từ EditText

        // Khởi chạy SearchResultsActivity với dữ liệu tìm kiếm
        Intent searchIntent = new Intent(manhinhchinh.this, SearchResultsActivity.class);
        searchIntent.putExtra("DEPARTURE_CODE", departureCode);
        searchIntent.putExtra("DESTINATION_CODE", destinationCode);
        searchIntent.putExtra("DATE", date);
        searchIntent.putExtra("ADULTS_COUNT", adults); // Thêm số lượng người lớn vào intent
        searchIntent.putExtra("CHILDREN_COUNT", children); // Thêm số lượng trẻ em vào intent
        startActivity(searchIntent);
    }
}
