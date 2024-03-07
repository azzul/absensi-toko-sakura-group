package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kgs.absensisakura.Adapter.KaryawanAdapter;
import com.kgs.absensisakura.Adapter.PeriodeAdapter;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Periode;
import com.kgs.absensisakura.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PeriodeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PeriodeAdapter adapter;
    private ArrayList<Periode> PeriodeArrayList;
    private Spinner spinnerBulan, spinnerTahun;
    private EditText tvMulai, tvAkhir;
    private  DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    String Bln, nmBln, tglMulai, tglAkhir, Tahun;
    private Integer idPeriode, idBln;
    private TextView txTo;
    private ImageView calendarMulai, calendarAkhir;
    DbHelper dbHelper;
    private static final String[] bulan = {"Januari", "Februari", "Maret"};
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_periode, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DateFormat dateFormat = new SimpleDateFormat("MM");

        dbHelper = new DbHelper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.rviewPeriode);
        adapter = new PeriodeAdapter(getActivity());
        PeriodeArrayList = dbHelper.getAllPeriode();
        adapter.setListPeriode(PeriodeArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Date date = new Date();
        nmBln=dateFormat.format(date);
        Log.e("Month",dateFormat.format(date));

        Log.e("Bulan", nmBln);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        //initialize webView - JavaScript Enable - load url
        //createDialogWithoutDateField().show();
        spinnerBulan = (Spinner) view.findViewById(R.id.spinnerBulan);
        spinnerTahun = (Spinner) view.findViewById(R.id.spinnerTahun);
        calendarMulai=view.findViewById(R.id.calendar1);
        calendarAkhir=view.findViewById(R.id.calendar2);

        spinnerBulan.setSelection((Integer.parseInt(nmBln)-1));
        spinnerBulan.getSelectedItem().toString();
        Tahun=spinnerTahun.getSelectedItem().toString();
        idBln=spinnerBulan.getSelectedItemPosition();
        if(idBln<10){
            Bln="0"+idBln;
            Log.e("Bulan simpan", Bln);
        }else{
            Bln= String.valueOf(idBln);
        }
        dbHelper = new DbHelper(getContext());
        //dbHelper.addPeriode("April 2024", "202403", "2024-01-25", "2024-02-24");
        //DateFormat dateIdF = new SimpleDateFormat("yyyymmddHms");
        //Date dateId = new Date();
        //idPeriode= Integer.valueOf(dateIdF.format(dateId)+);
        txTo = view.findViewById(R.id.txKe);
        txTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ spinnerBulan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                PeriodeArrayList = dbHelper.getAllPeriode();
                adapter.setListPeriode(PeriodeArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
        Log.i("Bulan", spinnerBulan.getSelectedItem().toString());
        tvMulai =view.findViewById(R.id.tvDateFrom);

        tvAkhir =view.findViewById(R.id.tvDateTo);
        tvMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ spinnerBulan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                showDateDialogMulai();
            }
        });
        calendarMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ spinnerBulan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                showDateDialogMulai();
            }
        });
        tvAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ spinnerBulan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                showDateDialogAkhir();
            }
        });
        calendarAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ spinnerBulan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                //showDateDialogAkhir();
                showDateDialogAkhir();
            }
        });
        view.findViewById(R.id.btnsimpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                //Toast.makeText(getContext(), "Selected "+ Tahun+Bln, Toast.LENGTH_SHORT).show();
                //createDialogWithoutDateField().show();
                Log.e("Tanggale", tglMulai);
                String mulai=tglMulai;
                String akhir=tglAkhir;
                String periode =Tahun+Bln;
                String txperiode=spinnerBulan.getSelectedItem().toString()+" "+Tahun;
                Log.e("Tanggal Akhir", tglAkhir);
                Log.e("Periode", spinnerBulan.getSelectedItem().toString()+" "+Tahun);
                Log.e("String Periode", Tahun+Bln);
                //Log.e("String Periode", String.valueOf(idPeriode));
                //dbHelper.addPeriode( "Maret 2024", "202403", "2024-01-25", "2024-02-24");
                dbHelper.addPeriode( txperiode,periode,mulai,akhir);
                PeriodeArrayList = dbHelper.getAllPeriode();
                adapter.setListPeriode(PeriodeArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void showDateDialogMulai(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tvMulai.setText(dateFormatter.format(newDate.getTime()));
                tglMulai=dateFormatter.format(newDate.getTime());

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private void showDateDialogAkhir(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvAkhir.setText(dateFormatter.format(newDate.getTime()));
                tglAkhir=dateFormatter.format(newDate.getTime());

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}