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

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder> {

    private ArrayList<Absensi> listAbsensi = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public AbsensiAdapter(Activity activity) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_absensi, parent, false);
        return new AbsensiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {

        holder.tvNama.setText(listAbsensi.get(position).getFcnama()+" ( "+listAbsensi.get(position).getFcnik()+" )");
        holder.tvStatus.setText(listAbsensi.get(position).getFcnmkdabsen());
        holder.tvDatang.setText(listAbsensi.get(position).getFddtgact());
        holder.tvPulang.setText(listAbsensi.get(position).getFdplgact());
        holder.tvTanggal.setText(listAbsensi.get(position).getFdtglabsen());
        //int i = position+1;
        //String a=listAbsensi.get(position).getFckdabsen();
        //Log.e("nm", a);
        //hitung(position);

        holder.btnEdit.setOnClickListener((View v) -> {
            Fragment fragment = new InputAbsensiFragment();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle b = new Bundle();
            b.putString("identify", "edit");
            b.putString("nama_edit", listAbsensi.get(position).getFcnama());
            b.putString("nik_edit", listAbsensi.get(position).getFcnik());
            b.putString("idabsen_edit", listAbsensi.get(position).getIdAbsen());
            b.putString("tgl_edit", listAbsensi.get(position).getFdtglabsen());
            b.putString("dtgrule_edit", listAbsensi.get(position).getFddtgrule());
            b.putString("plgrule_edit", listAbsensi.get(position).getFdplgrule());
            b.putString("dtgact_edit", listAbsensi.get(position).getFddtgact());
            b.putString("plgact_edit", listAbsensi.get(position).getFdplgact());
            b.putString("status_edit", listAbsensi.get(position).getFcnmkdabsen());
            b.putString("lemburr_edit", listAbsensi.get(position).getFnlbrr());
            b.putString("lemburh_edit", listAbsensi.get(position).getFnlbrh());
            b.putString("izin_edit", listAbsensi.get(position).getFnizin());
            b.putString("keterangan_edit", listAbsensi.get(position).getFcket());
            b.putString("fnkk_edit", listAbsensi.get(position).getFnkk());
            b.putString("fnoff_edit", listAbsensi.get(position).getFnoff());
            b.putString("fnkh_edit", listAbsensi.get(position).getFnkh());
            b.putString("fnit_edit", listAbsensi.get(position).getFnit());
            b.putString("fnalpha_edit", listAbsensi.get(position).getFnalpha());
            b.putString("fnsd_edit", listAbsensi.get(position).getFnsd());

            fragment.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        holder.btnDelete.setOnClickListener((View v) -> {
            new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Apakah anda yakin akan menghapus absensi ini?").setContentText("").setConfirmText("Ya").setCancelText("Tidak").showCancelButton(true).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.cancel();
            }
            }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
            public void onClick(SweetAlertDialog sDialog) {


                dbHelper.deleteAbsensi(listAbsensi.get(position).getIdAbsen());
                listAbsensi.remove(position);
                notifyItemRangeChanged(position, listAbsensi.size());
                notifyItemRemoved(position);
                notifyDataSetChanged();
                sDialog.setTitleText("Sukses dihapus!").setContentText("").setConfirmText("OK").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

            }
            }).show();

        });
    }

    @Override
    public int getItemCount() {
        return listAbsensi.size();
    }

    public class AbsensiViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNama, tvStatus, tvDatang, tvPulang, tvTanggal;
        final ConstraintLayout btnEdit, btnDelete;

        public AbsensiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.textnama);
            tvStatus = itemView.findViewById(R.id.textabsen);
            tvDatang = itemView.findViewById(R.id.textdtg);
            tvPulang = itemView.findViewById(R.id.textplg);
            tvTanggal = itemView.findViewById(R.id.texttgl);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
