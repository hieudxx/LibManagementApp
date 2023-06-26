package com.hieudx.fpoly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    DBHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean insert(String hoTen, String namSinh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", hoTen);
        values.put("namSinh", namSinh);
        long check = db.insert("ThanhVien", null, values);
        if (check == -1)
            return false;
        return true;
    }

    public boolean update(int maTV, String hoTen, String namSinh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", hoTen);
        values.put("namSinh", namSinh);
        long check = db.update("ThanhVien", values, "maTV = ?", new String[]{String.valueOf(maTV)});
        if (check == -1)
            return false;
        return true;
    }

//    1: xóa thành công, 0: thất bại, -1: tìm thấy tv đang có pm
    public int delete(int maTV){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PhieuMuon WHERE maTV = ?",new String[]{String.valueOf(maTV)});
        if (c.getCount() != 0){
            return -1;
        }

        long check = db.delete("ThanhVien","maTV = ?", new String[]{String.valueOf(maTV)});
        if (check == -1)
            return 0;
        return 1;
    }

    public ArrayList<ThanhVien> getData() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ThanhVien", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new ThanhVien(c.getInt(0), c.getString(1), c.getString(2),c.getInt(3)));
            } while (c.moveToNext());
        }
        return list;
    }

}
