package com.hieudx.fpoly.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.adapter.LoaiSachAdapter;
import com.hieudx.fpoly.dao.LoaiSachDAO;
import com.hieudx.fpoly.databinding.DialogAddLoaiSachBinding;
import com.hieudx.fpoly.databinding.DialogAddPmBinding;
import com.hieudx.fpoly.databinding.FragmentLoaiSachBinding;
import com.hieudx.fpoly.databinding.FragmentPMBinding;
import com.hieudx.fpoly.model.LoaiSach;

import java.util.ArrayList;


public class LoaiSachFragment extends Fragment {

    private LoaiSachDAO dao;
    private ArrayList<LoaiSach> list;
    private LoaiSachAdapter adapter;
    private FragmentLoaiSachBinding binding;
    private DialogAddLoaiSachBinding dialogBinding;
    public LoaiSachFragment() {
    }

    public static Fragment newInstance() {
        LoaiSachFragment fragment = new LoaiSachFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoaiSachBinding.inflate(inflater, container, false);

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

        dialogBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogBinding.edLoaiSach.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insert(dialogBinding.edLoaiSach.getText().toString());
                    Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                    loadData();
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

    private void loadData() {
        dao = new LoaiSachDAO(getContext());
        list = dao.getData();
        adapter = new LoaiSachAdapter(getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvLS.setLayoutManager(linearLayoutManager);
        binding.rcvLS.setAdapter(adapter);
    }
}