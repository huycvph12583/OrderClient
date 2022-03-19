package com.example.orderclient.FRAGMENT;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.LoaiThucDonSpinnerAdapter;
import com.example.orderclient.ADAPTER.ThucDonAdapter;
import com.example.orderclient.ANIMATION.TranslateAnimationUtil;
import com.example.orderclient.DAO.LoaiThucDonDao;
import com.example.orderclient.DAO.ThucDonDao;
import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.MODEL.LoaiThucDon;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;
import com.example.orderclient.SubActivity.ThemThucDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sa90.materialarcmenu.ArcMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThucDonFragment extends Fragment {

    private RecyclerView rcv_thucdon;
    private ThucDonAdapter thucDonAdapter;
    View v;
    Spinner spinner, spinnerDialog;
    ArrayList<LoaiThucDon> listLoaiThucDon, listLoaiThucDonDialog;
    LoaiThucDonSpinnerAdapter spinnerAdapter, spinnerAdapterDialog;
    ThucDon item;
    LoaiThucDonDao loaiThucDonDao;
    LoaiThucDon loaiThucDon;
    ThucDonDao thucDonDao;
    private ArcMenu arc_thucdon;
    FloatingActionButton fab_thucdon_themmon, fab_thucdon_themloaimon, fab_thucdon_xoaloaimon;
    Bundle bundleTD;
    String usernameTD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_thuc_don, container, false);
        anhXa();

        //Set quyền nhân viên
        bundleTD = getArguments();
        if (bundleTD != null) {
            usernameTD = bundleTD.getString("usernametd");
            if (usernameTD != null) {
                if (!usernameTD.equalsIgnoreCase("admin")) {
                    arc_thucdon.setVisibility(View.GONE);
                }
                if (usernameTD.equalsIgnoreCase("quanly")) {
                    arc_thucdon.setVisibility(View.VISIBLE);
                }
            }
        }

        //Xử lý hiển thị loại thực đơn vào spinner
        addSpinnerMain();

        //Xử lý hiển thị thực đơn lên rcv
        updateRecyclerView();

        //Xử lý lọc loại thực đơn theo item spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    updateRecyclerView2();
                } else {
                    ArrayList<ThucDon> locLoaiTDList = new ArrayList<>();
                    locLoaiTDList = (ArrayList<ThucDon>) getListThucDonTheoLTD(String.valueOf(listLoaiThucDon.get(position).maLoaiTD));
                    thucDonAdapter.setData(locLoaiTDList);
                    rcv_thucdon.setAdapter(thucDonAdapter);
                    rcv_thucdon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), arc_thucdon));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Xử lý sửa, xóa món
        thucDonAdapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(ThucDon thucDon) {
                item = thucDon;
                openDialogSuaMon(Gravity.CENTER);
            }

            @Override
            public void onClick(KhuyenMai khuyenMai) {

            }
        });

        //Xử lý thêm món
        fab_thucdon_themmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemThucDon.class);
                startActivity(intent);
            }
        });

        //Xử lý thêm loại món
        fab_thucdon_themloaimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThemLoaiTD(Gravity.CENTER);
            }
        });

        //Xử lý sửa, xóa loại món
        fab_thucdon_xoaloaimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSuaXoaLoaiTD(Gravity.CENTER);
            }
        });
        return v;
    }

    private void anhXa() {
        spinner = v.findViewById(R.id.sp_thucdon);
        arc_thucdon = v.findViewById(R.id.arc_thucdon);
        rcv_thucdon = v.findViewById(R.id.rcv_thucdon);
        fab_thucdon_themmon = v.findViewById(R.id.fab_thucdon_themmon);
        fab_thucdon_themloaimon = v.findViewById(R.id.fab_thucdon_themloaimon);
        fab_thucdon_xoaloaimon = v.findViewById(R.id.fab_thucdon_xoaloaimon);
    }

    private List<ThucDon> getListThucDon() {
        List<ThucDon> lists = new ArrayList<>();
        thucDonDao = new ThucDonDao(getActivity());
        lists = thucDonDao.getAll();
        return lists;
    }

    private List<ThucDon> getListThucDonTheoLTD(String maLoaiTD) {
        List<ThucDon> lists = new ArrayList<>();
        thucDonDao = new ThucDonDao(getActivity());
        lists = (List<ThucDon>) thucDonDao.getMaLoaiTD(maLoaiTD);
        return lists;
    }

    private void updateRecyclerView() {
        thucDonAdapter = new ThucDonAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rcv_thucdon.setLayoutManager(gridLayoutManager);
        thucDonAdapter.setData(getListThucDon());
        rcv_thucdon.setAdapter(thucDonAdapter);
        rcv_thucdon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), arc_thucdon));
    }

    public void updateRecyclerView2() {
        thucDonAdapter.setData(getListThucDon());
        rcv_thucdon.setAdapter(thucDonAdapter);
        rcv_thucdon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), arc_thucdon));
    }

    private void addSpinnerMain() {
        loaiThucDonDao = new LoaiThucDonDao(getActivity());
        LoaiThucDon loaiThucDonClone = new LoaiThucDon(0, "Tất cả");
        listLoaiThucDon = new ArrayList<LoaiThucDon>();
        listLoaiThucDon = (ArrayList<LoaiThucDon>) loaiThucDonDao.getAll();
        listLoaiThucDon.add(loaiThucDonClone);
        Collections.reverse(listLoaiThucDon);
        spinnerAdapter = new LoaiThucDonSpinnerAdapter(getActivity(), listLoaiThucDon);
        spinner.setAdapter(spinnerAdapter);
    }

    private void addSpinnerDialog() {
        loaiThucDonDao = new LoaiThucDonDao(getActivity());
        listLoaiThucDonDialog = new ArrayList<LoaiThucDon>();
        listLoaiThucDonDialog = (ArrayList<LoaiThucDon>) loaiThucDonDao.getAll();
        Collections.reverse(listLoaiThucDonDialog);
        spinnerAdapterDialog = new LoaiThucDonSpinnerAdapter(getActivity(),listLoaiThucDonDialog);
        spinnerDialog.setAdapter(spinnerAdapterDialog);
    }

    private void openDialogSuaMon(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thuc_don_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        TextView tv_tenmon = dialog.findViewById(R.id.tv_thucdon_dialog_tenmon);
        EditText edt_giamon = dialog.findViewById(R.id.edt_thucdon_dialog_giamon);
        Button btn_huy = dialog.findViewById(R.id.btn_thucdon_dialog_huy);
        Button btn_xoa = dialog.findViewById(R.id.btn_thucdon_dialog_xoa);
        Button btn_luu = dialog.findViewById(R.id.btn_thucdon_dialog_luu);

        tv_tenmon.setText("Mã: "+item.maMon+", Tên: "+item.tenMon+", Giá: "+item.donGia+" VND");

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa "+item.tenMon+" khỏi thực đơn ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Xóa", (alertDialog, which) -> {
                    if (usernameTD.equalsIgnoreCase("admin") || usernameTD.equalsIgnoreCase("quanly")) {
                        thucDonDao.delete(String.valueOf(item.maMon));
                        updateRecyclerView2();
                        addSpinnerMain();
                        Toast.makeText(getActivity(),"Đã xóa "+item.tenMon,Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(),"Bạn không có quyền xóa",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", (alertDialog, which) -> {
                    alertDialog.cancel();
                });
                AlertDialog alert = builder.create();
                builder.show();
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameTD.equalsIgnoreCase("admin") || usernameTD.equalsIgnoreCase("quanly")) {
                    if (edt_giamon.getText().length() != 0) {
                        item.donGia = Integer.valueOf(edt_giamon.getText().toString());
                        if (thucDonDao.update(item) > 0){
                            updateRecyclerView2();
                            addSpinnerMain();
                            Toast.makeText(getActivity(),"Sửa thành công: "+item.tenMon+", giá: "+item.donGia,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(),"Vui lòng nhập giá món",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"Bạn không có quyền sửa",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void openDialogThemLoaiTD(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loai_thuc_don_dialog_them);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edt_tenloaitd = dialog.findViewById(R.id.edt_loaithucdon_dialog_them_tenloaitd);
        Button btn_huy = dialog.findViewById(R.id.btn_loaithucdon_dialog_them_huy);
        Button btn_luu = dialog.findViewById(R.id.btn_loaithucdon_dialog_them_luu);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiThucDon = new LoaiThucDon();
                loaiThucDon.tenLoaiTD = edt_tenloaitd.getText().toString();
                if (edt_tenloaitd.getText().length() != 0) {
                    if (loaiThucDonDao.insert(loaiThucDon) > 0){
                        Toast.makeText(getActivity(),"Lưu thành công",Toast.LENGTH_SHORT).show();
                        addSpinnerMain();
                    }else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),"Vui lòng nhập tên loại thực đơn",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void openDialogSuaXoaLoaiTD(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loai_thuc_don_dialog_suaxoa);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        spinnerDialog = dialog.findViewById(R.id.sp_loaithucdon_dialog_suaxoa);
        EditText edt_tenloaitd = dialog.findViewById(R.id.edt_loaithucdon_dialog_suaxoa_tenloaitd);
        Button btn_huy = dialog.findViewById(R.id.btn_loaithucdon_dialog_suaxoa_huy);
        Button btn_xoa = dialog.findViewById(R.id.btn_loaithucdon_dialog_suaxoa_xoa);
        Button btn_luu = dialog.findViewById(R.id.btn_loaithucdon_dialog_suaxoa_luu);

        //Xử lý hiển thị loại thực đơn vào spinner
        addSpinnerDialog();
        LoaiThucDon cacheLTD = new LoaiThucDon();
        //Xử Lý chọn item spinner
        spinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edt_tenloaitd.setHint("Nhập tên mới thay thế cho "+listLoaiThucDonDialog.get(position).tenLoaiTD);
                cacheLTD.maLoaiTD = listLoaiThucDonDialog.get(position).maLoaiTD;
                cacheLTD.tenLoaiTD = listLoaiThucDonDialog.get(position).tenLoaiTD;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận xóa "+cacheLTD.tenLoaiTD);
                builder.setMessage("Lưu ý: Sau khi xóa "+cacheLTD.tenLoaiTD+", tất cả các món của thực đơn "+cacheLTD.tenLoaiTD+" cũng sẽ bị xóa. Vẫn xóa ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Xóa", (alertDialog, which) -> {
                    loaiThucDonDao.delete(String.valueOf(cacheLTD.maLoaiTD));
                    addSpinnerMain();
                    Toast.makeText(getActivity(),"Đã xóa",Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                    dialog.dismiss();
                });
                builder.setNegativeButton("Hủy", (alertDialog, which) -> {
                    alertDialog.cancel();
                });
                AlertDialog alert = builder.create();
                builder.show();
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_tenloaitd.getText().length() != 0) {
                    cacheLTD.tenLoaiTD = edt_tenloaitd.getText().toString();
                    if (loaiThucDonDao.update(cacheLTD) > 0){
                        addSpinnerMain();
                        Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),"Vui lòng nhập tên loại thực đơn",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

}