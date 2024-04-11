package com.example.kobietten.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kobietten.R;
import com.example.kobietten.model.ChuyenBay;

import java.util.List;

public class LichSuDatVeAdapter extends RecyclerView.Adapter<LichSuDatVeAdapter.LichSuViewHolder> {

    private List<ChuyenBay> chuyenBayList;

    public LichSuDatVeAdapter(List<ChuyenBay> chuyenBayList) {
        this.chuyenBayList = chuyenBayList;
    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lich_su_dat_ve, parent, false);
        return new LichSuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, int position) {
        ChuyenBay chuyenBay = chuyenBayList.get(position);
        holder.bind(chuyenBay);
    }

    @Override
    public int getItemCount() {
        return chuyenBayList.size();
    }

    static class LichSuViewHolder extends RecyclerView.ViewHolder {
        TextView tvTgdi, tvDiemDi, tvDiemDen, tvNgayDi;

        LichSuViewHolder(View view) {
            super(view);
            tvTgdi = view.findViewById(R.id.tvTgdi);
            tvDiemDi = view.findViewById(R.id.tvDiemDi);
            tvDiemDen = view.findViewById(R.id.tvDiemDen);
            tvNgayDi = view.findViewById(R.id.tvNgayDi);
        }

        void bind(ChuyenBay chuyenBay) {

            tvTgdi.setText(String.format("Thời gian đi: %s", chuyenBay.getBatdau()));
            tvDiemDi.setText(String.format("Điểm đi: %s", chuyenBay.getDiemdi()));
            tvDiemDen.setText(String.format("Điểm đến: %s", chuyenBay.getDiemden()));
            tvNgayDi.setText(String.format("Ngày đi: %s", chuyenBay.getThoigian()));
        }
    }
}
