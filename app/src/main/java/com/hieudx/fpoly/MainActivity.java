package com.hieudx.fpoly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hieudx.fpoly.dao.ThuThuDao;
import com.hieudx.fpoly.databinding.ActivityLoginBinding;
import com.hieudx.fpoly.databinding.ActivityMainBinding;
import com.hieudx.fpoly.fragment.DoanhThuFragment;
import com.hieudx.fpoly.fragment.LoaiSachFragment;
import com.hieudx.fpoly.fragment.MatKhauFragment;
import com.hieudx.fpoly.fragment.PMFragment;
import com.hieudx.fpoly.fragment.SachFragment;
import com.hieudx.fpoly.fragment.TVFragment;
import com.hieudx.fpoly.fragment.ThuThuFragment;
import com.hieudx.fpoly.fragment.Top10Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
//        loadFragment(LoaiSachFragment.newInstance()); // nếu muốn mở ra là fragment qly sách đầu tiên

//        thuThuDao = new ThuThuDao(getApplicationContext()); // nếu muốn rào đc màn hình login mà vẫn thêm đc
//        thuThuDao.checkLogin("tt01","123");

//        vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(mainBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu);

//        dùng để click vào icon menu bật ra nav
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainBinding.drawerLayout, mainBinding.toolbar, R.string.navigation_open, R.string.navigation_close);
        mainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerLayout = mainBinding.nav.getHeaderView(0);
        TextView tvName = headerLayout.findViewById(R.id.tvName);
        mainBinding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_qlyPM) {
                    loadFragment(PMFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyLS) {
                    loadFragment(LoaiSachFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyS) {
                    loadFragment(SachFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyTV) {
                    loadFragment(TVFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyTop10) {
                    loadFragment(Top10Fragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyDT) {
                    loadFragment(DoanhThuFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyThuThu) {
                    loadFragment(ThuThuFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_qlyPass) {
                    loadFragment(MatKhauFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_logOut) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    loadFragment(LoaiSachFragment.newInstance());

                }
                mainBinding.drawerLayout.closeDrawer(mainBinding.nav);
                mainBinding.toolbar.setTitle(item.getTitle());
                return true;
            }
        });

        SharedPreferences shared = getSharedPreferences("PROFILE", MODE_PRIVATE);
        String vaitro = shared.getString("vaiTro", "");
        if (!vaitro.equals("Admin")) {
            Menu menu = mainBinding.nav.getMenu();
            menu.findItem(R.id.nav_qlyDT).setVisible(false);
            menu.findItem(R.id.nav_qlyTop10).setVisible(false);
            menu.findItem(R.id.nav_qlyThuThu).setVisible(false);
        }
        String hoten = shared.getString("hoTen", "");
        tvName.setText("Xin chào " + hoten);
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}