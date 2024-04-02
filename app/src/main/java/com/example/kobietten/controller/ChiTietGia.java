package com.example.kobietten.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.kobietten.R;

public class ChiTietGia extends BottomSheetDialogFragment {

    public ChiTietGia() {
        // Constructor mặc định
    }
    public static ChiTietGia newInstance(int soNguoiLon, int soTreEm, int giaVe) {
        ChiTietGia fragment = new ChiTietGia();
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
        View view = inflater.inflate(R.layout.chitietgia, container, false);

        // Lấy thông tin từ Bundle
        int soNguoiLon = getArguments().getInt("soNguoiLon");
        int soTreEm = getArguments().getInt("soTreEm");
        int giaVe = getArguments().getInt("giaVe");

        // Cập nhật thông tin chi tiết giá vé
        // Ví dụ: Hiển thị số lượng và giá vé
        TextView tvSoNguoiLon = view.findViewById(R.id.tvNguoilon);
        TextView tvSoTreEm = view.findViewById(R.id.tvTreem);
        TextView tvTienNL = view.findViewById(R.id.tvTienNgL);
        TextView tvTienTE = view.findViewById(R.id.tvTienTE);
        TextView tvTongTien = view.findViewById(R.id.tvTongtien);

        tvSoNguoiLon.setText(String.format("Số người lớn: %d x %,d đ",soNguoiLon,giaVe));
        tvSoTreEm.setText(String.format("Số trẻ em: %d x %,d đ", soTreEm,giaVe));
        tvTienNL.setText(String.format("%,d đ",soNguoiLon*giaVe));
        tvTienTE.setText(String.format("%,d đ",soTreEm*giaVe));

        int tongTien = (soNguoiLon + soTreEm) * giaVe;
        tvTongTien.setText(String.format("Tổng tiền: %,d đ", tongTien));
        Button btnDatve = view.findViewById(R.id.btnDatve); // Giả sử bạn có một Button với id là btnDatVe


        btnDatve.setOnClickListener(v -> {
            // Tạo Intent để mở PassengerInfoActivity
            Intent intent = new Intent(getContext(), PassengerInfoActivity.class);
            // Bạn cũng có thể muốn truyền thêm số lượng người lớn và trẻ em tới PassengerInfoActivity
            intent.putExtra("EXTRA_ADULTS", soNguoiLon);
            intent.putExtra("EXTRA_CHILDREN", soTreEm);
            startActivity(intent);
        });

        return view;
    }
}
