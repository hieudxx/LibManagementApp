package com.hieudx.fpoly.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hieudx.fpoly.adapter.TTAdapter;

import com.hieudx.fpoly.dao.ThuThuDao;
import com.hieudx.fpoly.databinding.DialogAddTtBinding;
import com.hieudx.fpoly.databinding.FragmentThuThuBinding;
import com.hieudx.fpoly.model.ThuThu;

import java.util.ArrayList;

public class ThuThuFragment extends Fragment {
    private FragmentThuThuBinding binding;
    private DialogAddTtBinding dialogBinding;
    private ThuThuDao dao;
    private ArrayList<ThuThu> list;
    private TTAdapter adapter;
    public static Fragment newInstance() {
        ThuThuFragment fragment = new ThuThuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThuThuBinding.inflate(inflater, container, false);

        loadData();

        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(inflater, container);

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
                String matt = dialogBinding.edMaTT.getText().toString();
                String tentt = dialogBinding.edhoTen.getText().toString();
                String matkhau = dialogBinding.edtPass.getEditText().getText().toString();

                if (matt.equals("") || tentt.equals("") || matkhau.equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhâp đủ thông tin", Toast.LENGTH_SHORT).show();
                } else{
                    int check = dao.insert(matt,tentt,matkhau,"Thủ thư");
                    switch (check){
                        case 1:
                            loadData();
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            break;
                        case 0: Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show(); break;
                        case -1: Toast.makeText(getContext(), "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show(); break;
                        default: break;
                    }

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

    private void loadData(){
        dao = new ThuThuDao(getContext());
        list = dao.getData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rcvTT.setLayoutManager(manager);
        adapter = new TTAdapter(getContext(), list, dao);
        binding.rcvTT.setAdapter(adapter);
    }
}