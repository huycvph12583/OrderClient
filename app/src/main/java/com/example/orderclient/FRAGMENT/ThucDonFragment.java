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

        //Set quy???n nh??n vi??n
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

        //X??? l?? hi???n th??? lo???i th???c ????n v??o spinner
        addSpinnerMain();

        //X??? l?? hi???n th??? th???c ????n l??n rcv
        updateRecyclerView();

        //X??? l?? l???c lo???i th???c ????n theo item spinner
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

        //X??? l?? s???a, x??a m??n
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

        //X??? l?? th??m m??n
        fab_thucdon_themmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemThucDon.class);
                startActivity(intent);
            }
        });

        //X??? l?? th??m lo???i m??n
        fab_thucdon_themloaimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThemLoaiTD(Gravity.CENTER);
            }
        });

        //X??? l?? s???a, x??a lo???i m??n
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
        LoaiThucDon loaiThucDonClone = new LoaiThucDon(0, "T???t c???");
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

        tv_tenmon.setText("M??: "+item.maMon+", T??n: "+item.tenMon+", Gi??: "+item.donGia+" VND");

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
                builder.setTitle("X??c nh???n x??a");
                builder.setMessage("B???n c?? ch???c ch???n mu???n x??a "+item.tenMon+" kh???i th???c ????n ?");
                builder.setCancelable(true);
                builder.setPositiveButton("X??a", (alertDialog, which) -> {
                    if (usernameTD.equalsIgnoreCase("admin") || usernameTD.equalsIgnoreCase("quanly")) {
                        thucDonDao.delete(String.valueOf(item.maMon));
                        updateRecyclerView2();
                        addSpinnerMain();
                        Toast.makeText(getActivity(),"???? x??a "+item.tenMon,Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(),"B???n kh??ng c?? quy???n x??a",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("H???y", (alertDialog, which) -> {
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
                            Toast.makeText(getActivity(),"S???a th??nh c??ng: "+item.tenMon+", gi??: "+item.donGia,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(), "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(),"Vui l??ng nh???p gi?? m??n",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"B???n kh??ng c?? quy???n s???a",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(),"L??u th??nh c??ng",Toast.LENGTH_SHORT).show();
                        addSpinnerMain();
                    }else {
                        Toast.makeText(getActivity(), "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),"Vui l??ng nh???p t??n lo???i th???c ????n",Toast.LENGTH_SHORT).show();
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

        //X??? l?? hi???n th??? lo???i th???c ????n v??o spinner
        addSpinnerDialog();
        LoaiThucDon cacheLTD = new LoaiThucDon();
        //X??? L?? ch???n item spinner
        spinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edt_tenloaitd.setHint("Nh???p t??n m???i thay th??? cho "+listLoaiThucDonDialog.get(position).tenLoaiTD);
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
                builder.setTitle("X??c nh???n x??a "+cacheLTD.tenLoaiTD);
                builder.setMessage("L??u ??: Sau khi x??a "+cacheLTD.tenLoaiTD+", t???t c??? c??c m??n c???a th???c ????n "+cacheLTD.tenLoaiTD+" c??ng s??? b??? x??a. V???n x??a ?");
                builder.setCancelable(true);
                builder.setPositiveButton("X??a", (alertDialog, which) -> {
                    loaiThucDonDao.delete(String.valueOf(cacheLTD.maLoaiTD));
                    addSpinnerMain();
                    Toast.makeText(getActivity(),"???? x??a",Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                    dialog.dismiss();
                });
                builder.setNegativeButton("H???y", (alertDialog, which) -> {
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
                        Toast.makeText(getActivity(),"S???a th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),"Vui l??ng nh???p t??n lo???i th???c ????n",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

}