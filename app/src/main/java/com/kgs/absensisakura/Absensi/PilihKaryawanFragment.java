package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kgs.absensisakura.Adapter.DialogAdapter;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.R;

import java.util.ArrayList;


public class PilihKaryawanFragment extends Fragment {
    private Button btn_plh;
    private RecyclerView recyclerView;
    private DialogAdapter adapter;
    private DbHelper dbHelper;
    private ArrayList<Karyawan> karyawanArrayList;
    private Dialog customDialog;
    private TextView txtjudul, txtkosong;
    private String judul;
    private ConstraintLayout cL;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pilih_karyawan, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                recyclerView = (RecyclerView) view.findViewById(R.id.rview_pilih);
                adapter = new DialogAdapter(getActivity());
                txtjudul = view.findViewById(R.id.jdlTgl);
                txtkosong = view.findViewById(R.id.rvkosong);
                cL = view.findViewById(R.id.ltkosong);
                cL.setVisibility(View.GONE);
                dbHelper = new DbHelper(getContext());
                Bundle bundle = getArguments();

                String tgl = getArguments().getString("tanggal");
                txtjudul.setText("TANGGAL ABSENSI " + tgl);
                karyawanArrayList = dbHelper.getAllKaryawanAbsen(tgl);
                adapter.setListKaryawanDialog(karyawanArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                    if( layoutManager.getItemCount() == 0 ){
            //Do something
                        cL.setVisibility(View.VISIBLE);
                    }

    }
}