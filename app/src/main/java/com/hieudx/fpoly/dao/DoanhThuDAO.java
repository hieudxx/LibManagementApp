package com.hieudx.fpoly.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.Sach;
import com.hieudx.fpoly.model.Top10;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuDAO {
    DBHelper dbHelper;
    private Context context;
    SQLiteDatabase db;
    public DoanhThuDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Top10> getTop() {
        List<Top10> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10", null);
        while (c.moveToNext()) {
            Top10 top = new Top10();
            Sach sach = sachDAO.getMaSach(c.getString(c.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }


    //    thống kê doanh thu
    public int getDoanhThu(String dayStart, String dayEnd) {
        dayStart = dayStart.replace("/", "");
        dayEnd = dayEnd.replace("/", "");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(tienThue) FROM PhieuMuon WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) between ? and ?", new String[]{dayStart, dayEnd});
        if (c.getCount() != 0) {
            c.moveToFirst();
            return c.getInt(0);
        }
        return 0;
    }
}
