package com.example.kobietten.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kobietten.R;

public class HotroActivity extends AppCompatActivity {
    TextView call,sms,tvsdt;
    LinearLayout lncskh,lncsvbm,lndangxuat;
    ImageView ivBars;
    DrawerLayout drawerLayout;
    ImageView ivmap;
    private static final int REQUEST_PHONE_CALL = 1;
    private String phoneNumber = "0388832406";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotro);
        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        tvsdt = findViewById(R.id.tvsdt);
        ivmap = findViewById(R.id.ivmap);
        drawerLayout = findViewById(R.id.drawlayout);
        ivBars = findViewById(R.id.ivBars);
        lncskh = findViewById(R.id.lncskh);
        lncsvbm = findViewById(R.id.lncsvbm);
        lndangxuat = findViewById(R.id.lndangxuat);


        ivBars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        lncskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HotroActivity.this, HotroActivity.class);
            }
        });
        lncsvbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotroActivity.this,ChinhsachActivity.class);
                startActivity(intent);
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
                        Intent intent = new Intent(HotroActivity.this, DangnhapActivity.class);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                return;
            }
        }

        // Gọi phương thức để thực hiện cuộc gọi
        makePhoneCall();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = tvsdt.getText().toString();
                Intent intent_call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+sdt));
                startActivity(intent_call);
            }
        });

    }
    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                // Quyền không được cấp, xử lý tương ứng (ví dụ: thông báo cho người dùng)
            }
        }
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