package com.hieudx.fpoly.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbName = "PNLIB";
    public static final int dbVersion = 1;
//    nếu tăng version sẽ chạy vào onUpgrade còn mặc định sẽ chạy onCreate

    public DBHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        tạo bảng thủ thư
        String createTableThuThu =
                "create table ThuThu (maTT TEXT PRIMARY KEY, hoTen TEXT NOT NULL, matKhau TEXT NOT NULL, vaiTro TEXT)";
        db.execSQL(createTableThuThu);

//        tạo bảng thành viên
        String createTableThanhVien =
                "create table ThanhVien(maTV INTEGER PRIMARY KEY AUTOINCREMENT, hoTen TEXT NOT NULL, namSinh TEXT NOT NULL, tien INTEGER)";
        db.execSQL(createTableThanhVien);

//        tạo bảng loại sách
        String createTableLoaiSach =
                "create table LoaiSach (maLoai INTEGER PRIMARY KEY AUTOINCREMENT, tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

//        tạo bảng sách
        String createTableSach =
                "create table Sach (maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " tenSach TEXT NOT NULL, giaThue INTEGER NOT NULL, maLoai INTEGER REFERENCES LoaiSach(maLoai), trang INTEGER)";
        db.execSQL(createTableSach);

//        tạo bảng phiếu mượn
        String createTablePhieuMuon =
                "create table PhieuMuon (maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " maTV INTEGER REFERENCES ThanhVien(maTV)," +
                        " maTT TEXT REFERENCES ThuThu(maTT)," +
                        " maSach INTEGER REFERENCES Sach(maSach)," +
                        " ngay TEXT NOT NULL,traSach INTEGER NOT NULL, tienThue INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);

//        data mẫu
        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO Sach VALUES (1, 'Hãy đợi đấy', 2500, 1, 20), (2, 'Thằng cuội', 1000, 1,19), (3, 'Lập trình Android', 2000, 3,21)");
        db.execSQL("INSERT INTO ThuThu VALUES ('tt01','Đỗ Xuân Hiếu','123','Admin'),('tt02','Ngô Trung Hiếu','123','thủ thư'), ('tt03','Cao Văn Chỉnh','123','thủ thư')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Thành Viên 1','2000','1001'),(2,'Thành Viên 2','2000','999')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PhieuMuon VALUES (1,1,'tt01', 1, '19/05/2023', 1, 2500)," +
                "(2,1,'tt01', 3, '19/04/2022', 0, 2000),(3,2,'tt02', 1, '19/03/2022', 1, 2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { //i là old version i1 là new version
//  trong trường hợp các bảng đã tồn tại, nếu cta code sai hoặc thay đổi 1 trường data thì buộc phải xóa bảng đã có đi, khi đó ta sẽ chạy hàm này
        if (i != i1) {
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db); // gọi lại onCreate để tạo lại bảng
        }
    }
}
