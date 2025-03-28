package com.example.tlucontact.Contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlucontact.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactList;
    private List<Contact> contactListFull; // Danh sách đầy đủ để lọc
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public ContactAdapter(List<Contact> contactList, OnItemClickListener listener) {
        this.contactList = contactList;
        this.contactListFull = new ArrayList<>(contactList); // Tạo bản sao của danh sách đầy đủ
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());
        holder.unitTextView.setText(contact.getUnit());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(contact));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Phương thức để cập nhật danh sách từ Firebase
    public void setContacts(List<Contact> contacts) {
        this.contactList = new ArrayList<>(contacts);
        this.contactListFull = new ArrayList<>(contacts);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        contactList.clear();
        if (text.isEmpty()) {
            contactList.addAll(contactListFull);
        } else {
            text = text.toLowerCase();
            for (Contact contact : contactListFull) {
                if (contact.getName().toLowerCase().contains(text) ||
                        contact.getUnit().toLowerCase().contains(text) ||
                        contact.getPosition().toLowerCase().contains(text)) {
                    contactList.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, unitTextView;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contact_name);
            phoneTextView = itemView.findViewById(R.id.contact_phone);
            unitTextView = itemView.findViewById(R.id.contact_unit);
        }
    }
}