package com.example.kobietten.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kobietten.R;
import com.example.kobietten.model.KhachHang;

import java.util.Calendar;
import java.util.ArrayList;

public class PassengerInfoActivity extends Activity {
    private LinearLayout llAdultInfoContainer, llChildInfoContainer;
    private EditText etDienthoai, etEmail;
    private Button btnContinue;
    private ArrayList<KhachHang> danhSachKhachhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info);

        etDienthoai = findViewById(R.id.etDienthoai);
        etEmail = findViewById(R.id.etEmail);
        llAdultInfoContainer = findViewById(R.id.llAdultInfoContainer);
        llChildInfoContainer = findViewById(R.id.llChildInfoContainer);
        btnContinue = findViewById(R.id.btnContinue);

        // Nhận số lượng người lớn và trẻ em từ Intent
        Intent intent = getIntent();
        String diemDi = intent.getStringExtra("EXTRA_DIEMDI");
        String diemDen = intent.getStringExtra("EXTRA_DIEMDEN");
        String ngayDi = intent.getStringExtra("EXTRA_NGAYDI");
        int numAdults = intent.getIntExtra("EXTRA_ADULTS", 1);
        int numChildren = intent.getIntExtra("EXTRA_CHILDREN", 0);
        danhSachKhachhang = new ArrayList<>();
        // Thêm view động cho người lớn và trẻ em dựa trên số lượng
        addPassengerViews(numAdults, llAdultInfoContainer, true); // true cho người lớn
        addPassengerViews(numChildren, llChildInfoContainer, false);


        btnContinue.setOnClickListener(v -> {
            if (validateAllPassengerInfo()) {
                // Chuyển đến Activity thanh toán nếu thông tin đầy đủ
                 Intent paymentIntent = new Intent(PassengerInfoActivity.this,PaymentActivity.class);
                 paymentIntent.putExtra("PUT_DIEMDI", diemDi);
                 paymentIntent.putExtra("PUT_DIEMDEN", diemDen);
                 paymentIntent.putExtra("PUT_DIEMDI", ngayDi);
                 paymentIntent.putExtra("PUT_DIENTHOAI", etDienthoai.getText().toString());
                 paymentIntent.putExtra("PUT_EMAIL", etEmail.getText().toString());
                // Thêm danh sách khách hàng vào Intent
                 paymentIntent.putExtra("EXTRA_DANH_SACH_KHACH_HANG", danhSachKhachhang);
                 startActivity(paymentIntent);
            }
            else {
                // Xử lý trường hợp thông tin không hợp lệ
                Toast.makeText(PassengerInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPassengerViews(int count, LinearLayout container, boolean isAdult) {
        for (int i = 0; i < count; i++) {
            View passengerView = getLayoutInflater().inflate(R.layout.passenger_info_item, container, false);

            TextView tvPassengerTitle = passengerView.findViewById(R.id.tvPassengerTitle);
            LinearLayout llPassengerDetails = passengerView.findViewById(R.id.llPassengerDetails); // Đây phải là container chứa các thông tin chi tiết
            EditText Ngaysinh = passengerView.findViewById(R.id.etNgaysinh);
            String title = isAdult ? "Người lớn " + (i + 1) : "Trẻ em " + (i + 1);
            tvPassengerTitle.setText(title);

            // Đặt visibility mặc định của llPassengerDetails là GONE để nó không hiển thị
            llPassengerDetails.setVisibility(View.GONE);

            tvPassengerTitle.setOnClickListener(v -> {
                // Chuyển đổi visibility của llPassengerDetails khi nhấn vào tvPassengerTitle
                boolean isDetailsVisible = llPassengerDetails.getVisibility() == View.VISIBLE;
                llPassengerDetails.setVisibility(isDetailsVisible ? View.GONE : View.VISIBLE);
                tvPassengerTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                        isDetailsVisible ? R.drawable.ic_expand_more : R.drawable.ic_expand_less, 0);
            });
            Ngaysinh.setOnClickListener(v -> showDatePickerDialog(Ngaysinh));
            // Thêm view vừa tạo vào container chính
            container.addView(passengerView);
        }
    }



    private boolean validateAllPassengerInfo() {
        return validatePassengerInfo(llAdultInfoContainer) && validatePassengerInfo(llChildInfoContainer);
    }


    private boolean validatePassengerInfo(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            EditText Ten = view.findViewById(R.id.etTen);
            Spinner Gioitinh = view.findViewById(R.id.Gioitinh);
            EditText Ngaysinh = view.findViewById(R.id.etNgaysinh);
            String namSinh = extractYear(Ngaysinh.getText().toString());
            if (Ten.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Họ và tên không được để trống", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Gioitinh.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Ngaysinh.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                return false;
            }
            danhSachKhachhang.add(new KhachHang(Ten.getText().toString(),namSinh));


        }
        return true;
    }
    private void showDatePickerDialog(EditText etDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PassengerInfoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Cập nhật EditText ngaysinh với ngày đã chọn
                        // Định dạng ngày tháng có thể thay đổi tùy ý bạn muốn
                        String selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        etDate.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private String extractYear(String date) {
        // Giả sử định dạng ngày là YYYY-MM-DD
        return date.substring(0, 4); // Lấy bốn ký tự đầu tiên là năm
    }
}
