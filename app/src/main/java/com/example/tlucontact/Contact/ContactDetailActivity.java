package com.example.tlucontact.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlucontact.R;

public class ContactDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        TextView nameTextView = findViewById(R.id.detail_contact_name);
        TextView phoneTextView = findViewById(R.id.detail_contact_phone);
        TextView emailTextView = findViewById(R.id.detail_contact_email);
        TextView positionTextView = findViewById(R.id.detail_contact_position);
        TextView unitTextView = findViewById(R.id.detail_contact_unit);

        // Get intent extras
        String name = getIntent().getStringExtra("CONTACT_NAME");
        String phone = getIntent().getStringExtra("CONTACT_PHONE");
        String email = getIntent().getStringExtra("CONTACT_EMAIL");
        String position = getIntent().getStringExtra("CONTACT_POSITION");
        String unit = getIntent().getStringExtra("CONTACT_UNIT");

        // Set text views
        nameTextView.setText(name);
        phoneTextView.setText(phone);
        emailTextView.setText(email);
        positionTextView.setText(position);
        unitTextView.setText(unit);

        // Back button action
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        // Call button action
        ImageButton btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Message button action
        ImageButton btnMessage = findViewById(R.id.btn_message);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Email button action
        ImageButton btnEmail = findViewById(R.id.btn_email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + emailAddress));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
        });
    }
}