package com.example.kobietten.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;
import com.example.kobietten.adapter.SanBayAdapter;
import com.example.kobietten.model.SanBay;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiemdiActivity extends AppCompatActivity {
    private ListView listView;
    private SanBayAdapter adapter;
    private List<SanBay> listSanBay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoihanh);

        listView = findViewById(R.id.listView);
        adapter = new SanBayAdapter(this, listSanBay);
        listView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sanbay")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            SanBay sanBay = document.toObject(SanBay.class);
                            listSanBay.add(sanBay);

                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Firestore", "Error getting documents: ", task.getException());
                    }
                });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            SanBay selectedSanBay = adapter.getItem(position);
            if (selectedSanBay != null) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("selectedLocation", selectedSanBay.getAirportCode());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
