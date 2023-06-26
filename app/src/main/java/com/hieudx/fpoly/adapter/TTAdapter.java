package com.hieudx.fpoly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.ThuThuDao;
import com.hieudx.fpoly.databinding.DialogEditTtBinding;
import com.hieudx.fpoly.databinding.ItemRcvTtBinding;
import com.hieudx.fpoly.model.ThuThu;

import java.util.ArrayList;

public class TTAdapter extends RecyclerView.Adapter<TTAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThuThu> list;
    private ThuThuDao dao;
    private DialogEditTtBinding editBinding;

    public TTAdapter(Context context, ArrayList<ThuThu> list, ThuThuDao dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRcvTtBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rcv_tt, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvMaTT.setText("Mã thủ thư: " + list.get(position).getMaTT());
        holder.binding.tvhoTen.setText("Họ tên: " + list.get(position).getHoTen());
        holder.binding.tvVaiTro.setText("Vai trò: " + list.get(position).getVaiTro());

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvTtBinding binding;

        public ViewHolder(@NonNull ItemRcvTtBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
