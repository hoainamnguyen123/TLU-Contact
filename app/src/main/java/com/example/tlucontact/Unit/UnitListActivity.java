package com.example.tlucontact.Unit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlucontact.Contact.Contact;
import com.example.tlucontact.Contact.ContactListActivity;
import com.example.tlucontact.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// UnitListActivity.java
public class UnitListActivity extends AppCompatActivity {
    private List<Unit> unitList;
    private UnitAdapter unitAdapter;
    private EditText searchEditText;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);

        databaseReference = FirebaseDatabase.getInstance().getReference("units");

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.unit_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progress_bar);

        // Khởi tạo dữ liệu và adapter
        unitList = new ArrayList<>();
        unitAdapter = new UnitAdapter(unitList, this::showUnitDetails);
        recyclerView.setAdapter(unitAdapter);

        // Thiết lập thanh tìm kiếm
        searchEditText = findViewById(R.id.search_edittext);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lọc danh sách khi văn bản thay đổi
                unitAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });
        loadUnitsFromFirebase();
    }

    private void loadUnitsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unitList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Unit unit = snapshot.getValue(Unit.class);
                    if (unit != null) {
                        unitList.add(unit);
                    }
                }

                unitAdapter.setUnits(unitList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UnitListActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showUnitDetails(Unit unit) {
        Intent intent = new Intent(this, UnitDetailActivity.class);
        intent.putExtra("UNIT_NAME", unit.getName());
        intent.putExtra("UNIT_PHONE", unit.getPhone());
        intent.putExtra("UNIT_ADDRESS", unit.getAddress());
        startActivity(intent);
    }
}