package com.hieudx.fpoly.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.dao.DoanhThuDAO;
import com.hieudx.fpoly.databinding.FragmentDoanhThuBinding;
import com.hieudx.fpoly.databinding.FragmentLoaiSachBinding;

import java.util.Calendar;

public class DoanhThuFragment extends Fragment {

    private FragmentDoanhThuBinding binding;

    public static Fragment newInstance() {
        DoanhThuFragment fragment = new DoanhThuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoanhThuBinding.inflate(inflater, container, false);

        Calendar calendar = Calendar.getInstance();

        binding.edDayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        ngay = (i2 < 10) ? "0" + i2 : String.valueOf(i2);
                        String thang = "";
                        thang = ((i1 + 1) < 10) ? "0" + (i1 + 1) : String.valueOf(i1 + 1);

                        binding.edDayStart.setText(i + "/" + thang + "/" + ngay);
                    }

                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        binding.edDayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        ngay = (i2 < 10) ? "0" + i2 : String.valueOf(i2);
                        String thang = "";
                        thang = ((i1 + 1) < 10) ? "0" + (i1 + 1) : String.valueOf(i1 + 1);

                        binding.edDayEnd.setText(i + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });

        binding.btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoanhThuDAO dao = new DoanhThuDAO(getContext());
                String ngaybatdau = binding.edDayStart.getText().toString();
                String ngayketthuc = binding.edDayEnd.getText().toString();
                int doanhthu = dao.getDoanhThu(ngaybatdau, ngayketthuc);
                binding.tvResult.setText(doanhthu + "VND");
            }
        });
        return binding.getRoot();
    }
}