package com.hieudx.fpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hieudx.fpoly.dao.ThuThuDao;
import com.hieudx.fpoly.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ThuThuDao ThuThuDao;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ktra login, xem ng dùng có lưu tt hay k
        shared = getSharedPreferences("INFO", MODE_PRIVATE);
        boolean isCheck = shared.getBoolean("isChecked", false);
        if (isCheck) {
            binding.edUserName.setText(shared.getString("userLogin", ""));
            binding.edPass.setText(shared.getString("passLogin", ""));
            binding.chkLogin.setChecked(isCheck);
        }

        ThuThuDao = new ThuThuDao(this);

        binding.tvDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Toast.makeText(LoginActivity.this, "Tính năng chưa được phát triển", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = binding.edUserName.getText().toString();
                String pass = binding.edPass.getText().toString();
                int temp = 0;
                if (user.isEmpty()) {
                    binding.tvUser.setText("Tên đăng nhập không được để trống");
                    temp++;
                } else {
                    binding.tvUser.setText("");
                }

                if (pass.isEmpty()) {
                    binding.tvPass.setText("Mật khẩu không được để trống");
                    temp++;
                } else {
                    binding.tvPass.setText("");
                }

                if (temp == 0) {
                    if (ThuThuDao.checkLogin(user, pass)) {
                        if (binding.chkLogin.isChecked()) {
                            shared = getSharedPreferences("INFO", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit(); // gọi dòng trên và edit vào nó
                            editor.putString("userLogin", user);
                            editor.putString("passLogin", pass);
                            editor.putBoolean("isChecked", binding.chkLogin.isChecked());
                            editor.apply();
                        } else {
                            shared = getSharedPreferences("INFO", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit(); // gọi dòng trên và edit vào nó
                            editor.clear();
                            editor.apply();
                        }
                        binding.tvPass.setText("");
                        binding.tvUser.setText("");
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    temp = 0;
                }

            }
        });
    }
}