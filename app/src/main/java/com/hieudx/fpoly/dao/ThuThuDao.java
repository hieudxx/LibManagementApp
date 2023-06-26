package com.hieudx.fpoly.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudx.fpoly.database.DBHelper;
import com.hieudx.fpoly.model.ThuThu;

import java.util.ArrayList;

public class ThuThuDao {
    DBHelper dbHelper;
    SharedPreferences share;

    public ThuThuDao(Context context) {
//        DBHelper dbHelper = new DBHelper(context);
        dbHelper = new DBHelper(context);
        share = context.getSharedPreferences("PROFILE", MODE_PRIVATE);
//        db = dbHelper.getWritableDatabase();

    }

    //    1: thêm thành công - 0: thêm thất bại - -1: thủ thư có tồn tại, k đc thêm
    public int insert(String maTT, String hoTen, String matKhau, String vaiTro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT * FROM ThuThu WHERE maTT = ?", new String[]{maTT});
        if (c.getCount() != 0) {
            return -1;
        }
        values.put("maTT", maTT);
        values.put("hoTen", hoTen);
        values.put("matKhau", matKhau);
        values.put("vaiTro", vaiTro);

        long check = db.insert("ThuThu", null, values);
        if (check == -1) {
            return 0;
        }
        return 1;

    }

    //    đăng nhập
    public boolean checkLogin(String maTT, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?", new String[]{maTT, matKhau});
        if (c.getCount() != 0) {
            c.moveToFirst();
            SharedPreferences.Editor editor = share.edit();
            editor.putString("maTT", c.getString(0));
            editor.putString("vaiTro", c.getString(3));
            editor.putString("hoTen", c.getString(1));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public int updatePass(String userName, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?", new String[]{userName, String.valueOf(oldPass)}); // xem có nhập đúng oldPass ko
        if (c.getCount() > 0) {
//            nếu nhập đúng thì:
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", String.valueOf(newPass));
            long check = db.update("ThuThu", contentValues, "maTT = ?", new String[]{userName});
            if (check == -1) {
                return -1; // không thành công
            } else {
                return 1; // thành công
            }
        } else {
            return 0; // không tìm thấy oldpass
        }
    }

    public ArrayList<ThuThu> getData() {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ThuThu", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new ThuThu(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
        }
        return list;
    }
}
