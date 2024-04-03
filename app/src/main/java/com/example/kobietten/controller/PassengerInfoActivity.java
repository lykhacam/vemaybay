package com.example.kobietten.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kobietten.R;

public class PassengerInfoActivity extends Activity {
    private LinearLayout llAdultInfoContainer, llChildInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info);

        llAdultInfoContainer = findViewById(R.id.llAdultInfoContainer);
        llChildInfoContainer = findViewById(R.id.llChildInfoContainer);

        // Lấy số lượng người lớn và trẻ em từ Intent
        Intent intent = getIntent();
        int numAdults = intent.getIntExtra("EXTRA_ADULTS", 1);
        int numChildren = intent.getIntExtra("EXTRA_CHILDREN", 0);

        // Hiển thị form thông tin hành khách
        for (int i = 0; i < numAdults; i++) {
            addPassengerView(llAdultInfoContainer, "Người lớn " + (i + 1));
        }
        for (int i = 0; i < numChildren; i++) {
            addPassengerView(llChildInfoContainer, "Trẻ em " + (i + 1));
        }
    }

    private void addPassengerView(LinearLayout container, String title) {
        View passengerView = getLayoutInflater().inflate(R.layout.passenger_info_item, container, false);

        TextView tvTitle = passengerView.findViewById(R.id.tvPassengerTitle);
        LinearLayout llDetails = passengerView.findViewById(R.id.llPassengerDetails);

        tvTitle.setText(title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the details section
                if (llDetails.getVisibility() == View.VISIBLE) {
                    llDetails.setVisibility(View.GONE);
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more, 0);
                } else {
                    llDetails.setVisibility(View.VISIBLE);
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less, 0);
                }
            }
        });

        container.addView(passengerView);
    }
}
