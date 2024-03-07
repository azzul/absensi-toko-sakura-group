package com.kgs.absensisakura.Absensi;

import static android.content.Context.MODE_PRIVATE;
import static android.text.format.DateFormat.is24HourFormat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kgs.absensisakura.Adapter.DialogAdapter;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;
public class InputAbsensiFragment extends Fragment {
    private Button btn_plh;
    private RecyclerView recyclerView;
    private DialogAdapter adapter;
    private ArrayList<Karyawan> karyawanArrayList;
    private Dialog customDialog;
    private TextView txtnik, txtnama, jdlabsen, txinfolbr;
    private String Nama, Nik, Tanggal, Status, DtgRule, PlgRule, DtgAct, PlgAct, Lembur, LemburH, Izin, Keterangan, kdabsen,  identifikasi, idabsen;
    private EditText txttglabsen, txtdtgrule, txtplgrule, txtdtgact, txtplgact, txtlmbr,
    txtlmbrh, txtizin, txtketabsensi;
    private Button btn_simpanabsensi;
    private Spinner spinnerStatus;
    private String nm_karyawan, nik_karyawan, akh;
    private Integer fnkk, fnkh, fnoff, fnit, fnalpha, fnsd;
    private ImageView calendar2, jam1, jam2, jam3, jam4, infolbr, infolbrh, infoizin, infoket;
    DbHelper dbHelper;
    private SimpleDateFormat dtFormatter;
    private  DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ToolTipsManager mToolTipsManager;
    private ConstraintLayout parentLyt;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_absensi, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //untuk show toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mToolTipsManager = new ToolTipsManager();
        infolbr = view.findViewById(R.id.infolbr);
        infolbrh = view.findViewById(R.id.infolbrh);
        infoizin= view.findViewById(R.id.infoizin);
        infoket = view.findViewById(R.id.infoket);

