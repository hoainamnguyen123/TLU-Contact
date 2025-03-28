package com.example.tlucontact.Unit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlucontact.R;

import java.util.ArrayList;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {
    private List<Unit> unitList;
    private List<Unit> unitListFull; // Danh sách đầy đủ để lọc
    private OnItemClickListener listener;

    public void setUnits(List<Unit> unitList) {
        this.unitList = new ArrayList<>(unitList);
        this.unitListFull= new ArrayList<>(unitList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Unit unit);
    }

    public UnitAdapter(List<Unit> unitList, OnItemClickListener listener) {
        this.unitList = unitList;
        this.unitListFull = new ArrayList<>(unitList); // Tạo bản sao của danh sách đầy đủ
        this.listener = listener;
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unit, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        Unit unit = unitList.get(position);
        holder.nameTextView.setText(unit.getName());
        holder.phoneTextView.setText(unit.getPhone());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(unit));
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    // Phương thức lọc theo tên đơn vị
    public void filter(String text) {
        unitList.clear();
        if (text.isEmpty()) {
            unitList.addAll(unitListFull);
        } else {
            text = text.toLowerCase();
            for (Unit unit : unitListFull) {
                if (unit.getName().toLowerCase().contains(text)) {
                    unitList.add(unit);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class UnitViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.unit_name);
            phoneTextView = itemView.findViewById(R.id.unit_phone);
        }
    }
}