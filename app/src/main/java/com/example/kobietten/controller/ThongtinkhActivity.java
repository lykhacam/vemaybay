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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String email = intent.getStringExtra("EXTRA_EMAIL");
        String diemDen = intent.getStringExtra("EXTRA_DIEMDEN");
        String ngayDi = intent.getStringExtra("EXTRA_NGAYDI");
        int numAdults = intent.getIntExtra("EXTRA_ADULTS", 1);
        int numChildren = intent.getIntExtra("EXTRA_CHILDREN", 0);
        danhSachKhachhang = new ArrayList<>();
        // Thêm view động cho người lớn và trẻ em dựa trên số lượng
        addPassengerViews(numAdults, llNoidungNl, true); // true cho người lớn
        addPassengerViews(numChildren, llNoidungTe, false);

//    set cứng email
        etEmail.setText(email);
        btnTieptuc.setOnClickListener(v -> {
            if (validateAllPassengerInfo() && validatePhone(etDienthoai.getText().toString())) {
                // Chuyển đến Activity thanh toán nếu thông tin đầy đủ
                 Intent paymentIntent = new Intent(ThongtinkhActivity.this, ThanhtoanActivity.class);
                 paymentIntent.putExtra("PUT_DIEMDI", diemDi);
                 paymentIntent.putExtra("PUT_DIEMDEN", diemDen);
                 paymentIntent.putExtra("PUT_NGAYDI", ngayDi);
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

        if (edtDienthoai.getText().toString().trim().isEmpty()) {
            edtDienthoai.setError("Số điện thoại không được để trống");
            return false;
        }

        return validatePassengerInfo(llNoidungNl) && validatePassengerInfo(llNoidungTe);
    }

    private boolean validatePassengerInfo(LinearLayout container) {
        boolean isValid = true; // Giả định tất cả thông tin đều hợp lệ ban đầu
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            EditText edtTen = view.findViewById(R.id.edt_ten);
            Spinner spGioitinh = view.findViewById(R.id.gioitinh);
            EditText edtNgaysinh = view.findViewById(R.id.edt_ngaysinh);

            // Kiểm tra tên
            if (edtTen.getText().toString().trim().isEmpty()) {
                edtTen.setError("Tên không được để trống");
                isValid = false;
            }

            // Kiểm tra giới tính
            if (spGioitinh.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Vui lòng chọn giới tính cho hành khách " + (i + 1), Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            // Kiểm tra ngày sinh
            if (edtNgaysinh.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Ngày sinh không được để trống cho hành khách " + (i + 1), Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        }

        // Chỉ thêm vào danh sách nếu tất cả thông tin hợp lệ
        if (isValid) {
            for (int i = 0; i < container.getChildCount(); i++) {
                View view = container.getChildAt(i);
                EditText edtTen = view.findViewById(R.id.edt_ten);
                EditText edtNgaysinh = view.findViewById(R.id.edt_ngaysinh);
                String namSinh = extractYear(edtNgaysinh.getText().toString());
                danhSachKhachhang.add(new KhachHang(edtTen.getText().toString(), namSinh));
            }
        }

        return isValid;
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
    private boolean validatePhone(String phone) {
        String phoneRegex = "^[0-9]{10}$"; // Định dạng số điện thoại 10 chữ số
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            etDienthoai.setError("Số điện thoại không hợp lệ");
            return false;
        }
        return true;
    }
}
