package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kgs.absensisakura.Adapter.AbsensiAdapter;
import com.kgs.absensisakura.Adapter.PeriodeAdapter;
import com.kgs.absensisakura.Database.Absensi;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Periode;
import com.kgs.absensisakura.R;

import java.util.ArrayList;


public class ListAbsensiFragment extends Fragment {
    private RecyclerView recyclerView;
    private AbsensiAdapter adapter;
    private ArrayList<Absensi> AbsensiArrayList;
    private DbHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_absensi, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //untuk show toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        dbHelper = new DbHelper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.rviewAbsensi);
        adapter = new AbsensiAdapter(getActivity());
        AbsensiArrayList = dbHelper.getAllAbsensi();
        adapter.setListAbsensi(AbsensiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        view.findViewById(R.id.float_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container,new InputAbsensiFragment());
        fr.addToBackStack(null);
        fr.commit();
        getFragmentManager().executePendingTransactions();
            }
        });
    }
}