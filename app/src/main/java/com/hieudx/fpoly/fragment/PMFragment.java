package com.hieudx.fpoly.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.adapter.PMAdapter;
import com.hieudx.fpoly.dao.PMDAO;
import com.hieudx.fpoly.dao.SachDAO;
import com.hieudx.fpoly.dao.ThanhVienDAO;
import com.hieudx.fpoly.databinding.DialogAddPmBinding;
import com.hieudx.fpoly.databinding.FragmentPMBinding;
import com.hieudx.fpoly.model.PhieuMuon;
import com.hieudx.fpoly.model.Sach;
import com.hieudx.fpoly.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class PMFragment extends Fragment {
    private FragmentPMBinding binding;
    private DialogAddPmBinding dialogAddPmBinding;
    private PMAdapter adapter;
    private ArrayList<PhieuMuon> list;
    private PMDAO pmdao;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public PMFragment() {
    }

    public static Fragment newInstance() {
        PMFragment fragment = new PMFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPMBinding.inflate(inflater, container, false);

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
        builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
        builder.setCancelable(false);
//        LayoutInflater inflater = getLayoutInflater();
        dialogAddPmBinding = DialogAddPmBinding.inflate(inflater, container, false);
        getDataTV(dialogAddPmBinding.spnTV);
        getDataSach(dialogAddPmBinding.spnSach);
        builder.setView(dialogAddPmBinding.getRoot());
        alertDialog = builder.create();
        alertDialog.show();

        dialogAddPmBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                          lấy mã tv dialog
                HashMap<String, Object> hsTv = (HashMap<String, Object>) dialogAddPmBinding.spnTV.getSelectedItem();
                int matv = (int) hsTv.get("maTV");
//                        lấy mã sách dialog
                HashMap<String, Object> hsSach = (HashMap<String, Object>) dialogAddPmBinding.spnSach.getSelectedItem();
                int masach = (int) hsSach.get("maSach");

//                        lấy giá thuê trên dialog
//                            int tien = Integer.parseInt(dialogAddPmBinding.edTienThue.getText().toString());
                int tien = (int) hsSach.get("giaThue");

                addPM(matv, masach, tien);
//                        thêm ORDER BY pm.maPM DESC để những pm nào mới đc tạo sẽ lên trên đầu


            }
        });

        dialogAddPmBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void loadData() {
        pmdao = new PMDAO(getContext());
        list = pmdao.getData();
        adapter = new PMAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvPM.setLayoutManager(linearLayoutManager);
        binding.rcvPM.setAdapter(adapter);
    }

    private void getDataTV(Spinner spnTV) {
        ThanhVienDAO tvDao = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> listTV = tvDao.getData();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : listTV) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV", tv.getMaTV());
            hs.put("hoTen", tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"hoTen"}, new int[]{android.R.id.text1});
        spnTV.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDAO sachDao = new SachDAO(getContext());
        ArrayList<Sach> listSach = sachDao.getData();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach s : listSach) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maSach", s.getMaSach());
            hs.put("tenSach", s.getTenSach());
            hs.put("giaThue", s.getGiaThue()); // nếu k muốn lấy tiền thuê là giá thuê luôn thì rào lại
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter
                (getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"tenSach"}, new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void addPM(int matv, int masach, int tien) {
//        lấy mã thủ thư

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("maTT", ""); // nếu có thì là matt nếu k có thì để rỗng

//        lấy ngày hiên tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, 0, tien);
        boolean ktra = pmdao.insert(phieuMuon);
        if (ktra) {
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.dismiss();

        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}