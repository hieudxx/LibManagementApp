package com.hieudx.fpoly.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.LoaiSachDAO;
import com.hieudx.fpoly.databinding.DialogEditLoaiSachBinding;
import com.hieudx.fpoly.databinding.ItemRcvLoaiSachBinding;
import com.hieudx.fpoly.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<LoaiSach> list;
    private ArrayList<LoaiSach> mlist;
    private DialogEditLoaiSachBinding editBinding;
    private LoaiSachDAO dao;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        this.mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRcvLoaiSachBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rcv_loai_sach, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvTenLoaiSach.setText("Tên loại sách: " + list.get(position).getTenLoai());
        holder.binding.tvMaLS.setText("Mã loại: " + String.valueOf(list.get(position).getMaLoai()));

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit(position);
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDelete(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<LoaiSach> listFilter = new ArrayList<>();
                    for (LoaiSach loaiSach : mlist) {
                        if (loaiSach.getTenLoai().toLowerCase().contains(search)) {
                            listFilter.add(loaiSach);
                        }
                    }
                    list = listFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<LoaiSach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvLoaiSachBinding binding;

        public ViewHolder(@NonNull ItemRcvLoaiSachBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void showDelete(int position) {
        dao = new LoaiSachDAO(context);
        int check = dao.delete(list.get(position).getMaLoai());
        switch (check) {
            case 1: //load lại danh sách
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    list.clear();
                    list = dao.getData();
                    notifyDataSetChanged();
                });
                builder.setPositiveButton("Không", null);
                builder.show();
                break;
            case -1: // có sách tồn tại
                Toast.makeText(context, "Không thể xóa loại sách này vì đã có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_loai_sach, null);
        editBinding = DialogEditLoaiSachBinding.inflate(inflater, view, false);

        builder.setView(editBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editBinding.edEditLoaiSach.setText(list.get(position).getTenLoai());

        editBinding.btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoai = editBinding.edEditLoaiSach.getText().toString();
                int id = list.get(position).getMaLoai();
                LoaiSach loaiSach = new LoaiSach(id, tenLoai);
                dao = new LoaiSachDAO(context);
                if (tenLoai.isEmpty()) { //tenLoai.matches("[a-zA-Z ]+")
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.edit(loaiSach)) {
                        list = dao.getData();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        editBinding.btnEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }
}
