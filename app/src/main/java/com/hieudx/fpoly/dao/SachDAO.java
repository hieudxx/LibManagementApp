package com.hieudx.fpoly.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    DBHelper dbHelper;
    SQLiteDatabase db;
    private Context context;
    public SachDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    //    lấy toàn bộ đầu sách có trong thư viện
    public ArrayList<Sach> getData() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sc.maSach, sc.tenSach, sc.giaThue, sc.maLoai, lo.tenLoai, sc.trang FROM Sach sc, LoaiSach lo WHERE sc.maLoai = lo.maLoai ORDER BY sc.maSach DESC", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Sach(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getInt(5)));
            } while (c.moveToNext());

        }
        return list;
    }

    public Sach getMaSach(String maSach) {
        List<Sach> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Sach WHERE maSach=?", new String[]{String.valueOf(maSach)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Sach(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3)));
            } while (c.moveToNext());
        }
        return list.get(0);
    }

    public boolean insert(String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", tenSach);
        values.put("giaThue", giaThue);
        values.put("maLoai", maLoai);
        long check = db.insert("Sach", null, values);
        if (check == -1) {
            return false;
        }
        return true;

    }

    public boolean update(int maSach, String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach", tenSach);
        contentValues.put("giaThue", giaThue);
        contentValues.put("maLoai", maLoai);
        long check = db.update("Sach", contentValues, "maSach = ?", new String[]{String.valueOf(maSach)});
        if (check == -1)
            return false;
        return true;
    }

    //    1:xóa thành công, 0:thất bại, -1:k đc xóa khi sách có trong pm
    public int delete(int maSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PhieuMuon WHERE maSach = ?", new String[]{String.valueOf(maSach)});
        if (c.getCount() != 0) {
            return -1;
        }

        long check = db.delete("Sach", "maSach = ?", new String[]{String.valueOf(maSach)});
        if (check == -1)
            return 0;
        return 1;
    }


}
