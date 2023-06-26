package com.hieudx.fpoly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.PhieuMuon;

import java.util.ArrayList;

public class PMDAO {
    DBHelper dbHelper;
    private Context context;

    public PMDAO(Context context) {
//        DBHelper dbHelper = new DBHelper(context);
        dbHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getData() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT pm.maPM, pm.maTV, tv.hoTen, pm.maTT, tt.hoTen, pm.maSach, s.tenSach, pm.ngay, pm.traSach, pm.tienThue FROM PhieuMuon pm, ThanhVien tv, ThuThu tt, Sach s WHERE pm.maTV = tv.maTV and pm.maTT = tt.maTT AND pm.maSach = s.maSach ORDER BY pm.maPM DESC", null);

        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new PhieuMuon(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
            } while (c.moveToNext());

        }
        return list;
    }

    public boolean isTraSach(int maPM) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("traSach", 1);
        long check = db.update("PhieuMuon", contentValues, "maPM=?", new String[]{String.valueOf(maPM)});

        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean insert(PhieuMuon obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTV", obj.getMaTV());
        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("maSach", obj.getMaSach());
        contentValues.put("ngay", obj.getNgay());
        contentValues.put("traSach", obj.getTraSach());
        contentValues.put("tienThue", obj.getTienThue());

        long check = db.insert("PhieuMuon", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }


}
