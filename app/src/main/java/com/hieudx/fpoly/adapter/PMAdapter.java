package com.hieudx.fpoly.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.PMDAO;
import com.hieudx.fpoly.databinding.DialogEditPmBinding;
import com.hieudx.fpoly.databinding.ItemRcvPmBinding;
import com.hieudx.fpoly.model.PhieuMuon;

import java.util.ArrayList;

//b2: extend rcv.adapter
public class PMAdapter extends RecyclerView.Adapter<PMAdapter.ViewHolder> {

    //    b3:
    private ArrayList<PhieuMuon> list;
    private Context context;
    private DialogEditPmBinding editBinding;

    public PMAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRcvPmBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rcv_pm, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvMaPM.setText("Mã PM: " + list.get(position).getMaPM());
        holder.binding.tvMaTV.setText("Mã Thành Viên: " + list.get(position).getMaTV());
        holder.binding.tvTenTV.setText("Tên Thành Viên: " + list.get(position).getTenTV());
        holder.binding.tvMaTT.setText("Mã Thủ Thư: " + list.get(position).getMaTT());
        holder.binding.tvTenTT.setText("Tên thủ thư: " + list.get(position).getTenTT());
        holder.binding.tvMaSach.setText("Mã Sách: " + list.get(position).getMaSach());
        holder.binding.tvTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.binding.tvNgay.setText("Ngày thuê: " + list.get(position).getNgay());

//        if (list.get(position).getTenSach().contains("M")) { // equalsIgnoreCase()
//            holder.binding.tvTenSach.setTypeface(null, Typeface.BOLD);
//
//        } else if (list.get(position).getTenSach().contains("N")) {
//            holder.binding.tvTenSach.setTypeface(null, Typeface.ITALIC);
//        } else {
//            String str = list.get(position).getTenSach();
//            holder.binding.tvTenSach.setText("Tên sách: " + str.toUpperCase(Locale.ROOT));
//        }
        String tt = "";
        if (list.get(position).getTraSach() == 1) {
            holder.binding.btnTraSach.setVisibility(View.GONE);
            tt = "đã trả sách";

        } else {
            holder.binding.btnTraSach.setVisibility(View.VISIBLE);
            tt = "chưa trả sách";
        }
        holder.binding.tvTrangThai.setText("Trạng Thái: " + tt);
        holder.binding.tvTienThue.setText("Giá thuê: " + list.get(position).getTienThue());

        holder.binding.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMDAO pmdao = new PMDAO(context);
                boolean kiemtra = pmdao.isTraSach(list.get(holder.getAdapterPosition()).getMaPM());
                if (kiemtra == true) {
                    list.clear();
                    list = pmdao.getData();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit(list.get(holder.getAdapterPosition()));
            }
        });
    }
    private void showEdit(PhieuMuon pm){
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_pm, null);
        editBinding = DialogEditPmBinding.inflate(inflater, view, false);

        builder.setView(editBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Tính năng chưa phát triển", Toast.LENGTH_SHORT).show();
            }
        });

        editBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // b1:
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvPmBinding binding;

        public ViewHolder(@NonNull ItemRcvPmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
