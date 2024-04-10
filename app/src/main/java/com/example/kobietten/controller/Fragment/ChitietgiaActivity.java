package com.example.kobietten.controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.kobietten.controller.ThongtinkhActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.kobietten.R;

public class ChitietgiaActivity extends BottomSheetDialogFragment {

    public ChitietgiaActivity() {
        // Constructor mặc định
    }
    public static ChitietgiaActivity newInstance(int soNguoiLon, int soTreEm, int giaVe) {
        ChitietgiaActivity fragment = new ChitietgiaActivity();
        Bundle args = new Bundle();
        args.putInt("soNguoiLon", soNguoiLon);
        args.putInt("soTreEm", soTreEm);
        args.putInt("giaVe", giaVe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chitietgia, container, false);

        // Lấy thông tin từ Bundle
        int soNguoiLon = getArguments().getInt("soNguoiLon");
        int soTreEm = getArguments().getInt("soTreEm");
        int giaVe = getArguments().getInt("giaVe");

        // Cập nhật thông tin chi tiết giá vé
        // Ví dụ: Hiển thị số lượng và giá vé
        TextView tvSoNguoiLon = view.findViewById(R.id.tv_nguoilon);
        TextView tvSoTreEm = view.findViewById(R.id.tv_treem);
        TextView tvTienNL = view.findViewById(R.id.tv_tiennl);
        TextView tvTienTE = view.findViewById(R.id.tv_tiente);
        TextView tvTongTien = view.findViewById(R.id.tv_tongtien);

        tvSoNguoiLon.setText(String.format("Số người lớn: %d x %,d đ",soNguoiLon,giaVe));
        tvSoTreEm.setText(String.format("Số trẻ em: %d x %,d đ", soTreEm,giaVe));
        tvTienNL.setText(String.format("%,d đ",soNguoiLon*giaVe));
        tvTienTE.setText(String.format("%,d đ",soTreEm*giaVe));

        int tongTien = (soNguoiLon + soTreEm) * giaVe;
        tvTongTien.setText(String.format("Tổng tiền: %,d đ", tongTien));
        Button btnDatve = view.findViewById(R.id.btn_datve);


        btnDatve.setOnClickListener(v -> {
            // Tạo Intent để mở PassengerInfoActivity
            Intent intent = new Intent(getContext(), ThongtinkhActivity.class);
            intent.putExtra("EXTRA_ADULTS", soNguoiLon);
            intent.putExtra("EXTRA_CHILDREN", soTreEm);
            startActivity(intent);
        });

        return view;
    }
}
