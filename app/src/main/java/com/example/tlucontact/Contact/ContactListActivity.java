package com.example.tlucontact.Contact;

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

import com.example.tlucontact.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private List<Contact> contactList;
    private ContactAdapter contactAdapter;
    private EditText searchEditText;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Khởi tạo Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("contacts");

        // Khởi tạo ProgressBar
        progressBar = findViewById(R.id.progress_bar);

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách trống và adapter
        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this::showContactDetails);
        recyclerView.setAdapter(contactAdapter);

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
                contactAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });

        // Tải dữ liệu từ Firebase
        loadContactsFromFirebase();
    }

    private void loadContactsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contact contact = snapshot.getValue(Contact.class);
                    if (contact != null) {
                        contactList.add(contact);
                    }
                }

                contactAdapter.setContacts(contactList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ContactListActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showContactDetails(Contact contact) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("CONTACT_NAME", contact.getName());
        intent.putExtra("CONTACT_PHONE", contact.getPhone());
        intent.putExtra("CONTACT_EMAIL", contact.getEmail());
        intent.putExtra("CONTACT_POSITION", contact.getPosition());
        intent.putExtra("CONTACT_UNIT", contact.getUnit());
        startActivity(intent);
    }
}