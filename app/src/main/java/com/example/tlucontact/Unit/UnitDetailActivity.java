package com.example.tlucontact.Unit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlucontact.R;

// UnitDetailActivity.java
public class UnitDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_detail);

        TextView nameTextView = findViewById(R.id.detail_unit_name);
        TextView phoneTextView = findViewById(R.id.detail_unit_phone);
        TextView addressTextView = findViewById(R.id.detail_unit_address);

        // Get intent extras
        String name = getIntent().getStringExtra("UNIT_NAME");
        String phone = getIntent().getStringExtra("UNIT_PHONE");
        String address = getIntent().getStringExtra("UNIT_ADDRESS");

        // Set text views
        nameTextView.setText(name);
        phoneTextView.setText(phone);
        addressTextView.setText(address);

        // Back button
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        // Call button action
        ImageButton btnCall = findViewById(R.id.btn_call_unit);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Map button action
        ImageButton btnMap = findViewById(R.id.btn_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressText = addressTextView.getText().toString();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(addressText));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Nếu không có Google Maps, mở bằng trình duyệt web
                    Uri webIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(addressText));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webIntentUri);
                    startActivity(webIntent);
                }
            }
        });
    }
}