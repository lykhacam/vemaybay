package com.example.kobietten.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kobietten.R;
import com.example.kobietten.adapter.ChuyenBayAdapter;
import com.example.kobietten.model.ChuyenBay;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private ListView lvFlights;
    private ChuyenBayAdapter adapter;
    private List<ChuyenBay> flightList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        lvFlights = findViewById(R.id.listView);
        adapter = new ChuyenBayAdapter(this, flightList);
        lvFlights.setAdapter(adapter);

        // Lấy dữ liệu từ intent
        String diemdi = getIntent().getStringExtra("DIEMDI");
        String diemden = getIntent().getStringExtra("DIEMDEN");
        String ngaydi = getIntent().getStringExtra("NGAYDI");
        int nguoilon = getIntent().getIntExtra("NGUOILON", 1);
        int treem = getIntent().getIntExtra("TREEM", 0);

        // Thực hiện truy vấn Firebase
        db.collection("chuyenbay")
                .whereEqualTo("diemdi", diemdi)
                .whereEqualTo("diemden", diemden)
                .whereEqualTo("thoigian", ngaydi)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChuyenBay flight = document.toObject(ChuyenBay.class);
                            flightList.add(flight);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Firestore", "Error getting documents: ", task.getException());
                    }
                });

        lvFlights.setOnItemClickListener((parent, view, position, id) -> {
            ChuyenBay selectedFlight = flightList.get(position);
            FlightDetailBottomSheetFragment bottomSheet = FlightDetailBottomSheetFragment.newInstance(selectedFlight, diemdi, diemden, ngaydi, nguoilon, treem);
            bottomSheet.show(getSupportFragmentManager(), "FlightDetailBottomSheetFragment");
        });
    }
}
