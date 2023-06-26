package com.hieudx.fpoly.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.adapter.Top10Adapter;
import com.hieudx.fpoly.dao.DoanhThuDAO;
import com.hieudx.fpoly.model.Top10;

import java.util.ArrayList;

public class Top10Fragment extends Fragment {

    private TextView tvTop10, tvTenSachTop, tvSoLuongTop;
    public static Fragment newInstance() {
        Top10Fragment fragment = new Top10Fragment();
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top10, container, false);

        ListView lvTop = v.findViewById(R.id.lvTop10);
        tvTop10 = v.findViewById(R.id.tvTop);
        tvTenSachTop = v.findViewById(R.id.tvTenSachTop10);
        tvSoLuongTop = v.findViewById(R.id.tvSoLuongTop10);

        DoanhThuDAO dao  = new DoanhThuDAO(getActivity());
        ArrayList<Top10> list = (ArrayList<Top10>) dao.getTop();
        Top10Adapter adapter = new Top10Adapter(getActivity(),this,list);
        lvTop.setAdapter(adapter);
        return v;
    }
}