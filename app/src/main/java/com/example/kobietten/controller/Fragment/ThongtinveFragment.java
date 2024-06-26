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
import com.example.kobietten.model.ChuyenBay;

public class ThongtinveFragment extends BottomSheetDialogFragment {
    private static final String ARG_CHUYENBAY = "flight";
    private static final String ARG_DIEMDI = "diemDi";
    private static final String ARG_DIEMDEN = "diemDen";
    private static final String ARG_NGAYDI = "ngayDi";
    private static final String ARG_ADULTS = "soNguoiLon";
    private static final String ARG_CHILDREN = "soTreEm";
    private static final String ARG_EMAIL = "email";

    public static ThongtinveFragment newInstance(ChuyenBay chuyenBay, String diemDi, String diemDen, String ngayDi, int soNguoiLon, int soTreEm, String email) {
        ThongtinveFragment fragment = new ThongtinveFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHUYENBAY, chuyenBay);
        args.putString(ARG_DIEMDI, diemDi);
        args.putString(ARG_DIEMDEN, diemDen);
        args.putString(ARG_NGAYDI, ngayDi);
        args.putInt(ARG_ADULTS, soNguoiLon);
        args.putInt(ARG_CHILDREN, soTreEm);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chuyenbay, container, false);
        TextView tvMachuyenbay = view.findViewById(R.id.tv_machuyenbay);
        TextView tvThoigianbay = view.findViewById(R.id.tv_thoigian);
        TextView tvTongtien = view.findViewById(R.id.tv_tongtien);
        Button btnDatve = view.findViewById(R.id.btn_datve);
        TextView tvChitietgia = view.findViewById(R.id.tv_chitietgia);

        // Nhận ChuyenBay và số người lớn/trẻ em từ Bundle
        ChuyenBay chuyenBay = (ChuyenBay) getArguments().getSerializable(ARG_CHUYENBAY);
        int soNguoiLon = getArguments().getInt(ARG_ADULTS);
        int soTreEm = getArguments().getInt(ARG_CHILDREN);
        String email = getArguments().getString(ARG_EMAIL);
        int tongtien = (soTreEm+soNguoiLon) * chuyenBay.getTienve();
        if (chuyenBay != null) {
            tvMachuyenbay.setText(chuyenBay.getTenhang());
            tvThoigianbay.setText(String.format("%s - %s", chuyenBay.getBatdau(), chuyenBay.getKetthuc()));
            tvTongtien.setText(String.format("Tổng: %,d đ", tongtien));
        }

        tvChitietgia.setOnClickListener(v -> {
            // Mở ChiTietGiaDialogFragment, với các giá trị số người lớn và trẻ em
            ChiTietGiaFragment chiTietGiaDialog = ChiTietGiaFragment.newInstance(soNguoiLon, soTreEm, chuyenBay.getTienve(), email);
            chiTietGiaDialog.show(getParentFragmentManager(), "ChiTietGiaFragment");
        });

        btnDatve.setOnClickListener(v -> {

            // Tạo Intent để mở PassengerInfoActivity
            Intent intent = new Intent(getContext(), ThongtinkhActivity.class);
            intent.putExtra("EXTRA_DIEMDI", getArguments().getString(ARG_DIEMDI));
            intent.putExtra("EXTRA_DIEMDEN", getArguments().getString(ARG_DIEMDEN));
            intent.putExtra("EXTRA_NGAYDI", getArguments().getString(ARG_NGAYDI));
            intent.putExtra("EXTRA_ADULTS", soNguoiLon);
            intent.putExtra("EXTRA_CHILDREN", soTreEm);
            intent.putExtra("EXTRA_EMAIL", getArguments().getString(ARG_EMAIL));
            startActivity(intent);
        });
        return view;
    }
}
