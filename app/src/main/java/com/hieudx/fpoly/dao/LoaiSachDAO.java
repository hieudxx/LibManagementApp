package com.hieudx.fpoly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    DBHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    //    thêm loại sách
    public boolean insert(String tenLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", tenLoai);
        long check = db.insert("LoaiSach", null, contentValues);
        if (check == -1)
            return false;
        return true;

    }

    //    xóa loại sách
//    1: xóa thành công - 0: xóa thất bại - -1: sách có tồn tại, k đc xóa
    public int delete(int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Sach WHERE maLoai = ?", new String[]{String.valueOf(maLoai)});
        if (c.getCount() != 0) {
            return -1;
        }

        long check = db.delete("LoaiSach", "maLoai =?", new String[]{String.valueOf(maLoai)});
        if (check == -1)
            return 0;
        return 1;
    }
//  sửa loại sách
    public boolean edit(LoaiSach loaiSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai",loaiSach.getTenLoai());
        long check = db.update("LoaiSach",contentValues, "maLoai = ?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        if (check == -1)
            return false;
        return true;
    }

    //    lấy danh sách loại sách
    public ArrayList<LoaiSach> getData() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM LoaiSach", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new LoaiSach(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());
        }
        return list;
    }


}
