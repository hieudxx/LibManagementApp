package com.hieudx.fpoly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hieudx.fpoly.R;
import com.hieudx.fpoly.fragment.Top10Fragment;
import com.hieudx.fpoly.model.Top10;

import java.util.ArrayList;

public class Top10Adapter extends ArrayAdapter<Top10> {
    private Context context;
    private ArrayList<Top10> list;
    private LayoutInflater inflater;
    private Top10Fragment Top10Fragment;
    private TextView tvTop, tvTenSach, tvSoLuong;
    private int temp = 0;


    public Top10Adapter(Context context, Top10Fragment Top10Fragment, ArrayList<Top10> list) {
        super(context, 0, list);
        this.context = context;
        this.Top10Fragment = Top10Fragment;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            temp++;
            v = inflater.inflate(R.layout.item_rcv_top10, null);
        }
        final Top10 top = list.get(position);
        if (top != null) {
            tvTop = v.findViewById(R.id.tvTop);
            tvTop.setText(String.valueOf(temp));

            tvTenSach = v.findViewById(R.id.tvTenSachTop10);
            tvTenSach.setText(top.getTenSach());

            tvSoLuong = v.findViewById(R.id.tvSoLuongTop10);
            tvSoLuong.setText(String.valueOf(top.getSoLuong()));
        }
        return v;
    }
}
