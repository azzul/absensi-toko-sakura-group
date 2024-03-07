package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kgs.absensisakura.Adapter.KaryawanAdapter;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.R;

import java.util.ArrayList;


public class DataKaryawanFragment extends Fragment {
    private RecyclerView recyclerView;

    private ConstraintLayout cl;
    private KaryawanAdapter adapter;
    private ArrayList<Karyawan> karyawanArrayList;
    private DbHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_karyawan, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //untuk show toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        adapter = new KaryawanAdapter(getActivity());

        dbHelper = new DbHelper(getContext());
        karyawanArrayList = dbHelper.getAllUsers();
        adapter.setListKaryawan(karyawanArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        cl = view.findViewById(R.id.float_btn);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
                Toast.makeText(getContext(), "Selected "+ "Ini Baru OK", Toast.LENGTH_SHORT).show();
                //createDialogWithoutDateField().show();

            }
        });
    }

}