package com.example.kobietten.giaodien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;

public class Facilities extends AppCompatActivity {

    private CheckBox checkboxWifi, checkboxBaggage, checkboxPowerUSB, checkboxInFlightMeal;
    private Button buttonSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facilities_layout);

        // Initialize CheckBoxes
        checkboxWifi = findViewById(R.id.checkboxWifi);
        checkboxBaggage = findViewById(R.id.checkboxBaggage);
        checkboxPowerUSB = findViewById(R.id.checkboxPowerUSB);
        checkboxInFlightMeal = findViewById(R.id.checkboxInFlightMeal);

        // Initialize the Select All button
        buttonSelectAll = findViewById(R.id.buttonSelectAll);

        // Set the onClickListener for the Select All button
        buttonSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine if all checkboxes are currently checked
                boolean shouldCheckAll = !(checkboxWifi.isChecked() && checkboxBaggage.isChecked() &&
                        checkboxPowerUSB.isChecked() && checkboxInFlightMeal.isChecked());

                // Set the state for all checkboxes
                checkboxWifi.setChecked(shouldCheckAll);
                checkboxBaggage.setChecked(shouldCheckAll);
                checkboxPowerUSB.setChecked(shouldCheckAll);
                checkboxInFlightMeal.setChecked(shouldCheckAll);
            }
        });
    }
}
