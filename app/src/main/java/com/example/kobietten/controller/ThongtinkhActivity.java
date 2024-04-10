package com.example.kobietten.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class ThongtinkhActivity extends Activity {
    private LinearLayout llNoidungNl, llNoidungTe;
    private EditText etDienthoai, etEmail;
    private Button btnTieptuc;
    private ArrayList<KhachHang> danhSachKhachhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkh);

        etDienthoai = findViewById(R.id.edt_dienthoai);
        etEmail = findViewById(R.id.edt_email);
        llNoidungNl = findViewById(R.id.ll_nguoilon);
        llNoidungTe = findViewById(R.id.ll_treem);
        btnTieptuc = findViewById(R.id.btn_tieptuc);

        // Nhận số lượng người lớn và trẻ em từ Intent
        Intent intent = getIntent();
        String diemDi = intent.getStringExtra("EXTRA_DIEMDI");
        String diemDen = intent.getStringExtra("EXTRA_DIEMDEN");
        String ngayDi = intent.getStringExtra("EXTRA_NGAYDI");
        int numAdults = intent.getIntExtra("EXTRA_ADULTS", 1);
        int numChildren = intent.getIntExtra("EXTRA_CHILDREN", 0);
        danhSachKhachhang = new ArrayList<>();
        // Thêm view động cho người lớn và trẻ em dựa trên số lượng
        addPassengerViews(numAdults, llNoidungNl, true); // true cho người lớn
        addPassengerViews(numChildren, llNoidungTe, false);


        btnTieptuc.setOnClickListener(v -> {
            if (validateAllPassengerInfo()) {
                // Chuyển đến Activity thanh toán nếu thông tin đầy đủ
                 Intent paymentIntent = new Intent(ThongtinkhActivity.this, ThanhtoanActivity.class);
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
                Toast.makeText(ThongtinkhActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPassengerViews(int count, LinearLayout container, boolean isAdult) {
        for (int i = 0; i < count; i++) {
            View passengerView = getLayoutInflater().inflate(R.layout.item_thongtinkh, container, false);

            TextView tvTieude = passengerView.findViewById(R.id.tv_tieudenhap);
            LinearLayout llNhapttkh = passengerView.findViewById(R.id.ll_nhapttkh);
            EditText Ngaysinh = passengerView.findViewById(R.id.edt_ngaysinh);
            String tieude = isAdult ? "Người lớn " + (i + 1) : "Trẻ em " + (i + 1);
            tvTieude.setText(tieude);

            // Đặt visibility mặc định của llNhapttkh là GONE để nó không hiển thị
            llNhapttkh.setVisibility(View.GONE);

            tvTieude.setOnClickListener(v -> {
                // Chuyển đổi visibility của llNhapttkh khi nhấn vào tvtieude
                boolean isDetailsVisible = llNhapttkh.getVisibility() == View.VISIBLE;
                llNhapttkh.setVisibility(isDetailsVisible ? View.GONE : View.VISIBLE);
                tvTieude.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                        isDetailsVisible ? R.drawable.ic_expand_more : R.drawable.ic_expand_less, 0);
            });
            Ngaysinh.setOnClickListener(v -> showDatePickerDialog(Ngaysinh));
            // Thêm view vừa tạo vào container chính
            container.addView(passengerView);
        }
    }

    private boolean validateAllPassengerInfo() {
        // Đảm bảo rằng etPhone và etEmail đã được khai báo và liên kết với view
        EditText edtDienthoai = findViewById(R.id.edt_dienthoai);
        EditText edtEmail = findViewById(R.id.edt_email);

        if (edtDienthoai.getText().toString().trim().isEmpty()) {
            edtDienthoai.setError("Số điện thoại không được để trống");
            return false;
        }

        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtEmail.setError("Email không được để trống");
            return false;
        }


        return validatePassengerInfo(llNoidungNl) && validatePassengerInfo(llNoidungTe);
    }

    private boolean validatePassengerInfo(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            EditText edtTen = view.findViewById(R.id.edt_ten);
            Spinner spGioitinh = view.findViewById(R.id.gioitinh);
            EditText edtNgaysinh = view.findViewById(R.id.edt_ngaysinh);
            String namSinh = extractYear(edtNgaysinh.getText().toString());
            if (edtTen.getText().toString().trim().isEmpty()) {
                edtTen.setError("Tên không được để trống");
                return false;
            }

            if (spGioitinh.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (edtNgaysinh.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                return false;
            }
            danhSachKhachhang.add(new KhachHang(edtTen.getText().toString(),namSinh));
        }
        return true;
    }

    private void showDatePickerDialog(EditText etDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ThongtinkhActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        etDate.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private String extractYear(String date) {
        return date.substring(0, 4); // Lấy bốn ký tự đầu tiên là năm
    }
}
