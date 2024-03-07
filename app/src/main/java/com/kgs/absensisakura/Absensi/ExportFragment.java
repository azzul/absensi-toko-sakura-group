package com.kgs.absensisakura.Absensi;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kgs.absensisakura.Adapter.AbsensiAdapter;
import com.kgs.absensisakura.Adapter.KaryawanAdapter;
import com.kgs.absensisakura.Adapter.ReportAdapter;
import com.kgs.absensisakura.Database.Absensi;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.Database.Periode;
import com.kgs.absensisakura.Database.PeriodeSpinner;
import com.kgs.absensisakura.R;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExportFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;

    private ConstraintLayout cl;
    private ReportAdapter adapter;
    private ArrayList<Absensi> absensiArrayList;
    private ArrayList<Periode> periodeArrayList;
    private DbHelper dbHelper;
    private TextView txdtfrom, txdtto;
    private Spinner spinnerPeriode;
    private String selected, nmperiode, dtperiode, dtfrom, dtto, fcnmgrp, nmperiode2;
    private static final int STORAGE_PERMISSION_CODE = 101;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_export, container, false);


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //untuk hide toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        recyclerView = (androidx.recyclerview.widget.RecyclerView) view.findViewById(R.id.rviewReport);
        spinnerPeriode = view.findViewById(R.id.spinnerprd);
        txdtfrom = view.findViewById(R.id.dtfrom);
        txdtto = view.findViewById(R.id.dtto);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        dbHelper = new DbHelper(getContext());

        cl = view.findViewById(R.id.float_btn);
        //untuk spinner
        List<PeriodeSpinner> list= dbHelper.getAllData();
        //creating adapter for spinner
        String[] nameList=new String[list.size()];
        String[] dtList=new String[list.size()];

        for(int i=0;i<list.size();i++){
            nameList[i]=list.get(i).getNamaPeriode();
            dtList[i]=list.get(i).getDatePeriode();//create array of name

        }
        Log.e( "onViewCreated: ", Arrays.toString(nameList));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, nameList);
        //drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

