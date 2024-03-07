package com.kgs.absensisakura.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kgs.absensisakura.Absensi.InputAbsensiFragment;
import com.kgs.absensisakura.Database.Absensi;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.MainActivity;
import com.kgs.absensisakura.R;

import java.io.Serializable;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.AbsensiViewHolder> {

    private ArrayList<Absensi> listAbsensi = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public ReportAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Absensi> getListAbsensi() {
        return listAbsensi;
    }

    public void setListAbsensi(ArrayList<Absensi> listNotes) {
        if (listNotes.size() > 0) {
            this.listAbsensi.clear();
        }
        this.listAbsensi.addAll(listNotes);
        notifyDataSetChanged();
    }
    public String hitung(int position) {
        String tglnow = null;
        for (int n = position+1; n < listAbsensi.toArray().length; n++) {
            tglnow = listAbsensi.get(n).getFdtglabsen();
        }
        Log.e("Tgl hitung", tglnow);
        return tglnow;
    }
    @NonNull
    @Override
    public AbsensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report, parent, false);
        return new AbsensiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {

        holder.tvNama.setText(listAbsensi.get(position).getFcnama()+" ( "+listAbsensi.get(position).getFcnik()+" )");
        holder.tvIzin.setText(listAbsensi.get(position).getFnizin());
        holder.tvIzinIt.setText(listAbsensi.get(position).getFnit());
        holder.tvIzinAlpha.setText(listAbsensi.get(position).getFnalpha());
        holder.tvIzinSd.setText(listAbsensi.get(position).getFnsd());
        holder.tvjumKK.setText(listAbsensi.get(position).getFnkk());
        holder.tvjumoff.setText(listAbsensi.get(position).getFnoff());
        holder.tvjumKH.setText(listAbsensi.get(position).getFnkh());
        holder.tvjumLbr.setText(listAbsensi.get(position).getFnlbrr());
        holder.tvjumLbrH.setText(listAbsensi.get(position).getFnlbrh());
        holder.tvPeriode.setText(listAbsensi.get(position).getDtperiode());
        //int i = position+1;
        //String a=listAbsensi.get(position).getFckdabsen();
        //Log.e("nm", a);
        //hitung(position);
    }

    @Override
    public int getItemCount() {
        return listAbsensi.size();
    }

    public class AbsensiViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNama, tvPeriode, tvIzin, tvIzinIt, tvIzinAlpha, tvIzinSd, tvjumKK, tvjumKH, tvjumLbr, tvjumLbrH, tvjumoff;
        //final ConstraintLayout btnEdit, btnDelete;

        public AbsensiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.textnama);
            tvPeriode = itemView.findViewById(R.id.textprd);
            tvIzin = itemView.findViewById(R.id.textizinrpt);
            tvIzinIt = itemView.findViewById(R.id.textizinkerja);
            tvIzinAlpha = itemView.findViewById(R.id.textalpha);
            tvIzinSd = itemView.findViewById(R.id.textsd);
            tvjumKK = itemView.findViewById(R.id.textjumkk);
            tvjumKH = itemView.findViewById(R.id.textjumkh);
            tvjumLbr = itemView.findViewById(R.id.textjumlbr);
            tvjumLbrH = itemView.findViewById(R.id.textjumlbrh);
            tvjumoff = itemView.findViewById(R.id.textjumoff);
        }
    }
}

