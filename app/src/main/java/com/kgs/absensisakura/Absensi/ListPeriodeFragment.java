package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kgs.absensisakura.Adapter.KaryawanAdapter;
import com.kgs.absensisakura.Adapter.PeriodeAdapter;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Periode;
import com.kgs.absensisakura.R;

import java.util.ArrayList;


public class ListPeriodeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PeriodeAdapter adapter;
    private ArrayList<Periode> PeriodeArrayList;
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

        dbHelper = new DbHelper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.rviewPeriode);
        adapter = new PeriodeAdapter(getActivity());
        PeriodeArrayList = dbHelper.getAllPeriode();
        adapter.setListPeriode(PeriodeArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}