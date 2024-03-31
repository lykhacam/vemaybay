package com.example.kobietten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kobietten.R;
import com.example.kobietten.model.SanBay;
import java.util.List;

public class SanBayAdapter extends ArrayAdapter<SanBay> {
    public SanBayAdapter(Context context, List<SanBay> sanBayList) {
        super(context, 0, sanBayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.airport_item, parent, false);
        }
        SanBay sanBay = getItem(position);

        TextView tvTenSanBay = convertView.findViewById(R.id.tvTenSanBay);
        TextView tvMaSanBay = convertView.findViewById(R.id.tvMaSanBay);

        if (sanBay != null) {
            tvTenSanBay.setText(sanBay.getCityName());
            tvMaSanBay.setText(sanBay.getAirportCode());
        }

        return convertView;
    }
}
