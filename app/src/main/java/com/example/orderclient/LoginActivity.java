package com.example.orderclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderclient.DAO.NhanVienDao;
import com.example.orderclient.FRAGMENT.QuanLyFragment;
import com.example.orderclient.SubActivity.DoiMatKhau;

public class LoginActivity extends AppCompatActivity {

    EditText edt_userName, edt_password;
    CheckBox chk_rememberpass;
    String strUser = "", strPass;
    NhanVienDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setTitle("Đăng Nhập");
        edt_userName = findViewById(R.id.ed_lg_user);
        edt_password = findViewById(R.id.ed_lg_pass);
        chk_rememberpass = findViewById(R.id.ckb_luu);
        dao = new NhanVienDao(this);

        //Đọc user, pass
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edt_userName.setText(pref.getString("USERNAME", ""));
        edt_password.setText(pref.getString("PASSWORD", ""));
        chk_rememberpass.setChecked(pref.getBoolean("REMEMBER", false));
    }

    public void checkLogin() {
        strUser = edt_userName.getText().toString();
        strPass = edt_password.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Tên đăng nhập và mật khẩu không được bỏ trống",Toast.LENGTH_SHORT).show();
        }else {
            if (dao.checkLogin(strUser,strPass) > 0 || (strUser.equals("admin")) && (strPass.equals("admin"))){
                Toast.makeText(getApplicationContext(),"Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chk_rememberpass.isChecked());

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("user", strUser);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, Boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status){
            //Xóa tình trạng lưu trữ trước đó
            edit.clear();
        }else {
            //Lưu dữ liệu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        //Lưu lại toàn bộ
        edit.commit();
    }

    public void save(View view) {
            checkLogin();
    }

    public void Cancel(View view) {
            edt_userName.setText("");
            edt_password.setText("");
    }
}