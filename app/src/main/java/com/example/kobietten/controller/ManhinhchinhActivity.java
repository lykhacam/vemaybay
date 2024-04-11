package com.example.kobietten.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kobietten.R;

import java.util.Calendar;

public class ManhinhchinhActivity extends AppCompatActivity {
    LinearLayout lncskh,lncsvbm,lndangxuat;
    private Button btnDiemdi;
    private Button btnDiemden;
    private Button btnChonngay, btnTimchuyen;
    DrawerLayout drawerLayout;
    private String email;
    private static final int REQUEST_DIEMDI = 1;
    private static final int REQUEST_DIEMDEN = 2;
    private EditText edtSnl, edtSte;
    ImageView ivBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchinh);

        btnDiemdi = findViewById(R.id.btn_diemdi);
        btnDiemden = findViewById(R.id.btn_diemden);
        btnChonngay = findViewById(R.id.btn_chonngay);
        edtSnl = findViewById(R.id.edt_snguoilon);
        edtSte = findViewById(R.id.edt_streem);
        btnTimchuyen = findViewById(R.id.btn_timchuyen);
        drawerLayout = findViewById(R.id.drawlayout);
        ivBars = findViewById(R.id.ivBars);
        lncskh = findViewById(R.id.lncskh);
        lncsvbm = findViewById(R.id.lncsvbm);
        lndangxuat = findViewById(R.id.lndangxuat);




        Intent intent = getIntent();
        email = intent.getStringExtra("EXTRA_EMAIL");

        ivBars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        // Xử lý nhấn nút Điểm đi
        btnDiemdi.setOnClickListener(v -> openDiemdiActivity());

        // Xử lý nhấn nút Điểm đến
        btnDiemden.setOnClickListener(v -> openDiemdenActivity());

        // Xử lý nhấn nút Chọn ngày
        btnChonngay.setOnClickListener(v -> showDatePickerDialog());

        // Xử lý nhấn nút Tìm kiếm
        btnTimchuyen.setOnClickListener(view -> searchFlights(view));
        lncskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ManhinhchinhActivity.this, HotroActivity.class);
            }
        });
        lncsvbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ManhinhchinhActivity.this, ChinhsachActivity.class);
            }
        });

        lndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(R.layout.signout);

                final AlertDialog dialog = builder.create();
                dialog.show();

                Button bYes = dialog.findViewById(R.id.bYes);
                Button bNo = dialog.findViewById(R.id.bNo);

                bYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ManhinhchinhActivity.this, DangnhapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                bNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });







    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
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
        // Lấy thông tin từ các EditText và Button
        String diemdi = btnDiemdi.getText().toString();
        String diemden = btnDiemden.getText().toString();
        String ngaydi = btnChonngay.getText().toString();
        String soNguoiLon = edtSnl.getText().toString();
        String soTreEm = edtSte.getText().toString();

        // Kiểm tra xem người dùng có nhập số người lớn và trẻ em không
        if (soNguoiLon.isEmpty() || soTreEm.isEmpty()) {
            Toast.makeText(this, "Bạn cần nhập số lượng người lớn và trẻ em.", Toast.LENGTH_LONG).show();
            return; // Dừng hàm nếu thông tin chưa được nhập
        }

        int nguoilon, treem;
        try {
            nguoilon = Integer.parseInt(soNguoiLon); // Lấy số người lớn từ EditText
            treem = Integer.parseInt(soTreEm); // Lấy số trẻ em từ EditText
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số lượng người lớn và trẻ em phải là số hợp lệ.", Toast.LENGTH_LONG).show();
            return; // Dừng hàm nếu có lỗi khi chuyển đổi sang số
        }

        // Kiểm tra xem người dùng đã chọn điểm đi, điểm đến và ngày đi chưa
        boolean diemdiChuaChon = diemdi.equals(getString(R.string.hint_diemdi));
        boolean diemdenChuaChon = diemden.equals(getString(R.string.hint_diemden));
        boolean ngaydiChuaChon = ngaydi.equals(getString(R.string.hint_ngaydi));

        if (diemdiChuaChon || diemdenChuaChon || ngaydiChuaChon) {
            String message = "Bạn cần chọn ";
            if (diemdiChuaChon) message += "điểm đi, ";
            if (diemdenChuaChon) message += "điểm đến, ";
            if (ngaydiChuaChon) message += "ngày đi.";
            message = message.substring(0, message.length() - 2) + ".";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return; // Dừng hàm nếu thông tin chưa được chọn
        }

        // Nếu tất cả thông tin đã được nhập, tiếp tục với việc khởi chạy Activity mới
        Intent searchIntent = new Intent(ManhinhchinhActivity.this, TimchuyenbayActivity.class);
        searchIntent.putExtra("EXTRA_DIEMDI", diemdi);
        searchIntent.putExtra("EXTRA_DIEMDEN", diemden);
        searchIntent.putExtra("EXTRA_NGAYDI", ngaydi);
        searchIntent.putExtra("EXTRA_NGUOILON", nguoilon);
        searchIntent.putExtra("EXTRA_TREEM", treem);
        searchIntent.putExtra("EXTRA_EMAIL", email);
        startActivity(searchIntent);
    }

}