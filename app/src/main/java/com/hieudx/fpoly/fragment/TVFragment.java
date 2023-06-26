package com.hieudx.fpoly.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.adapter.TVAdapter;
import com.hieudx.fpoly.dao.ThanhVienDAO;
import com.hieudx.fpoly.databinding.DialogAddLoaiSachBinding;
import com.hieudx.fpoly.databinding.DialogAddTvBinding;
import com.hieudx.fpoly.databinding.FragmentTVBinding;
import com.hieudx.fpoly.model.ThanhVien;

import java.util.ArrayList;


public class TVFragment extends Fragment {
    private FragmentTVBinding binding;
    private DialogAddTvBinding dialogBinding;
    private ThanhVienDAO dao;
    private ArrayList<ThanhVien> list;
    private TVAdapter adapter;

    public static Fragment newInstance() {
        TVFragment fragment = new TVFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTVBinding.inflate(inflater, container, false);

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

        dialogBinding.numberPicker.setMaxValue(2020);
        dialogBinding.numberPicker.setMinValue(1900);
        dialogBinding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
//                dialogBinding.edNamSinh.setText(newValue);
            }
        });


        dialogBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = dialogBinding.edhoTen.getText().toString();
                String namsinh = dialogBinding.edNamSinh.getText().toString();

                if (hoten.equals("") || namsinh.equals("")) {
//                    (hoten.matches("[a-zA-Z ]+") || namsinh.matches("[a-zA-Z ]+"))
                    Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dao.insert(hoten, namsinh);
                    if (check) {
                        Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        dao = new ThanhVienDAO(getContext());
        list = dao.getData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rcvTV.setLayoutManager(manager);
        adapter = new TVAdapter(getContext(), list, dao);
        binding.rcvTV.setAdapter(adapter);
    }
}