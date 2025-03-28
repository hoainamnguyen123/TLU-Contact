package com.example.tlucontact;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.tlucontact.Contact.ContactListActivity;
import com.example.tlucontact.Unit.UnitListActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUnitContacts = findViewById(R.id.btn_unit_contacts);
        Button btnEmployeeContacts = findViewById(R.id.btn_employee_contacts);

        btnUnitContacts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UnitListActivity.class);
            startActivity(intent);
        });

        btnEmployeeContacts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
            startActivity(intent);
        });
    }
}



