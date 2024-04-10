package com.example.kobietten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.kobietten.R;
import com.example.kobietten.model.ChuyenBay;
import java.util.List;

public class ChuyenBayAdapter extends ArrayAdapter<ChuyenBay> {
    public ChuyenBayAdapter(Context context, List<ChuyenBay> flights) {
        super(context, 0, flights);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem view hiện tại có được tái sử dụng không, nếu không thì inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chuyenbay, parent, false);
        }

        // Lấy đối tượng 'Flight' tại vị trí này
        ChuyenBay chuyenbay = getItem(position);

        // Tìm TextView và đặt giá trị từ đối tượng 'ChuyenBay'
        TextView tvTgdi= convertView.findViewById(R.id.tv_tgdi);
        TextView tvTgden = convertView.findViewById(R.id.tv_tgden);
        TextView tvMachuyenbay = convertView.findViewById(R.id.tv_machuyenbay);
        TextView tvGiatien = convertView.findViewById(R.id.tv_giatien);

        // Điền dữ liệu vào các TextView (hoặc bất kỳ view nào khác trong layout của bạn)
        if (chuyenbay != null) {
            tvTgdi.setText(chuyenbay.getBatdau());
            tvTgden.setText(chuyenbay.getKetthuc());
            tvMachuyenbay.setText(chuyenbay.getTenhang()); // Hoặc số hiệu chuyến bay nếu bạn có trường đó
            tvGiatien.setText(String.format("%,d đ", chuyenbay.getTienve()));
        }

        return convertView;
    }
}
