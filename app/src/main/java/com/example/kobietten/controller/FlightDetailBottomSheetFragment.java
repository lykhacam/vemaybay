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
import com.example.kobietten.model.ChuyenBay;

public class FlightDetailBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String ARG_FLIGHT = "flight";
    private static final String ARG_DIEMDI = "diemDi";
    private static final String ARG_DIEMDEN = "diemDen";
    private static final String ARG_NGAYDI = "ngayDi";

    private static final String ARG_ADULTS = "soNguoiLon";
    private static final String ARG_CHILDREN = "soTreEm";

    public static FlightDetailBottomSheetFragment newInstance(ChuyenBay chuyenBay, String diemDi, String diemDen, String ngayDi, int soNguoiLon, int soTreEm) {
        FlightDetailBottomSheetFragment fragment = new FlightDetailBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FLIGHT, chuyenBay);
        args.putString(ARG_DIEMDI, diemDi);
        args.putString(ARG_DIEMDEN, diemDen);
        args.putString(ARG_NGAYDI, ngayDi);
        args.putInt(ARG_ADULTS, soNguoiLon);
        args.putInt(ARG_CHILDREN, soTreEm);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_detail, container, false);
        TextView tvFlightName = view.findViewById(R.id.tvFlightName);
        TextView tvFlightTimes = view.findViewById(R.id.tvFlightTimes);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        Button btnDatve = view.findViewById(R.id.btnBookFlight);
        TextView tvPriceDetails = view.findViewById(R.id.tvPriceDetails);

        // Nhận ChuyenBay và số người lớn/trẻ em từ Bundle
        ChuyenBay chuyenBay = (ChuyenBay) getArguments().getSerializable(ARG_FLIGHT);
        int soNguoiLon = getArguments().getInt(ARG_ADULTS);
        int soTreEm = getArguments().getInt(ARG_CHILDREN);

        if (chuyenBay != null) {
            tvFlightName.setText(chuyenBay.getTenhang());
            tvFlightTimes.setText(String.format("%s - %s", chuyenBay.getBatdau(), chuyenBay.getKetthuc()));
            tvTotalPrice.setText(String.format("Tổng: %,d đ", (soTreEm+soNguoiLon) * chuyenBay.getTienve()));
        }

        tvPriceDetails.setOnClickListener(v -> {
            // Đây là nơi chúng ta mở ChiTietGiaDialogFragment, với các giá trị số người lớn và trẻ em
            ChiTietGia chiTietGiaDialog = ChiTietGia.newInstance(soNguoiLon, soTreEm, chuyenBay.getTienve());
            chiTietGiaDialog.show(getParentFragmentManager(), "ChiTietGiaDialogFragment");
        });

        btnDatve.setOnClickListener(v -> {
            // Tạo Intent để mở PassengerInfoActivity
            Intent intent = new Intent(getContext(), PassengerInfoActivity.class);
            // Bạn cũng có thể muốn truyền thêm số lượng người lớn và trẻ em tới PassengerInfoActivity
            intent.putExtra("EXTRA_DIEMDI", getArguments().getString(ARG_DIEMDI));
            intent.putExtra("EXTRA_DIEMDEN", getArguments().getString(ARG_DIEMDEN));
            intent.putExtra("EXTRA_NGAYDI", getArguments().getString(ARG_NGAYDI));
            intent.putExtra("EXTRA_ADULTS", soNguoiLon);
            intent.putExtra("EXTRA_CHILDREN", soTreEm);
            startActivity(intent);
        });
        return view;
    }
}