        parentLyt = view.findViewById(R.id.parent_layout);
        //txinfolbr = view.findViewById(R.id.txinfolbr);
        dbHelper = new DbHelper(getContext());
        btn_plh = view.findViewById(R.id.btn_pilih);
        txtnama = view.findViewById(R.id.txtNama);
        txtnik = view.findViewById(R.id.txtNik);
        txttglabsen = view.findViewById(R.id.txttglabsen);
        txtdtgrule = view.findViewById(R.id.txtdtgrule);
        txtplgrule = view.findViewById(R.id.txtplgrule);
        txtdtgact = view.findViewById(R.id.txtdtgact);
        txtplgact = view.findViewById(R.id.txtplgact);
        txtlmbr = view.findViewById(R.id.txtlmbr);
        txtlmbrh = view.findViewById(R.id.txtlmbrh);
        txtizin = view.findViewById(R.id.txtizin);
        txtketabsensi = view.findViewById(R.id.txtketabsensi);
        btn_simpanabsensi = view.findViewById(R.id.btn_simpanabsensi);
        spinnerStatus = view.findViewById(R.id.spinnerStatus);
        calendar2 = view.findViewById(R.id.calendar2);
        jdlabsen = view.findViewById(R.id.tvjdlabsen);
        jam1= view.findViewById(R.id.jam1);
        jam2= view.findViewById(R.id.jam2);
        jam3= view.findViewById(R.id.jam3);
        jam4= view.findViewById(R.id.jam4);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                int Sts = spinnerStatus.getSelectedItemPosition();
                Status = spinnerStatus.getSelectedItem().toString();
                switch (Sts) {
                    case 1:
                        txtdtgact.setText("00:00");
                        txtplgact.setText("00:00");
                        break;
                    case 3:
                        txtdtgact.setText("00:00");
                        txtplgact.setText("00:00");
                        break;
                    case 4:
                        txtdtgact.setText("00:00");
                        txtplgact.setText("00:00");
                        break;
                    case 5:
                        txtdtgact.setText("00:00");
                        txtplgact.setText("00:00");
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        //btn info 1
        infolbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infolbr.getVisibility() == View.VISIBLE){
                    ToolTip.Builder builder = new ToolTip.Builder(getContext(), infolbr, parentLyt, "Kolom lembuh hari normal ini akan \n terisi otomatis  saat anda mengisi jam kehadiran dan memilih status KERJA HOLIDAY", ToolTip.POSITION_LEFT_TO);
                    builder.setAlign(ToolTip.ALIGN_CENTER);
                    // set background color

                    builder.setBackgroundColor(com.tomergoldst.tooltips.R.attr.colorPrimary);
                    // show tooltip
                    mToolTipsManager.show(builder.build());
                }else{
                    mToolTipsManager.findAndDismiss(infolbr);
                }
            }
        });
        infolbrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolTip.Builder builder = new ToolTip.Builder(getContext(), infolbrh, parentLyt, "Kolom Lembur di hari libur ini akan terisi otomatis  saat anda mengisi jam kehadiran dan memilih status KERJA HOLIDAY", ToolTip.POSITION_LEFT_TO);
                builder.setAlign(ToolTip.ALIGN_CENTER);
                // set background color
                builder.setBackgroundColor(com.tomergoldst.tooltips.R.attr.colorPrimary);
                // show tooltip
                mToolTipsManager.show(builder.build());
                infolbrh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                mToolTipsManager.findAndDismiss(infolbrh);
                    }
                });
            }
        });
        infoizin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolTip.Builder builder = new ToolTip.Builder(getContext(), infoizin, parentLyt, "Kolom izin ini akan terisi otomatis saat anda mengisi jam kehadiran, jika isi 0.5 berarti dihitung izin 1/2 hari", ToolTip.POSITION_LEFT_TO);
                builder.setAlign(ToolTip.ALIGN_CENTER);
                // set background color
                builder.setBackgroundColor(com.tomergoldst.tooltips.R.attr.colorPrimary);
                // show tooltip
                mToolTipsManager.show(builder.build());
                infoizin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                mToolTipsManager.findAndDismiss(infoizin);
                    }
                });
            }
        });
        infoket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolTip.Builder builder = new ToolTip.Builder(getContext(), infoket, parentLyt, "Kolom ini akan terisi otomatis  saat anda mengisi jam kehadiran dan berisi keterangan terlambat atau tidak", ToolTip.POSITION_LEFT_TO);
                builder.setAlign(ToolTip.ALIGN_CENTER);
                // set background color
                builder.setBackgroundColor(com.tomergoldst.tooltips.R.attr.colorPrimary);
                // show tooltip
                mToolTipsManager.show(builder.build());
                infoket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                mToolTipsManager.findAndDismiss(infoket);
                    }
                });
            }
        });

       // Log.e("Tanggal Akhir", akh);
        txtdtgrule.setText("08:30");
        txtplgrule.setText("17:00");

        // Storing data into SharedPreferences


        jam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String dtgrule= checkDigit(hourOfDay)+":"+checkDigit(minute);
                        txtdtgrule.setText(dtgrule);
                        Log.e("Tanggal Akhir", String.valueOf(minute));
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        is24HourFormat(getContext()));

                timePickerDialog.show();
            }
        });
        jam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String plgrule= checkDigit(hourOfDay)+":"+checkDigit(minute);
                        txtplgrule.setText(plgrule);
                        Log.e("pulang", String.valueOf(minute));
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        is24HourFormat(getContext()));

                timePickerDialog.show();
            }
        });
        jam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String dtgact= checkDigit(hourOfDay)+":"+checkDigit(minute);
                        txtdtgact.setText(dtgact);
                        DtgAct=dtgact;
                        Log.e("pulang", String.valueOf(minute));
                        txtdtgact.setError(null);
                        txtdtgact.setBackgroundResource(R.drawable.normal_edit);
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        is24HourFormat(getContext()));

                timePickerDialog.show();
            }
        });
        jam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String plgact= checkDigit(hourOfDay)+":"+checkDigit(minute);
                        txtplgact.setText(plgact);
                        PlgAct = plgact;
                        Log.e("pulang", String.valueOf(minute));
                        txtplgact.setError(null);
                        txtplgact.setBackgroundResource(R.drawable.normal_edit);
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        is24HourFormat(getContext()));

                timePickerDialog.show();
            }
        });
        dtFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        txttglabsen.setText(dateFormat.format(date));
        calendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txttglabsen.setText(dtFormatter.format(newDate.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        //Status = spinnerStatus.getSelectedItem().toString();
        btn_plh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putString("tanggal", txttglabsen.getText().toString());
                myEdit.putInt("status", spinnerStatus.getSelectedItemPosition());
                myEdit.putString("dtgrule", txtdtgrule.getText().toString());
                myEdit.putString("plgrule", txtplgrule.getText().toString());
                myEdit.putString("dtgact", txtdtgact.getText().toString());
                myEdit.putString("plgact", txtplgact.getText().toString());
                myEdit.putString("lembur", txtlmbr.getText().toString());
                myEdit.putString("lemburh", txtlmbrh.getText().toString());
                myEdit.putString("izin", txtizin.getText().toString());
                myEdit.putString("keterangan", txtketabsensi.getText().toString());

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.commit();
                //Do something
                Fragment fragment = new PilihKaryawanFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle b = new Bundle();
                b.putString("tanggal", txttglabsen.getText().toString());
                fragment.setArguments(b);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Bundle bundle = getArguments();
        if(bundle!=null) {
            identifikasi = getArguments().getString("identify");
            Log.e("identifikasi: ", getArguments().getString("identify"));
            if(getArguments().getString("identify")=="edit"){
                jdlabsen.setText("EDIT ABSENSI KARYAWAN");
                txtnik.setText(getArguments().getString("nik_edit"));
                txtnama.setText(getArguments().getString("nama_edit"));
                txttglabsen.setText(getArguments().getString("tgl_edit"));
                //untuk set spinner as selected
                ArrayAdapter myAdapter = (ArrayAdapter) spinnerStatus.getAdapter();
                int spinnerPosition = myAdapter.getPosition(getArguments().getString("status_edit"));
                spinnerStatus.setSelection(spinnerPosition);

                txtdtgrule.setText(getArguments().getString("dtgrule_edit"));
                txtplgrule.setText(getArguments().getString("plgrule_edit"));
                txtdtgact.setText(getArguments().getString("dtgact_edit"));
                txtplgact.setText(getArguments().getString("plgact_edit"));
                txtizin.setText(getArguments().getString("izin_edit"));
                txtketabsensi.setText(getArguments().getString("keterangan_edit"));
                txtlmbr.setText(getArguments().getString("lemburr_edit"));
                txtlmbrh.setText(getArguments().getString("lemburh_edit"));
            }else {

                SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
// The value will be default as empty string because for
// first time when the app is opened, there is nothing to show
                String tanggal = sh.getString("tanggal", "");
                Integer status = sh.getInt("status", 0);
                String dtgrule = sh.getString("dtgrule", "");
                String plgrule = sh.getString("plgrule", "");
                String dtgact = sh.getString("dtgact", "");
                String plgact = sh.getString("plgact", "");
                String lembur = sh.getString("lembur", "");
                String lemburh = sh.getString("lemburh", "");
                String izin = sh.getString("izin", "");
                String keterangan = sh.getString("keterangan", "");

// We can then use the data
                txttglabsen.setText(tanggal);
                spinnerStatus.setSelection(status);
                txtdtgrule.setText(dtgrule);
                txtplgrule.setText(plgrule);
                txtdtgact.setText(dtgact);
                txtplgact.setText(plgact);
                txtizin.setText(izin);
                txtketabsensi.setText(keterangan);
                txtlmbr.setText(lembur);
                txtlmbrh.setText(lemburh);


                //Log.e("pulang", lembur);
                nm_karyawan = getArguments().getString("nama");
                nik_karyawan = getArguments().getString("nik");
                txtnama.setText(nm_karyawan);
                txtnik.setText(nik_karyawan);
                //remove error logo after input

            }
            btn_simpanabsensi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tanggal = txttglabsen.getText().toString();
                    Status = spinnerStatus.getSelectedItem().toString();
                    DtgRule = txtdtgrule.getText().toString();
                    PlgRule = txtplgrule.getText().toString();
                    DtgAct = txtdtgact.getText().toString();
                    PlgAct = txtplgact.getText().toString();
                    Nama = txtnama.getText().toString();
                    Nik = txtnik.getText().toString();
                    if(txtdtgact.getText().toString().isEmpty() || txtplgact.getText().toString().isEmpty())
                    {
                        if(DtgAct.equals("")){
                            txtdtgact.setError("error");
                            txtdtgact.setBackgroundResource(R.drawable.error_background);
                        }
                        if(PlgAct.equals("")){
                            txtplgact.setError("error");
                            txtplgact.setBackgroundResource(R.drawable.error_background);
                        }
                        //btn_simpanabsensi.setEnabled(false);
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("DATA KEHADIRAN BELUM DI ISI!").setContentText("Mohon isi jam datang dan jam pulang terlebih dahulu!").show();
                    }else {


                        int Sts = spinnerStatus.getSelectedItemPosition();
                        Status = spinnerStatus.getSelectedItem().toString();
                        Log.e("ststus", Status);
                        switch (Sts) {
                            case 0:
                                kdabsen = "KK";
                                fnkk =1;
                                fnkh=0;
                                fnoff=0;
                                fnit=0;
                                fnalpha=0;
                                fnsd=0;
                                break;
                            case 1:
                                kdabsen = "OFF";
                                fnkk =0;
                                fnkh=0;
                                fnoff=1;
                                fnit=0;
                                fnalpha=0;
                                fnsd=0;
                                break;
                            case 2:
                                kdabsen = "KH";
                                fnkk =0;
                                fnkh=1;
                                fnoff=0;
                                fnit=0;
                                fnalpha=0;
                                fnsd=0;
                                break;
                            case 3:
                                kdabsen = "IT";
                                fnkk =0;
                                fnkh=0;
                                fnoff=0;
                                fnit=1;
                                fnalpha=0;
                                fnsd=0;
                                break;
                            case 4:
                                kdabsen = "A";
                                fnkk=0;
                                fnkh=0;
                                fnoff=0;
                                fnit=0;
                                fnalpha=1;
                                fnsd=0;
                                break;
                            case 5:
                                kdabsen = "SD";
                                fnkk=0;
                                fnkh=0;
                                fnoff=0;
                                fnit=0;
                                fnalpha=0;
                                fnsd=1;
                                break;
                        }

                        Lembur = txtlmbr.getText().toString();
                        if (Lembur.equals("")) {
                            txtlmbr.setText("0");
                            Lembur = "0";
                        }
                        LemburH = txtlmbrh.getText().toString();
                        if (LemburH.equals("")) {
                            txtlmbrh.setText("0");
                            LemburH = "0";
                        }
                        Log.e("masa ora bisa", Lembur);
                        Izin = txtizin.getText().toString();
                        if (Izin.equals("")) {
                            txtizin.setText("0");
                            Izin = "0";
                        }
                        //cek apa karyawan tersebut sudah keinput atau belum pada tanggal tersebut
                        Keterangan = txtketabsensi.getText().toString();
                        Log.e( "onClick: ", Nik);
                        if(Nik==null || Nik=="" || Nik.isEmpty()) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("BELUM PILIH KARYAWAN!").setContentText("Mohon pilih karyawan terlebih dahulu").show();
                        }else{
                            //k
                            idabsen = (Tanggal.replaceAll("-", "")) + Nik;
                            int id_cek = dbHelper.cek(idabsen);
                            Log.e("hitung id", String.valueOf(id_cek));
                            dbHelper.close();
                            if(identifikasi=="edit"){
                                dbHelper.updateAbsensi(idabsen, Tanggal, DtgRule, PlgRule, DtgAct, PlgAct, Lembur, LemburH, Izin, Keterangan, Nik, Nama, kdabsen, Status, fnkk, fnoff, fnkh, fnit, fnalpha, fnsd);
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("DATA ABSENSI BERHASIL DIPERBARUI!").setContentText("Silahkan cek pada daftar absensi").show();
                                txtnama.setText(null);
                                txtnik.setText(null);
                            }else {
                                if (id_cek == 0) {
                                    //jika belum keinput
                                    Log.e("data", idabsen + " " + Tanggal + " " + DtgRule + " " + PlgRule + " " + DtgAct + " " + PlgAct + " " + Lembur + " " + LemburH + " " + Izin + " " + Keterangan + " " + Nik + " " + Nama + " " + kdabsen + " " + Status);
                                    dbHelper.addAbsensi(idabsen, Tanggal, DtgRule, PlgRule, DtgAct, PlgAct, Lembur, LemburH, Izin, Keterangan, Nik, Nama, kdabsen, Status, fnkk, fnoff, fnkh, fnit, fnalpha, fnsd);
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("BERHASIL INPUT ABSENSI!").setContentText("Silahkan input absensi karyawan lainnya").show();
                                    txtnama.setText(null);
                                    txtnik.setText(null);
                                } else {
                                    //jika sudah
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("ABSENSI SUDAH TERINPUT SEBELUMNYA!").setContentText("Karyawan Dengan Nama " + Nama + " Pada Tanggal " + Tanggal + " Sudah Di Input Absen").show();
                                }
                            }
                        }
                    }
                }

            });//end click simpan

        }else {
            //else belum pilih karyawan
            btn_simpanabsensi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tanggal = txttglabsen.getText().toString();
                    Status = spinnerStatus.getSelectedItem().toString();
                    DtgRule = txtdtgrule.getText().toString();
                    PlgRule = txtplgrule.getText().toString();
                    DtgAct = txtdtgact.getText().toString();
                    PlgAct = txtplgact.getText().toString();
                    if(DtgAct.equals("")){
                        txtdtgact.setError("error");
                        txtdtgact.setBackgroundResource(R.drawable.error_background);
                    }
                    if(PlgAct.equals("")){
                        txtplgact.setError("error");
                        txtplgact.setBackgroundResource(R.drawable.error_background);
                    }
                    btn_plh.setError("error");
                    btn_plh.setBackgroundResource(R.drawable.error_background);
                    int Sts = spinnerStatus.getSelectedItemPosition();
                    Status = spinnerStatus.getSelectedItem().toString();
                    switch (Sts) {
                        case 0:
                            kdabsen = "KK";
                            fnkk=1;
                            break;
                        case 1:
                            kdabsen = "OFF";
                            break;
                        case 2:
                            kdabsen = "KH";
                            break;
                        case 3:
                            kdabsen = "IT";
                            break;
                        case 4:
                            kdabsen = "A";
                            break;
                        case 5:
                            kdabsen = "SD";
                            break;
                    }
                    idabsen=(Tanggal.replaceAll("-",""))+Nik;
                    Lembur = txtlmbr.getText().toString();
                    if(Lembur.equals("")){
                        txtlmbr.setText("0");
                        Lembur = "0";
                    }
                    LemburH = txtlmbrh.getText().toString();
                    if(LemburH.equals("")){
                        txtlmbrh.setText("0");
                        LemburH = "0";
                    }
                    Log.e("masa ora bisa", Lembur);
                    Izin = txtizin.getText().toString();
                    if(Izin.equals("")){
                        txtizin.setText("0");
                        Izin = "0";
                    }
                    //alert dialog belum pilih karyawan
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("BELUM PILIH KARYAWAN!").setContentText("Mohon pilih karyawan terlebih dahulu").show();
// Code to be executed when the positive button is clicked
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                    myEdit.putString("tanggal", txttglabsen.getText().toString());
                    myEdit.putInt("status", spinnerStatus.getSelectedItemPosition());
                    myEdit.putString("dtgrule", txtdtgrule.getText().toString());
                    myEdit.putString("plgrule", txtplgrule.getText().toString());
                    myEdit.putString("dtgact", txtdtgact.getText().toString());
                    myEdit.putString("plgact", txtplgact.getText().toString());
                    myEdit.putString("lembur", txtlmbr.getText().toString());
                    myEdit.putString("lemburh", txtlmbrh.getText().toString());
                    myEdit.putString("izin", txtizin.getText().toString());
                    myEdit.putString("keterangan", txtketabsensi.getText().toString());

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                    myEdit.commit();
                    //Do something
                    Fragment fragment = new PilihKaryawanFragment();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle b = new Bundle();
                    b.putString("tanggal", txttglabsen.getText().toString());
                    fragment.setArguments(b);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });//end click simpan
        }


    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}