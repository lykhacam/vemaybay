package com.example.kobietten.controller.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kobietten.R;

public class HotroActivity extends AppCompatActivity {
    TextView call,sms,tvsdt;
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
        ivmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.google.com/maps/place/%C4%90%E1%BA%A1i+H%E1%BB%8Dc+Thu%E1%BB%B7+L%E1%BB%A3i+-+175+T%C3%A2y+S%C6%A1n+(C%E1%BB%99t+Sau)/@21.007651,105.8238196,15z/data=!4m6!3m5!1s0x3135ac81847527d9:0x608eb25e26856d92!8m2!3d21.007651!4d105.8238196!16s%2Fg%2F1hhx6qmkx?entry=ttu");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
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
}