// attaching data adapter to spinner
        spinnerPeriode.setAdapter(dataAdapter);
        selected = spinnerPeriode.getSelectedItem().toString();
        spinnerPeriode.getSelectedItemPosition();
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
               //Toast.makeText(getContext(), "Selected "+ "Ini Baru OK", Toast.LENGTH_SHORT).show();
                //createDialogWithoutDateField().show();
                fcnmgrp= absensiArrayList.get(0).getFcnmgrp();
                nmperiode2 = absensiArrayList.get(1).getNmperiode();
                Log.e("cek1: ", nmperiode2);
                createXlsx(absensiArrayList, fcnmgrp, nmperiode2);
            }
        });
        spinnerPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Date fromDate;
                //((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                dtperiode = list.get(position).getDatePeriode();
                dtfrom = list.get(position).getDateFrom();
                dtto = list.get(position).getDateTo();
                nmperiode = list.get(position).getNamaPeriode();
                DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Log.e("dtfrom: ", dtfrom);
                txdtfrom.setText(dtfrom);
                txdtto.setText(dtto);
                Date datefrom = null;
                Date dateto = null;
                try {
                    datefrom = inputFormat.parse(dtfrom);
                    dateto = inputFormat.parse(dtto);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String outputdateFrom = outputFormat.format(datefrom);
                String outputdateTo = outputFormat.format(dateto);
                Log.e("dt: ", outputdateFrom);
                //call database with this parameter
                adapter = new ReportAdapter(getActivity());
                absensiArrayList = dbHelper.getRekap(nmperiode, dtperiode, outputdateFrom, outputdateTo);
                adapter.setListAbsensi(absensiArrayList);
                //setelah itu tampilkan di recycleview
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
               selectData(dtperiode);
            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



    }
    private void selectData(String dtperiode) {

        Log.e( "selected2: ", dtperiode);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String list = parent.getItemAtPosition(position).toString();
        //showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + list, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void createXlsx(ArrayList<Absensi> modelHistoryAbsen, String fcnmgrp, String fcnmprd) {

        try {
            String strDate = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss", Locale.getDefault()).format(new Date());
            File root = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"");

            if (!root.exists())
                root.mkdirs();
            File path = new File(root, "/Absensi " + fcnmgrp+ " "+fcnmprd+".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(path);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderRight(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.MEDIUM);

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);

            XSSFSheet sheet = workbook.createSheet("Rekap absensi "+fcnmprd);
            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("fcbln");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(1);
            cell.setCellValue("fdfrom1");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(2);
            cell.setCellValue("fdto1");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(3);
            cell.setCellValue("fcnik");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(4);
            cell.setCellValue("fcnama");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(5);
            cell.setCellValue("fcalamat");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(6);
            cell.setCellValue("fctelp");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(7);
            cell.setCellValue("fcnmgrp");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(8);
            cell.setCellValue("fnlbrjam");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(9);
            cell.setCellValue("fnlbrhjam");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(10);
            cell.setCellValue("fnkk");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(11);
            cell.setCellValue("fnkh");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(12);
            cell.setCellValue("fnik");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(13);
            cell.setCellValue("fnof");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(14);
            cell.setCellValue("fnit");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(15);
            cell.setCellValue("fnsd");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(16);
            cell.setCellValue("fna");
            cell.setCellStyle(headerStyle);

            for (int i = 0; i < modelHistoryAbsen.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(modelHistoryAbsen.get(i).getDtperiode());
                sheet.setColumnWidth(0, (modelHistoryAbsen.get(i).getDtperiode().length() + 30) * 80);

                cell = row.createCell(1);
                cell.setCellValue(modelHistoryAbsen.get(i).getDtFrom());
                sheet.setColumnWidth(1, (modelHistoryAbsen.get(i).getDtFrom().length() + 30) * 80);

                cell = row.createCell(2);
                cell.setCellValue(modelHistoryAbsen.get(i).getDtTo());
                sheet.setColumnWidth(2, (modelHistoryAbsen.get(i).getDtTo().length() + 30) * 80);

                cell = row.createCell(3);
                cell.setCellValue(modelHistoryAbsen.get(i).getFcnik());
                sheet.setColumnWidth(3, (modelHistoryAbsen.get(i).getFcnik().length() + 30) * 80);

                cell = row.createCell(4);
                cell.setCellValue(modelHistoryAbsen.get(i).getFcnama());
                sheet.setColumnWidth(4, (modelHistoryAbsen.get(i).getFcnama().length() + 30) * 150);

                cell = row.createCell(5);
                cell.setCellValue(modelHistoryAbsen.get(i).getFcalamat());
                sheet.setColumnWidth(5, (modelHistoryAbsen.get(i).getFcalamat().length() + 30) * 230);

                cell = row.createCell(6);
                cell.setCellValue(modelHistoryAbsen.get(i).getFctelp());
                sheet.setColumnWidth(6, (modelHistoryAbsen.get(i).getFctelp().length() + 30) * 100);

                cell = row.createCell(7);
                cell.setCellValue(modelHistoryAbsen.get(i).getFcnmgrp());
                sheet.setColumnWidth(7, (modelHistoryAbsen.get(i).getFcnmgrp().length() + 30) * 120);

                cell = row.createCell(8);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnlbrr());
                sheet.setColumnWidth(8, (modelHistoryAbsen.get(i).getFnlbrr().length() + 30) * 80);

                cell = row.createCell(9);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnlbrh());
                sheet.setColumnWidth(9, (modelHistoryAbsen.get(i).getFnlbrh().length() + 30) * 80);

                cell = row.createCell(10);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnkk());
                sheet.setColumnWidth(10, (modelHistoryAbsen.get(i).getFnkk().length() + 30) * 70);

                cell = row.createCell(11);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnkh());
                sheet.setColumnWidth(11, (modelHistoryAbsen.get(i).getFnkh().length() + 30) * 70);

                cell = row.createCell(12);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnizin());
                sheet.setColumnWidth(12, (modelHistoryAbsen.get(i).getFnizin().length() + 30) * 70);

                cell = row.createCell(13);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnoff());
                sheet.setColumnWidth(13, (modelHistoryAbsen.get(i).getFnoff().length() + 30) * 70);

                cell = row.createCell(14);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnit());
                sheet.setColumnWidth(14, (modelHistoryAbsen.get(i).getFnit().length() + 30) * 70);

                cell = row.createCell(15);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnsd());
                sheet.setColumnWidth(15, (modelHistoryAbsen.get(i).getFnsd().length() + 30) * 70);

                cell = row.createCell(16);
                cell.setCellValue(modelHistoryAbsen.get(i).getFnalpha());
                sheet.setColumnWidth(16, (modelHistoryAbsen.get(i).getFnalpha().length() + 30) * 70);

            }

            workbook.write(outputStream);
            outputStream.close();
            Toast.makeText(getContext(), "Data berhasil di ekspor!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}