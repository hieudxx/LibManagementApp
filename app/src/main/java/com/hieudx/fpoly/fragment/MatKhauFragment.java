package com.hieudx.fpoly.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hieudx.fpoly.LoginActivity;
import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.ThuThuDao;
import com.hieudx.fpoly.databinding.FragmentMatKhauBinding;
import com.hieudx.fpoly.databinding.FragmentPMBinding;


public class MatKhauFragment extends Fragment {
    private FragmentMatKhauBinding binding;

    public MatKhauFragment() {
    }

    public static Fragment newInstance() {
        MatKhauFragment fragment = new MatKhauFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMatKhauBinding.inflate(inflater, container, false);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.OldPass.setText("");
                binding.newPass.setText("");
                binding.passCheck.setText("");
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newpass = binding.newPass.getText().toString();
                String passcheck = binding.passCheck.getText().toString();
                String oldpass = binding.OldPass.getText().toString();
                if (newpass.equals("") || passcheck.equals("") || oldpass.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.newPass.getText().toString().equals(binding.passCheck.getText().toString())) {
//                    cập nhật
                        SharedPreferences share = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
                        String matt = share.getString("maTT", "");
                        ThuThuDao thuThuDao = new ThuThuDao(getContext());
                        int check = thuThuDao.updatePass(matt, binding.OldPass.getText().toString(), binding.newPass.getText().toString());
                        switch (check) {
                            case 1:
                                Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                break;
                            case 0:
                                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return binding.getRoot();
    }
}