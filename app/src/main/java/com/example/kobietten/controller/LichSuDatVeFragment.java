package com.example.kobietten.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kobietten.R;
import com.example.kobietten.adapter.LichSuDatVeAdapter;
import com.example.kobietten.model.ChuyenBay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class LichSuDatVeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LichSuDatVeAdapter adapter;
    private List<ChuyenBay> chuyenBayList;

    public LichSuDatVeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_lich_su_dat_ve, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewLichSuDatVe);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        chuyenBayList = new ArrayList<>();
        adapter = new LichSuDatVeAdapter(chuyenBayList);
        recyclerView.setAdapter(adapter);

        loadLichSuDatVe();
    }

    private void loadLichSuDatVe() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("chuyenBay");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chuyenBayList.clear(); // Clear existing data
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ChuyenBay chuyenBay = postSnapshot.getValue(ChuyenBay.class);
                    chuyenBayList.add(chuyenBay);
                }
                adapter.notifyDataSetChanged(); // Refresh data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });
    }
}
