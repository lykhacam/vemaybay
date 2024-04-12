package com.example.kobietten.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kobietten.R;

public class ChinhsachActivity extends AppCompatActivity {
    LinearLayout lncskh,lncsvbm,lndangxuat,lnhome;
    ImageView ivBars;
    DrawerLayout drawerLayout;
    private Button btnDiemdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chinhsach);
        drawerLayout = findViewById(R.id.drawlayout);
        ivBars = findViewById(R.id.ivBars);
        lncskh = findViewById(R.id.lncskh);
        lncsvbm = findViewById(R.id.lncsvbm);
        lndangxuat = findViewById(R.id.lndangxuat);
        lnhome = findViewById(R.id.lnhome);



        ivBars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        lncskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChinhsachActivity.this,HotroActivity.class);
                startActivity(intent);
            }
        });
        lnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChinhsachActivity.this,ManhinhchinhActivity.class);
                startActivity(intent);
            }
        });
        lncsvbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChinhsachActivity.this, ChinhsachActivity.class);
            }
        });

        lndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(R.layout.signout);

                final AlertDialog dialog = builder.create();
                dialog.show();

                Button bYes = dialog.findViewById(R.id.bYes);
                Button bNo = dialog.findViewById(R.id.bNo);

                bYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChinhsachActivity.this, DangnhapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                bNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}