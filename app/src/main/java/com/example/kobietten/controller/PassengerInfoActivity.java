package com.example.kobietten.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kobietten.R;

public class PassengerInfoActivity extends Activity {
    private LinearLayout llAdultInfoContainer, llChildInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info);

        llAdultInfoContainer = findViewById(R.id.llAdultInfoContainer);
        llChildInfoContainer = findViewById(R.id.llChildInfoContainer);

        findViewById(R.id.btnToggleAdultInfo).setOnClickListener(v -> toggleInfo(llAdultInfoContainer));
        findViewById(R.id.btnToggleChildInfo).setOnClickListener(v -> toggleInfo(llChildInfoContainer));

        // Giả định bạn đã nhận số người lớn và trẻ em từ Intent

        int numAdults = getIntent().getIntExtra("EXTRA_ADULTS", 1);
        int numChildren = getIntent().getIntExtra("EXTRA_CHILDREN", 0);

        addPassengerForms(llAdultInfoContainer, R.layout.passenger_adult, numAdults);
        addPassengerForms(llChildInfoContainer, R.layout.passenger_child, numChildren);
    }

    private void toggleInfo(LinearLayout layout) {
        layout.setVisibility(layout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void addPassengerForms(LinearLayout container, int layoutId, int count) {
        LayoutInflater inflater = LayoutInflater.from(this);
        container.removeAllViews();
        for (int i = 0; i < count; i++) {
            inflater.inflate(layoutId, container, true);
        }
    }
}
