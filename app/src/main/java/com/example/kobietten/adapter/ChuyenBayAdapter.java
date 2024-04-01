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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flight_item, parent, false);
        }

        // Lấy đối tượng 'Flight' tại vị trí này
        ChuyenBay chuyenbay = getItem(position);

        // Tìm TextView và đặt giá trị từ đối tượng 'Flight'
        TextView tvDepartureTime = convertView.findViewById(R.id.tvDepartureTime);
        TextView tvArrivalTime = convertView.findViewById(R.id.tvArrivalTime);
        TextView tvFlightNumber = convertView.findViewById(R.id.tvFlightNumber);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        // Điền dữ liệu vào các TextView (hoặc bất kỳ view nào khác trong layout của bạn)
        if (chuyenbay != null) {
            tvDepartureTime.setText(chuyenbay.getBatdau());
            tvArrivalTime.setText(chuyenbay.getKetthuc());
            tvFlightNumber.setText(chuyenbay.getTenhang()); // Hoặc số hiệu chuyến bay nếu bạn có trường đó
            tvPrice.setText(String.format("$%s", chuyenbay.getTienve()));
        }

        return convertView;
    }
}
