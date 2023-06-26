package com.hieudx.fpoly.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.ThanhVienDAO;
import com.hieudx.fpoly.databinding.DialogEditLoaiSachBinding;
import com.hieudx.fpoly.databinding.DialogEditTvBinding;
import com.hieudx.fpoly.databinding.ItemRcvTvBinding;
import com.hieudx.fpoly.model.ThanhVien;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO dao;
    private DialogEditTvBinding editBinding;

    public TVAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRcvTvBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rcv_tv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.tvMaTV.setText("Mã thành viên: " + list.get(position).getMaTV());
        holder.binding.tvhoTen.setText("Họ tên: " + list.get(position).getHoTen());
        holder.binding.tvNamSinh.setText("Năm sinh: " + list.get(position).getNamSinh());
        holder.binding.tvTien.setText("Tiền: "+list.get(position).getTien());

        if (list.get(position).getTien() > 1000){
            holder.binding.tvTien.setTypeface(null, Typeface.BOLD);
        }

        //        if (list.get(position).getTenSach().contains("M")) { // equalsIgnoreCase()
//            holder.binding.tvTenSach.setTypeface(null, Typeface.BOLD);
//
//        } else if (list.get(position).getTenSach().contains("N")) {
//            holder.binding.tvTenSach.setTypeface(null, Typeface.ITALIC);
//        } else {
//            String str = list.get(position).getTenSach();
//            holder.binding.tvTenSach.setText("Tên sách: " + str.toUpperCase(Locale.ROOT));
//        }

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit(list.get(holder.getAdapterPosition()));
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao = new ThanhVienDAO(context);
                int check = dao.delete(list.get(holder.getAdapterPosition()).getMaTV());
                switch (check) {
                    case 1:

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                        builder.setNegativeButton("Có", (dialog, which) -> {
                            list.clear();
                            list = dao.getData();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        });
                        builder.setPositiveButton("Không", null);
                        builder.show();

//                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành viên đang có phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvTvBinding binding;

        public ViewHolder(@NonNull ItemRcvTvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void showDelete() {

    }

    private void showEdit(ThanhVien tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_loai_sach, null);
        editBinding = DialogEditTvBinding.inflate(inflater, view, false);

        builder.setView(editBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editBinding.tvMaTV.setText(tv.getMaTV() + "");
        editBinding.edEditHoTen.setText(tv.getHoTen());
        editBinding.edEditNamSinh.setText(tv.getNamSinh());

        editBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = editBinding.edEditHoTen.getText().toString();
                String namsinh = editBinding.edEditNamSinh.getText().toString();
                int id = tv.getMaTV();

                if ((hoten.equals("") || namsinh.equals(""))) {
//            (hoten.matches("[a-zA-Z ]+") || namsinh.matches("[a-zA-Z ]+")
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dao.update(id, hoten, namsinh);
                    if (check) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        editBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void loadData() {
        list.clear();
        list = dao.getData();
        notifyDataSetChanged();
    }
}
