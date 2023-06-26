package com.hieudx.fpoly.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.SachDAO;
import com.hieudx.fpoly.databinding.DialogEditSachBinding;
import com.hieudx.fpoly.databinding.ItemRcvSachBinding;
import com.hieudx.fpoly.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<Sach> mlist;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO dao;
    private DialogEditSachBinding editBinding;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDAO dao) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.listHM = listHM;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRcvSachBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rcv_sach, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvMaSach.setText("Mã sách: " + list.get(position).getMaSach());
        holder.binding.tvTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.binding.tvGiaThue.setText("Giá thuê: " + list.get(position).getGiaThue());
        holder.binding.tvMaLoai.setText("Mã loại: " + list.get(position).getMaLoai());
        holder.binding.tvTenLoai.setText("Tên loại: " + list.get(position).getTenLoai());

        holder.binding.tvTrang.setText("Số trang: " + list.get(position).getTrang());

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit(list.get(holder.getAdapterPosition()));
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = dao.delete(list.get(holder.getAdapterPosition()).getMaSach());
                switch (check) {
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                        builder.setNegativeButton("Có", (dialog, which) -> {
                            loadData();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        });
                        builder.setPositiveButton("Không", null);
                        builder.show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không thể xóa sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
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
                    ArrayList<Sach> listFilter = new ArrayList<>();
                    for (Sach sach : mlist) {
                        if (Integer.parseInt(search) >= sach.getTrang()) { // tìm theo số trang
                            listFilter.add(sach);
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
                list = (ArrayList<Sach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvSachBinding binding;

        public ViewHolder(@NonNull ItemRcvSachBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    private void showEdit(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_sach, null);
        editBinding = DialogEditSachBinding.inflate(inflater, view, false);

        builder.setView(editBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editBinding.tvMaSach.setText(sach.getMaSach() + "");
        editBinding.edTenSach.setText(sach.getTenSach());
        editBinding.edGiaThue.setText(String.valueOf(sach.getGiaThue()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1});
        editBinding.spnLoaiSach.setAdapter(simpleAdapter);
        int index = 0;
        int position = -1;
        for (HashMap<String, Object> item : listHM) {
            if ((int) item.get("maLoai") == sach.getMaLoai()) {
                position = index;
            }
            index++;
        }
        editBinding.spnLoaiSach.setSelection(position);

        editBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = editBinding.edTenSach.getText().toString();
                String giathue = editBinding.edGiaThue.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) editBinding.spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maLoai");

                if (tensach.isEmpty() || giathue.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dao.update(sach.getMaSach(), tensach, Integer.parseInt(giathue), maloai);
                    if (check) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
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
