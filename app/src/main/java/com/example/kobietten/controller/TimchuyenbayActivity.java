package com.example.kobietten.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kobietten.R;
import com.example.kobietten.adapter.ChuyenBayAdapter;
import com.example.kobietten.controller.Fragment.ThongtinveFragment;
import com.example.kobietten.model.ChuyenBay;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TimchuyenbayActivity extends AppCompatActivity {
    private ListView lvFlights;
    private TextView tvKochuyen;
    private ChuyenBayAdapter adapter;
    private List<ChuyenBay> flightList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dschuyen);
        lvFlights = findViewById(R.id.listView);
        tvKochuyen = findViewById(R.id.tv_kochuyen); // Thêm tham chiếu đến TextView mới
        adapter = new ChuyenBayAdapter(this, flightList);
        lvFlights.setAdapter(adapter);

        // Lấy dữ liệu từ intent
        String diemdi = getIntent().getStringExtra("EXTRA_DIEMDI");
        String diemden = getIntent().getStringExtra("EXTRA_DIEMDEN");
        String ngaydi = getIntent().getStringExtra("EXTRA_NGAYDI");
        int nguoilon = getIntent().getIntExtra("EXTRA_NGUOILON", 1);
        int treem = getIntent().getIntExtra("EXTRA_TREEM", 0);
        String email = getIntent().getStringExtra("EXTRA_EMAIL");

        // Thực hiện truy vấn Firebase
        db.collection("chuyenbay")
                .whereEqualTo("diemdi", diemdi)
                .whereEqualTo("diemden", diemden)
                .whereEqualTo("thoigian", ngaydi)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        flightList.clear(); // Xóa danh sách hiện tại trước khi thêm mới
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChuyenBay flight = document.toObject(ChuyenBay.class);
                            flightList.add(flight);
                        }
                        adapter.notifyDataSetChanged();
                        tvKochuyen.setVisibility(View.GONE); // Ẩn TextView khi có dữ liệu
                    } else {
                        tvKochuyen.setVisibility(View.VISIBLE); // Hiển thị TextView khi không có dữ liệu
                    }
                });

        lvFlights.setOnItemClickListener((parent, view, position, id) -> {
            ChuyenBay selectedFlight = flightList.get(position);
            ThongtinveFragment bottomSheet = ThongtinveFragment.newInstance(selectedFlight, diemdi, diemden, ngaydi, nguoilon, treem, email);
            bottomSheet.show(getSupportFragmentManager(), "ThongtinveFragment");
        });
    }
}
