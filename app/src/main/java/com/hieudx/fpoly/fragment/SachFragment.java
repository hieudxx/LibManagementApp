package com.hieudx.fpoly.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hieudx.fpoly.adapter.SachAdapter;
import com.hieudx.fpoly.dao.LoaiSachDAO;
import com.hieudx.fpoly.dao.SachDAO;
import com.hieudx.fpoly.databinding.DialogAddSachBinding;
import com.hieudx.fpoly.databinding.FragmentSachBinding;
import com.hieudx.fpoly.model.LoaiSach;
import com.hieudx.fpoly.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;


public class SachFragment extends Fragment {
    private FragmentSachBinding binding;
    private DialogAddSachBinding dialogBinding;
    private ArrayList<Sach> list;

    private SachDAO dao;
    private SachAdapter adapter;

    public static Fragment newInstance() {
        SachFragment fragment = new SachFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSachBinding.inflate(inflater, container, false);

        loadData();
        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(inflater, container);
            }
        });

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return binding.getRoot();
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
        builder.setCancelable(false);
        dialogBinding = dialogBinding.inflate(inflater, container, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1});
        dialogBinding.spnLoaiSach.setAdapter(simpleAdapter);
        dialogBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = dialogBinding.edTenSach.getText().toString();
                String giathue = dialogBinding.edGiaThue.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) dialogBinding.spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maLoai");

                if (tensach.isEmpty() || giathue.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dao.insert(tensach, Integer.parseInt(giathue), maloai);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                }
            }
        });

        dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public ArrayList<HashMap<String, Object>> getLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getData();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach loai : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", loai.getMaLoai());
            hs.put("tenLoai", loai.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }

    private void loadData() {
        dao = new SachDAO(getContext());
        list = dao.getData();
//        adapter = new SachAdapter(getContext(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rcvSach.setLayoutManager(manager);
        adapter = new SachAdapter(getContext(), list,getLoaiSach(), dao);
        binding.rcvSach.setAdapter(adapter);
    }
}